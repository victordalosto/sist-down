package dalosto.sistdown.action.actions;
import dalosto.sistdown.service.Caminho;


/**
 * Faz o Bootstrap da aplicação:                <p>
 * (i) Cria pastas para fazer a inicialização;  <p>
 * (ii) Carrega uma List com os possiveis trechos para download.
 */
public class Inicializacao implements Acao {


    public void executa() throws Exception {
        new Caminho().init();
    }

}
