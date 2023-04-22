package dalosto.dnit.sistdown.app;
import java.util.ArrayList;
import java.util.List;
import dalosto.dnit.sistdown.action.Acao;
import dalosto.dnit.sistdown.framework.ApplicationContext;
import dalosto.dnit.sistdown.framework.annotations.Autowired;
import dalosto.dnit.sistdown.framework.annotations.Component;
import dalosto.dnit.sistdown.service.LoggerConsoleService;


/**
 * Classe principal que faz a inicialização do Sist-down.               <p>
 * Para iniciar o sistema, basta rodar o método estático: <code>iniciar()</code>
 */
@Component
public class Sistdown {

    @Autowired
    private static List<Acao> acoes = new ArrayList<>(11);

    @Autowired
    private static LoggerConsoleService loggerConsole;
    

    /**
     * Inicia um loop com a chamada dos metodos que fazem o sistema funcionar.     <p>
     * Funciona utilizando os padrões: (i) Chain of Responsability e (ii) Strategy.
     */
    public static void inicia() {
        try {
            ApplicationContext.initialize();
            while (true) {
                for (Acao acao : acoes) {
                    acao.executa();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            loggerConsole.printaMensagem("Problema com o Sistdown. Favor, avisar o vitão.\n\n");
        }
    }

}
