package dalosto.company.sistdown.action;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import dalosto.company.sistdown.service.Util;


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
    public void executaCLI() throws Exception {
        Util.reiniciaOPrograma();
    }

}
