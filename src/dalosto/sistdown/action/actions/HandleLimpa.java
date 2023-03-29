package dalosto.sistdown.action.actions;
import java.io.File;
import dalosto.sistdown.Handler.PromptInputsHandler;
import dalosto.sistdown.Handler.RecursosHandler;
import dalosto.sistdown.model.InputArgsModel;
import dalosto.sistdown.model.TagsConfiguracao;
import dalosto.sistdown.service.Caminho;
import dalosto.sistdown.service.Registrador;


/**
 * Funcionalidade - Limpa <p>
 * Classe que permite que o usuário limpe os trechos que estão armazenados na maquina local.
 */
public class HandleLimpa implements Acao {

    
    public void executa() throws Exception {
        InputArgsModel input = PromptInputsHandler.verificaSeFoiSolicitado(
                               (txt) -> TagsConfiguracao.textEhUmaTag(txt, TagsConfiguracao.LIMPA));
        if (input.foiSolicitado()) {
            Registrador.clearLog();
            limpaPastaDownloads();
        }
    }


    private void limpaPastaDownloads() {
        File temp = Caminho.FILE_TARGET_VIDEOS_TEMP;
        boolean isRenamed = Caminho.DIR_TARGET_VIDEOS_ROOT.renameTo(temp);
        if (isRenamed)
            new Thread(() -> RecursosHandler.delete(temp)).start();
        else
            RecursosHandler.delete(Caminho.DIR_TARGET_VIDEOS_ROOT);
        Caminho.DIR_TARGET_VIDEOS_ROOT.mkdirs();
        Registrador.printaMensagemConsole("... Pasta Limpa");
    }


}
