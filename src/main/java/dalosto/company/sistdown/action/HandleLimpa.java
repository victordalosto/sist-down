package dalosto.company.sistdown.action;
import java.io.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import dalosto.company.sistdown.domain.InputArgsModel;
import dalosto.company.sistdown.domain.TagsConfiguracao;
import dalosto.company.sistdown.handler.PromptInputsHandler;
import dalosto.company.sistdown.handler.RecursosHandler;
import dalosto.company.sistdown.service.ArquivoService;
import dalosto.company.sistdown.service.CaminhoService;
import dalosto.company.sistdown.service.LoggerConsoleService;


/**
 * Funcionalidade - Limpa <p>
 * Classe que permite que o usuário limpe os trechos que estão armazenados na maquina local.
 */
@Component
@Order(8)
public final class HandleLimpa extends Acao {

    @Autowired
    private LoggerConsoleService loggerConsoleService;

    @Autowired
    private ArquivoService arquivoService;

    @Autowired
    private RecursosHandler recursosHandler;

    @Autowired
    private PromptInputsHandler prompt;


    @Override
    public boolean isCalled() {
        InputArgsModel input = prompt.verificaSeFoiSolicitado(TagsConfiguracao.LIMPA);
        return input.isStatus();
    }


    @Override
    public void executaCLI() throws Exception {
        arquivoService.clearTrechosBaixados();
        limpaPastaDownloads();
    }


    private void limpaPastaDownloads() {
        File temp = CaminhoService.DIR_SISTDOWN_VIDEOS_TEMP;
        boolean isRenamed = CaminhoService.DIR_SISTDOWN_VIDEOS.renameTo(temp);
        if (isRenamed) {
            new Thread(() -> recursosHandler.delete(temp)).start();
        }
        else {
            recursosHandler.delete(CaminhoService.DIR_SISTDOWN_VIDEOS);
        }
        CaminhoService.DIR_SISTDOWN_VIDEOS.mkdirs();
        loggerConsoleService.printaMensagem("... Pasta Limpa");
    }


}
