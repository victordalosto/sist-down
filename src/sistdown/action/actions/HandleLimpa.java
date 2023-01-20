package sistdown.action.actions;
import java.io.File;
import sistdown.model.PromptInputs;
import sistdown.service.Caminho;
import sistdown.service.Downloads;
import sistdown.service.LogsDownloads;


/**
 * Funcionalidade - Limpa <p>
 * Classe que permite que o usuário limpe os trechos que estão armazenados na maquina local.
 */
public class HandleLimpa implements Acao {

    
    public void executa() throws Exception {
        if (PromptInputs.foiSolicitadoLimpar()) {
            LogsDownloads.clear();
            limpaPastaDownloads();
        }
    }


    private void limpaPastaDownloads() {
        File temp = new File(Caminho.SISTDOWN_ROOT.toString(), "Videos-local");
        boolean isRenamed = Caminho.TARGET_DOWNLOAD.renameTo(temp);
        if (isRenamed)
            new Thread(() -> Downloads.delete(temp)).start();
        else
            Downloads.delete(Caminho.TARGET_DOWNLOAD);
        Caminho.TARGET_DOWNLOAD.mkdirs();
        System.out.print("\n * ... Pasta Limpa");
    }


}
