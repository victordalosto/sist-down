package dalosto.sistdown.action;
import java.io.File;
import dalosto.sistdown.domain.InputArgsModel;
import dalosto.sistdown.domain.TagsConfiguracao;
import dalosto.sistdown.framework.annotations.Component;
import dalosto.sistdown.framework.annotations.Order;
import dalosto.sistdown.handler.PromptInputsHandler;
import dalosto.sistdown.handler.RecursosHandler;
import dalosto.sistdown.helper.CaminhoHelper;
import dalosto.sistdown.service.LoggerConsoleService;


/**
 * Funcionalidade - Limpa <p>
 * Classe que permite que o usuário limpe os trechos que estão armazenados na maquina local.
 */
@Component
@Order(8)
public class HandleLimpa implements Acao {

    
    public void executa() throws Exception {
        InputArgsModel input = PromptInputsHandler.verificaSeFoiSolicitado(
                               (txt) -> TagsConfiguracao.textEhUmaTag(txt, TagsConfiguracao.LIMPA));
        if (input.foiSolicitado()) {
            LoggerConsoleService.clearLog();
            limpaPastaDownloads();
        }
    }


    private void limpaPastaDownloads() {
        File temp = CaminhoHelper.FILE_TARGET_VIDEOS_TEMP;
        boolean isRenamed = CaminhoHelper.DIR_TARGET_VIDEOS_ROOT.renameTo(temp);
        if (isRenamed)
            new Thread(() -> RecursosHandler.delete(temp)).start();
        else
            RecursosHandler.delete(CaminhoHelper.DIR_TARGET_VIDEOS_ROOT);
        CaminhoHelper.DIR_TARGET_VIDEOS_ROOT.mkdirs();
        LoggerConsoleService.printaMensagemConsole("... Pasta Limpa");
    }


}
