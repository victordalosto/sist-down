package action.actions;

import service.Util;

public class Fim implements Acao {

    /**
     * Cria alguns separadores de linhas e 
     * informa a reinicialização do Sistdown.
     */
    public void executa() throws Exception {
        Util.inicializacoes.incrementAndGet();
        System.out.println("\n\n\n\n\n\n\n");
    }
    
}
