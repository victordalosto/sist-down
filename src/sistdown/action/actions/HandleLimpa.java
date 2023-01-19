package sistdown.action.actions;
import java.io.FileWriter;
import sistdown.model.InputsPrompt;
import sistdown.model.TagsConfiguracao;
import sistdown.service.Caminho;
import sistdown.service.Downloads;


/**
 * Funcionalidade - Limpa <p>
 * Classe que permite que o usuário limpe os trechos que estão armazenados na maquina local.
 */
public class HandleLimpa implements Acao {

    
    public void executa() throws Exception {
        for (int i=0; i<InputsPrompt.sizeList(); i++) {
            String param = InputsPrompt.listaComInputs.get(i);
            if (TagsConfiguracao.isClearTag(param)) {
                System.out.print("\n * .");
                InputsPrompt.listaComInputs.remove(i);
                FileWriter f = new FileWriter(Caminho.SISTDOWN_INFO_DOWNLOADS, false);
                f.close();
                System.out.print(".");
                Downloads.delete(Caminho.TARGET_ROOT);
                System.out.print(".");
                System.out.print(" Pasta Limpa");
                Caminho.TARGET_ROOT.mkdir();
            }
        }
    }


}
