package dalosto.sistdown.action.actions;
import dalosto.sistdown.Handler.PromptInputsHandler;


/**
 * Cria na tela um prompt pedindo inputs de:
 * (i)  Trechos para baixar na maquina local,
 * (ii) Tags de funcionalidades que manipulam o Sistdown.
 */
public class Prompt implements Acao {
    
    public void executa() throws Exception {
        PromptInputsHandler.obtemInputs();
    }

}