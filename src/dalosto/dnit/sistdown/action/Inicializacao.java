package dalosto.dnit.sistdown.action;
import dalosto.dnit.sistdown.framework.annotations.Component;
import dalosto.dnit.sistdown.framework.annotations.Order;
import dalosto.dnit.sistdown.helper.CaminhoHelper;


/**
 * Faz o Bootstrap da aplicação:                <p>
 * (i) Cria pastas para fazer a inicialização;  <p>
 * (ii) Carrega uma List com os possiveis trechos para download.
 */
@Component
@Order(1)
public class Inicializacao implements Acao {


    public void executa() throws Exception {
        new CaminhoHelper().initialize();
    }

}
