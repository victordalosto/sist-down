package sistdown.action.actions;
import java.io.File;
import java.io.FileWriter;
import sistdown.model.InputsPrompt;
import sistdown.service.Caminho;
import sistdown.service.Util;


/**
 * Funcionalidade - Limpa <p>
 * Classe que permite que o usuário limpe os trechos que estão armazenados na maquina local.
 */
public class HandleLimpa implements Acao {

    
    public void executa() throws Exception {
        for (int i=0; i<InputsPrompt.sizeList(); i++) {
            String param = InputsPrompt.listaComInputs.get(i);
            if (param.equalsIgnoreCase("limpar") || param.equalsIgnoreCase("limpa") || param.equalsIgnoreCase("limp")) {
                InputsPrompt.listaComInputs.remove(i);
                File caminhoParaLimpar;
                if(Caminho.SISTDOWN_DOWNLOADS_LOCAL.isDirectory())
                    caminhoParaLimpar = Caminho.SISTDOWN_DOWNLOADS_LOCAL;
                else 
                    caminhoParaLimpar = Caminho.SISTDOWN_CURRENT;

                Util.deleteFolder(caminhoParaLimpar);
                FileWriter f = new FileWriter(Caminho.SISTDOWN_CONFIG_INFODOWNLOADS, false);
                f.close();
                caminhoParaLimpar.mkdir();
                System.out.println(" * ...Pasta Limpa");
            }
        }
    }


}
