package dalosto.sistdown.action;
import dalosto.sistdown.framework.annotations.Component;
import dalosto.sistdown.helper.CaminhoHelper;


/**
 * Faz o Bootstrap da aplicação:                <p>
 * (i) Cria pastas para fazer a inicialização;  <p>
 * (ii) Carrega uma List com os possiveis trechos para download.
 */
@Component
public class Inicializacao implements Acao {


    public void executa() throws Exception {
        new CaminhoHelper().initialize();
    }

}
