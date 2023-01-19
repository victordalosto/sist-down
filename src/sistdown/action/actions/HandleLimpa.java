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
                InputsPrompt.listaComInputs.remove(i);
                Downloads.delete(Caminho.TARGET_ROOT);
                FileWriter f = new FileWriter(Caminho.SISTDOWN_CONFIG_INFODOWNLOADS, false);
                f.close();
                Caminho.TARGET_ROOT.mkdir();
                System.out.println(" * ...Pasta Limpa");
            }
        }
    }


}
