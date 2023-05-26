package dalosto.dnit.sistdown.action;
import dalosto.dnit.sistdown.framework.annotations.Component;
import dalosto.dnit.sistdown.framework.annotations.Order;
import dalosto.dnit.sistdown.service.Util;


/**
 * Classe que finaliza o ciclo das ações, preparando o Sistema para ser reiniciado automicamente. <p>
 */
@Component
@Order(10)
public class Fim implements Acao {

    
    public void executa() throws Exception {
        Util.reiniciaOPrograma();
    }
    
}
