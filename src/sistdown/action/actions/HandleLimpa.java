package sistdown.action.actions;
import java.io.File;
import sistdown.model.InputsPrompt;
import sistdown.model.TagsConfiguracao;
import sistdown.service.Caminho;
import sistdown.service.Downloads;
import sistdown.service.LogsDownloads;


/**
 * Funcionalidade - Limpa <p>
 * Classe que permite que o usuário limpe os trechos que estão armazenados na maquina local.
 */
public class HandleLimpa implements Acao {

    
    public void executa() throws Exception {
        for (int i=0; i<InputsPrompt.sizeList(); i++) {
            String param = InputsPrompt.listaComInputs.get(i);
            if (TagsConfiguracao.isClearTag(param)) {
                InputsPrompt.listaComInputs.remove(i);
                LogsDownloads.clear();
                clearTheDownLoadTargetFolder();
            }
        }
    }


    private void clearTheDownLoadTargetFolder() {
        File temp = new File(Caminho.SISTDOWN_ROOT.toString(), "Videos-local");
        boolean isRenamed = Caminho.TARGET_DOWNLOAD.renameTo(temp);
        if (isRenamed) {
            new Thread(() -> {
                Downloads.delete(temp);
            }).start(); ;
        } else {
            Downloads.delete(Caminho.TARGET_DOWNLOAD);
        }
        Caminho.TARGET_DOWNLOAD.mkdirs();
        System.out.print("\n * ... Pasta Limpa");
    }


}
