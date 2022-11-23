package sistdown.action.actions;
import sistdown.service.Update;
import sistdown.service.Util;


/**
 * Faz a atualizacao do Sistdown, permitindo realizar migration. <p>
 * Caso algum usuário esteja rodando uma versão antiga, ao rodar a versão em deploy, 
 * o programa se encarrega de realizar a atualização dos arquivos de configuração para rodar a versão mais recente.  
 */
public class Atualizacao implements Acao {

    public void executa() throws Exception {
        if (Util.primeiraRun()) {
            Update.V1();
            Update.V2();
        }
    }

}
