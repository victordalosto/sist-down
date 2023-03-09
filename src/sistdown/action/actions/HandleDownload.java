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

import sistdown.Handler.PromptInputsHandler;
import sistdown.Handler.RecursosHandler;
import sistdown.repository.TrechoRepository;
import sistdown.service.Caminho;
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
        Set<String> idsParaBaixar = PromptInputsHandler.obtemIdsDigitados();
        if (idsParaBaixar.size() > 0) {
            System.out.println("\n * ... Iniciando o download dos trechos");
            Set<Tarefa> listaParaBaixar = new HashSet<>();
            Set<String> trechosBaixadosNesseLoop = new HashSet<>();
            for (String id : idsParaBaixar) {
                String caminho = TrechoRepository.getPath(id);
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
            Path input = Paths.get(Caminho.DIR_REDE_VIDEOS_ROOT.toString(), caminho);
            Path target = Paths.get(Caminho.DIR_TARGET_VIDEOS_ROOT.toString(), caminho);
            RecursosHandler.delete(target.toFile());
            RecursosHandler.walkAndCopy(input, target, StandardCopyOption.REPLACE_EXISTING);
            informaQueTrechoFoiBaixado(idTrecho, target);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    private void informaQueTrechoFoiBaixado(String idTrecho, Path target) throws IOException {
        String nomeTrecho = Logger.logaUmDownload(idTrecho, target);
        System.out.println(" * ...> Baixado: " + nomeTrecho);
        PromptInputsHandler.removeInputDaLista(idTrecho);
    }

}
