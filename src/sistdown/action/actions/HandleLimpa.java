package action.actions;
import java.io.File;
import java.io.FileWriter;
import model.Inputs;
import service.Caminhos;
import service.Util;

public class HandleLimpa implements Acao {

    /**
     * Faz o tratamento caso tenha sido inseridos inputs de Configuração.
     * Faz a limpeza dos trechos guardados na maquina local,
     */
    public void executa() throws Exception {
        for (int i=0; i<Inputs.size(); i++) {
            String param = Inputs.listaComInputs.get(i);
            if (param.equalsIgnoreCase("limpar") || param.equalsIgnoreCase("limpa") || param.equalsIgnoreCase("limp")) {
                Inputs.listaComInputs.remove(i);
                File caminhoParaLimpar;
                if(Caminhos.SISTDOWN_DOWNLOADS_LOCAL.isDirectory())
                    caminhoParaLimpar = Caminhos.SISTDOWN_DOWNLOADS_LOCAL;
                else 
                    caminhoParaLimpar = Caminhos.SISTDOWN_CURRENT;

                Util.deleteFolder(caminhoParaLimpar);
                FileWriter f = new FileWriter(Caminhos.SISTDOWN_CONFIG_INFODOWNLOADS, false);
                f.close();
                caminhoParaLimpar.mkdir();
                System.out.println(" * ...Pasta Limpa");
            }
        }
    }


}
