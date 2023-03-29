package dalosto.sistdown.app;
import java.util.ArrayList;
import java.util.List;
import dalosto.sistdown.action.Acao;
import dalosto.sistdown.action.Atualizacao;
import dalosto.sistdown.action.Autorizacao;
import dalosto.sistdown.action.Fim;
import dalosto.sistdown.action.HandleApaga;
import dalosto.sistdown.action.HandleDownload;
import dalosto.sistdown.action.HandleHelp;
import dalosto.sistdown.action.HandleLimpa;
import dalosto.sistdown.action.Inicializacao;
import dalosto.sistdown.action.PrintaInicio;
import dalosto.sistdown.action.Prompt;
import dalosto.sistdown.service.Registrador;


/**
 * Classe principal que faz a inicialização do <b>Sist-down</b>.<p>
 * Para iniciar o sistema basta rodar o método estático: <code>iniciar()</code>
 */
public class Sistdown {

    /** Lista com as ações executadas pelo sistema. */
    private static List<Acao> acoes = new ArrayList<>(11);
    
    
    /** Cria o encademaneto das ações. */
    static {
        acoes.add(new Atualizacao());
        acoes.add(new Autorizacao());
        acoes.add(new Inicializacao());
        acoes.add(new PrintaInicio());
        acoes.add(new Prompt());
        acoes.add(new HandleApaga());
        acoes.add(new HandleHelp());
        acoes.add(new HandleLimpa());
        acoes.add(new HandleDownload());
        acoes.add(new Fim());
    }



    /**
     * Inicia um loop com a chamada dos metodos que fazem o sistema funcionar.     <p>
     * Funciona utilizando os padrões: (i) Chain of Responsability e (ii) Strategy.
     */
    public static void inicia() {
        try {
            while (true) {
                for (Acao acao : acoes) {
                    acao.executa();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Registrador.printaMensagemConsole("Problema com o Sistdown. Favor, avisar o vitão.\n\n");
        }
    }

}
