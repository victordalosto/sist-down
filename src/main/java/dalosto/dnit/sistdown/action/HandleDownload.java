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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import dalosto.dnit.sistdown.handler.PromptInputsHandler;
import dalosto.dnit.sistdown.handler.RecursosHandler;
import dalosto.dnit.sistdown.repository.TrechoRepository;
import dalosto.dnit.sistdown.service.ArquivoService;
import dalosto.dnit.sistdown.service.CaminhoService;
import dalosto.dnit.sistdown.service.LoggerConsoleService;


/**
 * Funcionalidade - Download <p>
 * Classe que faz o download dos vídeos da rede para a maquina local. <p>
 * Caso o usuário faça a alteração de contexto, o sistema é capaz de 
 * migrar o target da aplicação para trabalhar com a rede e continuar baixando os vídeos.
 */
@Component
@Order(9)
public class HandleDownload extends Acao {

    @Autowired
    private LoggerConsoleService loggerConsoleService;

    @Autowired
    private ArquivoService arquivoService;
    
    @Autowired
    private RecursosHandler recursosHandler;

    @Autowired
    private PromptInputsHandler promptInputsHandler;

    @Autowired
    private TrechoRepository trechoRepository;

    private ExecutorService executorService = Executors.newFixedThreadPool(1);
 

    @Override
    public boolean isCalled() {
        return promptInputsHandler.obtemIdsDigitados().size() > 0;
    }


    /**
     * Faz o download dos trechos na maquina local.
     */
    @Override
    public void executa() throws Exception {
        Set<String> idsParaBaixar = promptInputsHandler.obtemIdsDigitados();
        loggerConsoleService.printaMensagem("... Iniciando o download dos trechos");
        Set<TarefaDownload> listaParaBaixar = new HashSet<>();
        Set<String> trechosBaixadosNesseLoop = new HashSet<>();
        for (String id : idsParaBaixar) {
            String caminho = trechoRepository.getPath(id);
            if (caminho == null) {
                loggerConsoleService.printaMensagem("!!! Trecho de id: "+id+" não está no banco !!!");
            } else if(!trechosBaixadosNesseLoop.contains(caminho)) {
                trechosBaixadosNesseLoop.add(caminho);
                listaParaBaixar.add(new TarefaDownload(id, caminho, loggerConsoleService, arquivoService, recursosHandler));
            } 
        }
        executorService.invokeAll(listaParaBaixar);
    }

}




/**
 * Classe contendo Callable Task, permitindo que a aplicação faça o Download em diferentes Threads.
 */
class TarefaDownload implements Callable<Void> {

    String idTrecho;
    String caminho;
    LoggerConsoleService loggerConsoleService;
    ArquivoService arquivoService;
    RecursosHandler recursosHandler;

    TarefaDownload(String idTrecho, 
                   String caminho, 
                   LoggerConsoleService loggerConsoleService, 
                   ArquivoService arquivoService,
                   RecursosHandler recursosHandler) {
        this.idTrecho = idTrecho;
        this.caminho = caminho;
        this.loggerConsoleService = loggerConsoleService;
        this.arquivoService = arquivoService;
        this.recursosHandler = recursosHandler;
    }


    @Override
    public Void call() {
        try {
            Path input = Paths.get(CaminhoService.DIR_REDE_VIDEOS.toString(), caminho);
            Path target = Paths.get(CaminhoService.DIR_SISTDOWN_VIDEOS.toString(), caminho);
            recursosHandler.delete(target.toFile());
            recursosHandler.walkAndCopy(input, target, StandardCopyOption.REPLACE_EXISTING);
            informaQueTrechoFoiBaixado(idTrecho, target);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private void informaQueTrechoFoiBaixado(String idTrecho, Path target) throws IOException {
        String nomeTrecho = arquivoService.logaTrechoBaixado(idTrecho, target);
        loggerConsoleService.printaMensagem("...> Baixado: " + nomeTrecho);
    }

}
