package sistdown.action.actions;
import java.io.File;
import sistdown.model.PromptInputs;
import sistdown.service.Caminho;
import sistdown.service.RecursosHandler;
import sistdown.service.Logger;


/**
 * Funcionalidade - Limpa <p>
 * Classe que permite que o usuário limpe os trechos que estão armazenados na maquina local.
 */
public class HandleLimpa implements Acao {

    
    public void executa() throws Exception {
        if (PromptInputs.foiSolicitadoLimpar()) {
            Logger.clearLog();
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
        System.out.print("\n * ... Pasta Limpa");
    }


}
