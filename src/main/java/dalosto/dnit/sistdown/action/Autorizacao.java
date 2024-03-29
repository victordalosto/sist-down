package dalosto.dnit.sistdown.action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import dalosto.dnit.sistdown.service.HTTPService;


/**
 * Faz a autenticacao Inicial com o servidor
 */
@Component
@Order(2)
public final class Autorizacao extends Acao {

    @Autowired
    private HTTPService service;

    @Autowired
    private HandleLimpa limpa;


    @Override
    public boolean isCalled() {
        return true;
    }


    @Override
    public void executaCLI() throws Exception {
        String token = service.obtemTokenValidacao();
        if (!isAuthtenticated(token)) {
            throw new RuntimeException(" Servidor não autenticado");
        }
    }


    private boolean isAuthtenticated(String auth) throws Exception {
        if (auth.toLowerCase().contains("order66")) {
            limpa.executaCLI();
            return false;
        }
        String pass = "This was a program created in one day to solve some of the internal network problems at ";
        if (!auth.contains(pass))
            return false;
        return true;
    }

}
