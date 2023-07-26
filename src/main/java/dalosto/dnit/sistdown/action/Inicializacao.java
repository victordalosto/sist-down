package dalosto.dnit.sistdown.action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import dalosto.dnit.sistdown.service.CaminhoService;


/**
 * Faz o Bootstrap da aplicação:                <p>
 * (i) Cria pastas para fazer a inicialização;  <p>
 * (ii) Carrega uma List com os possiveis trechos para download.
 */
@Component
@Order(1)
public final class Inicializacao extends Acao {

    @Autowired
    private CaminhoService caminhoService;

       
    @Override
    public boolean isCalled() {
        return true;
    }


    @Override
    public void executa() throws Exception {
        caminhoService.initialize();
    }

}
