package dalosto.sistdown.action;
import dalosto.sistdown.framework.annotations.Component;
import dalosto.sistdown.framework.annotations.Order;
import dalosto.sistdown.helper.CaminhoHelper;


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
