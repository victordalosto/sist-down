package action;
import java.util.ArrayList;
import java.util.List;
import action.actions.Acao;
import action.actions.Atualizacao;
import action.actions.Fim;
import action.actions.HandleContexto;
import action.actions.HandleDownload;
import action.actions.HandleLimpa;
import action.actions.Inicializacao;
import action.actions.PrintaInicio;
import action.actions.Prompt;

public class Sistdown {

    private static List<Acao> acoes = new ArrayList<>();
    
    static {
        acoes.add(new Inicializacao());
        acoes.add(new Atualizacao());
        acoes.add(new PrintaInicio());
        acoes.add(new Prompt());
        acoes.add(new HandleContexto());
        acoes.add(new HandleLimpa());
        acoes.add(new HandleDownload());
        acoes.add(new Fim());
    }


    /**
     * Inicia o loop com a chamada dos metodos dos eventos encadeados - chain
     */
    public static void inicia() throws Exception {
        while (true) {
            for (Acao acao : acoes)
                acao.executa();
        }
    }




}
