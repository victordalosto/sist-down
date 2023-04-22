package dalosto.dnit.sistdown.action;
import dalosto.dnit.sistdown.framework.annotations.Autowired;
import dalosto.dnit.sistdown.framework.annotations.Component;
import dalosto.dnit.sistdown.framework.annotations.Order;
import dalosto.dnit.sistdown.service.LoggerConsoleService;


/**
 * Faz o print na tela dos textos de inicialização.      <p>
 * Printa a logo de inicio ou a logo de reinicializacao,
 * bem como também printa os trechos que foram baixados.
 */
@Component
@Order(4)
public class PrintaInicio implements Acao {

    @Autowired
    LoggerConsoleService loggerConsoleService;


    public void executa() throws Exception {
        loggerConsoleService.printaMensagemInicio();
    }

}
