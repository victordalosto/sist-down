package dalosto.dnit.sistdown.action;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import dalosto.dnit.sistdown.framework.annotations.Autowired;
import dalosto.dnit.sistdown.framework.annotations.Component;
import dalosto.dnit.sistdown.framework.annotations.Order;
import dalosto.dnit.sistdown.handler.PromptInputsHandler;
import dalosto.dnit.sistdown.handler.RecursosHandler;
import dalosto.dnit.sistdown.helper.CaminhoHelper;
import dalosto.dnit.sistdown.repository.TrechoRepository;
import dalosto.dnit.sistdown.service.LoggerArquivoService;
import dalosto.dnit.sistdown.service.LoggerConsoleService;


/**
 * Funcionalidade - Download <p>
 * Classe que faz o download dos vídeos da rede para a maquina local. <p>
 * Caso o usuário faça a alteração de contexto, o sistema é capaz de 
 * migrar o target da aplicação para trabalhar com a rede e continuar baixando os vídeos.
 */
@Component
@Order(9)
public class HandleDownload implements Acao {

    @Autowired
    LoggerConsoleService loggerConsoleService;

    @Autowired
    LoggerArquivoService loggerArquivoService;
    
    private static ExecutorService executorService = Executors.newFixedThreadPool(3);
 

    /**
     * Faz o download dos trechos na maquina local.
     */
    public void executa() throws Exception {
        
        Set<String> idsParaBaixar = PromptInputsHandler.obtemIdsDigitados();
        if (idsParaBaixar.size() > 0) {
            loggerConsoleService.printaMensagem("... Iniciando o download dos trechos");
            Set<TarefaDownload> listaParaBaixar = new HashSet<>();
            Set<String> trechosBaixadosNesseLoop = new HashSet<>();
            for (String id : idsParaBaixar) {
                String caminho = TrechoRepository.getPath(id);
                if (caminho == null) {
                    loggerConsoleService.printaMensagem("!!! Trecho de id: "+id+" não está no banco !!!");
                } else if(!trechosBaixadosNesseLoop.contains(caminho)) {
                    trechosBaixadosNesseLoop.add(caminho);
                    listaParaBaixar.add(new TarefaDownload(id, caminho, loggerConsoleService, loggerArquivoService));
                } 
            }
            executorService.invokeAll(listaParaBaixar);
        }
    }

}




/**
 * Classe contendo Callable Task, permitindo que a aplicação faça o Download em diferentes Threads.
 */
class TarefaDownload implements Callable<Void> {

    String idTrecho;
    String caminho;
    LoggerConsoleService loggerConsoleService;
    LoggerArquivoService loggerArquivoService;

    TarefaDownload(String idTrecho, 
                   String caminho, 
                   LoggerConsoleService loggerConsoleService, 
                   LoggerArquivoService loggerArquivoService) {
        this.idTrecho = idTrecho;
        this.caminho = caminho;
        this.loggerConsoleService = loggerConsoleService;
        this.loggerArquivoService = loggerArquivoService;
    }

    @Override
    public Void call() {
        try {
            Path input = Paths.get(CaminhoHelper.DIR_REDE_VIDEOS_ROOT.toString(), caminho);
            Path target = Paths.get(CaminhoHelper.DIR_TARGET_VIDEOS_ROOT.toString(), caminho);
            RecursosHandler.delete(target.toFile());
            RecursosHandler.walkAndCopy(input, target, StandardCopyOption.REPLACE_EXISTING);
            informaQueTrechoFoiBaixado(idTrecho, target);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    private void informaQueTrechoFoiBaixado(String idTrecho, Path target) throws IOException {
        String nomeTrecho = loggerArquivoService.logaUmDownload(idTrecho, target);
        loggerConsoleService.printaMensagem("...> Baixado: " + nomeTrecho);
    }

}
