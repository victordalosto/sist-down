package dalosto.sistdown.action;
import dalosto.sistdown.framework.annotations.Component;
import dalosto.sistdown.service.LoggerConsoleService;


/**
 * Faz o print na tela dos textos de inicialização.      <p>
 * Printa a logo de inicio ou a logo de reinicializacao,
 * bem como também printa os trechos que foram baixados.
 */
@Component
public class PrintaInicio implements Acao {
    
    
    public void executa() throws Exception {
        LoggerConsoleService.printaInicio();
    }

}
