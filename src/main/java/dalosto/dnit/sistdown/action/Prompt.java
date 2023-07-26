package dalosto.dnit.sistdown.action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import dalosto.dnit.sistdown.handler.PromptInputsHandler;


/**
 * Cria na tela um prompt pedindo inputs de:
 * (i)  Trechos para baixar na maquina local,
 * (ii) Tags de funcionalidades que manipulam o Sistdown.
 */
@Component
@Order(5)
public final class Prompt extends Acao {

    @Autowired
    private PromptInputsHandler promptInputsHandler;
    
       
    @Override
    public boolean isCalled() {
        return true;
    }


    @Override
    public void executa() throws Exception {
        promptInputsHandler.obtemInputs();
    }

}