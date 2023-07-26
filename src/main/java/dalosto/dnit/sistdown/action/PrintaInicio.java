package dalosto.dnit.sistdown.action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import dalosto.dnit.sistdown.service.LoggerConsoleService;


/**
 * Faz o print na tela dos textos de inicialização.      <p>
 * Printa a logo de inicio ou a logo de reinicializacao,
 * bem como também printa os trechos que foram baixados.
 */
@Component
@Order(4)
public final class PrintaInicio extends Acao {

    @Autowired
    private LoggerConsoleService loggerConsoleService;

       
    @Override
    public boolean isCalled() {
        return true;
    }


    @Override
    public void executa() throws Exception {
        loggerConsoleService.printaMensagemInicio();
    }

}
