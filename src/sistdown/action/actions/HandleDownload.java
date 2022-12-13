package sistdown.action.actions;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import sistdown.model.InputsPrompt;
import sistdown.model.Trechos;
import sistdown.service.Caminho;
import sistdown.service.Util;


/**
 * Funcionalidade - Download <p>
 * Classe que faz o download dos vídeos da rede para a maquina local. <p>
 * Caso o usuário faça a alteração de contexto, o sistema é capaz de 
 * migrar o target da aplicação para trabalhar com a rede e continuar baixando os vídeos.
 */
public class HandleDownload implements Acao {
    
    static ExecutorService executorService = Executors.newFixedThreadPool(3);
    static List<Tarefa> listaTarefa;
 

    /**
     * Faz o download dos trechos na maquina local.
     */
    public void executa() throws Exception {
        Util.criaListBancoComTrechosDisponiveis();
        if (InputsPrompt.sizeList() > 0) {
            System.out.println(" * ...Iniciando o download dos trechos");
            listaTarefa = new ArrayList<>();
            while (InputsPrompt.sizeList() > 0) {
                String idTrecho = InputsPrompt.getFirstInList();
                String caminho = Trechos.getCaminho(idTrecho);
                InputsPrompt.removeFirstInList();
                if (caminho == null) {
                    System.out.println(" * ...Não foi encontrado o trecho de id: "+idTrecho+".");
                    continue;
                } else {
                    Tarefa tarefa = new Tarefa(idTrecho, caminho);
                    listaTarefa.add(tarefa);
                } 
            }
            executorService.invokeAll(listaTarefa);
        }
    }
}




/**
 * Classe contendo Callable Task, permitindo que a aplicação faça o Download em diferentes Threads.
 */
class Tarefa implements Callable<Void> {

    String idTrecho;
    String caminho;

    Tarefa(String idTrecho, String caminho) {
        this.idTrecho = idTrecho;
        this.caminho = caminho;
    }

    @Override
    public Void call() {
        try {
            Path origin = Paths.get(Caminho.REDE_VIDEO_FOLDER.toString(), caminho);
            Path target = getTarget(caminho);
            Util.deleteFolder(target.toFile());
            Util.copyFolder(origin, target, StandardCopyOption.REPLACE_EXISTING);
            informaQueTrechoFoiBaixado(idTrecho, target);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    
    private Path getTarget(String caminhoTrecho) {
        if (Util.contexto.equalsIgnoreCase("LOCAL"))
            return Paths.get(Caminho.SISTDOWN_CURRENT.toString(), caminhoTrecho);
        return Paths.get(Caminho.SISTDOWN_DOWNLOADS_LOCAL.toString(), caminhoTrecho);
    }



    private void informaQueTrechoFoiBaixado(String idTrecho, Path target) throws IOException {
        String nomeTrecho = idTrecho + "-" + target.toString().replaceAll(".+_", "").substring(0, 5);
        Files.write(Caminho.SISTDOWN_CONFIG_INFODOWNLOADS.toPath(), (nomeTrecho + ",  ").getBytes(), StandardOpenOption.APPEND);
        System.out.println(" * ...>Baixado " + nomeTrecho);
    }

}
