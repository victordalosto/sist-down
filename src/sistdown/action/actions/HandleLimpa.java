package sistdown.action.actions;
import java.io.File;
import sistdown.model.PromptInputs;
import sistdown.service.Caminho;
import sistdown.service.Download;
import sistdown.service.Logger;


/**
 * Funcionalidade - Limpa <p>
 * Classe que permite que o usuário limpe os trechos que estão armazenados na maquina local.
 */
public class HandleLimpa implements Acao {

    
    public void executa() throws Exception {
        if (PromptInputs.foiSolicitadoLimpar()) {
            Logger.clear();
            limpaPastaDownloads();
        }
    }


    private void limpaPastaDownloads() {
        File temp = Caminho.TARGET_DOWNLOAD_TEMP;
        boolean isRenamed = Caminho.TARGET_DOWNLOAD.renameTo(temp);
        if (isRenamed)
            new Thread(() -> Download.delete(temp)).start();
        else
            Download.delete(Caminho.TARGET_DOWNLOAD);
        Caminho.TARGET_DOWNLOAD.mkdirs();
        System.out.print("\n * ... Pasta Limpa");
    }


}
