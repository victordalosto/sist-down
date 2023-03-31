package dalosto.sistdown.action;
import dalosto.sistdown.framework.annotations.Component;
import dalosto.sistdown.service.Util;


/**
 * Classe que finaliza o ciclo das ações, preparando o Sistema para ser reiniciado automicamente. <p>
 */
@Component
public class Fim implements Acao {

    
    public void executa() throws Exception {
        Util.iniciaNovamenteOPrograma();
    }
    
}
