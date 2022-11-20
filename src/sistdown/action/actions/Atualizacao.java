package action.actions;

import service.Update;
import service.Util;

public class Atualizacao implements Acao {
    

    /**
     * Faz a atualizacao do Sistdown, 
     */
    public void executa() throws Exception {
        if (Util.primeiraRun()) {
            Update.V1();
            Update.V2();
        }
    }



}
