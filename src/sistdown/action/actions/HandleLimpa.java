package sistdown.action.actions;
import java.io.File;

import sistdown.Handler.PromptInputsHandler;
import sistdown.Handler.RecursosHandler;
import sistdown.service.Caminho;
import sistdown.service.Registrador;


/**
 * Funcionalidade - Limpa <p>
 * Classe que permite que o usuário limpe os trechos que estão armazenados na maquina local.
 */
public class HandleLimpa implements Acao {

    
    public void executa() throws Exception {
        if (PromptInputsHandler.foiSolicitadoLimpar()) {
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
