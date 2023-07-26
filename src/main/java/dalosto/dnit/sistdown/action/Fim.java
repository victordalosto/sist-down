package dalosto.dnit.sistdown.action;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import dalosto.dnit.sistdown.service.Util;


/**
 * Classe que finaliza o ciclo das ações, preparando o Sistema para ser reiniciado automicamente. <p>
 */
@Component
@Order(100)
public final class Fim extends Acao {

       
    @Override
    public boolean isCalled() {
        return true;
    }


    @Override
    public void executa() throws Exception {
        Util.reiniciaOPrograma();
    }
    
}
