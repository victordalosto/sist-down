package dalosto.dnit.sistdown.action;
import java.io.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import dalosto.dnit.sistdown.domain.InputArgsModel;
import dalosto.dnit.sistdown.domain.TagsConfiguracao;
import dalosto.dnit.sistdown.handler.PromptInputsHandler;
import dalosto.dnit.sistdown.handler.RecursosHandler;
import dalosto.dnit.sistdown.service.ArquivoService;
import dalosto.dnit.sistdown.service.CaminhoService;
import dalosto.dnit.sistdown.service.LoggerConsoleService;


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
    public void executa() throws Exception {
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
