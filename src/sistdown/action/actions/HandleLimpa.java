package sistdown.action.actions;
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
                Downloads.delete(Caminho.TARGET_ROOT);
                Caminho.TARGET_ROOT.mkdir();
                System.out.print("\n * ... Pasta Limpa");
            }
        }
    }


}
