package dalosto.dnit.sistdown.action;
import java.io.File;
import dalosto.dnit.sistdown.domain.InputArgsModel;
import dalosto.dnit.sistdown.domain.TagsConfiguracao;
import dalosto.dnit.sistdown.framework.annotations.Autowired;
import dalosto.dnit.sistdown.framework.annotations.Component;
import dalosto.dnit.sistdown.framework.annotations.Order;
import dalosto.dnit.sistdown.handler.PromptInputsHandler;
import dalosto.dnit.sistdown.handler.RecursosHandler;
import dalosto.dnit.sistdown.helper.CaminhoHelper;
import dalosto.dnit.sistdown.service.LoggerArquivoService;
import dalosto.dnit.sistdown.service.LoggerConsoleService;


/**
 * Funcionalidade - Limpa <p>
 * Classe que permite que o usuário limpe os trechos que estão armazenados na maquina local.
 */
@Component
@Order(8)
public class HandleLimpa implements Acao {

    @Autowired
    LoggerConsoleService loggerConsoleService;

    @Autowired
    LoggerArquivoService loggerArquivoService;

    @Autowired
    RecursosHandler recursosHandler;

    @Autowired
    PromptInputsHandler promptInputsHandler;

    
    public void executa() throws Exception {
        InputArgsModel input = promptInputsHandler.verificaSeFoiSolicitado(
                               (txt) -> TagsConfiguracao.textEhUmaTag(txt, TagsConfiguracao.LIMPA));
        if (input.foiSolicitado()) {
            loggerArquivoService.clearLog();
            limpaPastaDownloads();
        }
    }


    private void limpaPastaDownloads() {
        File temp = CaminhoHelper.DIR_VIDEOS_TEMP;
        boolean isRenamed = CaminhoHelper.DIR_VIDEOS.renameTo(temp);
        if (isRenamed)
            new Thread(() -> recursosHandler.delete(temp)).start();
        else
        recursosHandler.delete(CaminhoHelper.DIR_VIDEOS);
        CaminhoHelper.DIR_VIDEOS.mkdirs();
        loggerConsoleService.printaMensagem("... Pasta Limpa");
    }


}
