package dalosto.dnit.sistdown.handler;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.Callable;
import dalosto.dnit.sistdown.service.ArquivoService;
import dalosto.dnit.sistdown.service.CaminhoService;
import dalosto.dnit.sistdown.service.LoggerConsoleService;

/**
 * Classe contendo Callable Task, permitindo que a aplicação faça o Download em
 * diferentes Threads.
 */
public class TarefaDownload implements Callable<Void> {

    String idTrecho;
    String caminho;
    LoggerConsoleService loggerConsoleService;
    ArquivoService arquivoService;
    RecursosHandler recursosHandler;


    public TarefaDownload(String idTrecho, String caminho, LoggerConsoleService loggerConsoleService,
            ArquivoService arquivoService, RecursosHandler recursosHandler) {
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
