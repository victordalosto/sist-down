package sistdown.action.actions;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import sistdown.model.PromptInputs;
import sistdown.service.Caminho;
import sistdown.service.DBTrecho;
import sistdown.service.Download;
import sistdown.service.Logger;


/**
 * Funcionalidade - Download <p>
 * Classe que faz o download dos vídeos da rede para a maquina local. <p>
 * Caso o usuário faça a alteração de contexto, o sistema é capaz de 
 * migrar o target da aplicação para trabalhar com a rede e continuar baixando os vídeos.
 */
public class HandleDownload implements Acao {
    
    private static ExecutorService executorService = Executors.newFixedThreadPool(3);
 

    /**
     * Faz o download dos trechos na maquina local.
     */
    public void executa() throws Exception {
        Set<String> idsParaBaixar = PromptInputs.obtemIdsDigitados();
        if (idsParaBaixar.size() > 0) {
            System.out.println("\n * ... Iniciando o download dos trechos");
            Set<Tarefa> listaParaBaixar = new HashSet<>();
            Set<String> trechosBaixadosNesseLoop = new HashSet<>();
            for (String id : idsParaBaixar) {
                String caminho = DBTrecho.getPath(id);
                if (caminho == null) {
                    System.out.println(" * ... Trecho de id: "+id+" não está no banco.");
                } else if(!trechosBaixadosNesseLoop.contains(caminho)) {
                    trechosBaixadosNesseLoop.add(caminho);
                    listaParaBaixar.add(new Tarefa(id, caminho));
                } 
            }
            executorService.invokeAll(listaParaBaixar);
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
            Path input = Paths.get(Caminho.INPUT_ROOT.toString(), caminho);
            Path target = Paths.get(Caminho.TARGET_DOWNLOAD.toString(), caminho);
            Download.delete(target.toFile());
            Download.walkAndCopy(input, target, StandardCopyOption.REPLACE_EXISTING);
            informaQueTrechoFoiBaixado(idTrecho, target);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    private void informaQueTrechoFoiBaixado(String idTrecho, Path target) throws IOException {
        String nomeTrecho = Logger.logDownload(idTrecho, target);
        System.out.println(" * ...> Baixado: " + nomeTrecho);
        PromptInputs.removeInputDaLista(idTrecho);
    }

}
