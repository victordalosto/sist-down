package dalosto.sistdown.action;
import dalosto.sistdown.framework.annotations.Component;
import dalosto.sistdown.framework.annotations.Order;
import dalosto.sistdown.handler.PromptInputsHandler;


/**
 * Cria na tela um prompt pedindo inputs de:
 * (i)  Trechos para baixar na maquina local,
 * (ii) Tags de funcionalidades que manipulam o Sistdown.
 */
@Component
@Order(5)
public class Prompt implements Acao {
    
    public void executa() throws Exception {
        PromptInputsHandler.obtemInputs();
    }

}