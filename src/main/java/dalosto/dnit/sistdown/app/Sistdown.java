package dalosto.dnit.sistdown.app;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import dalosto.dnit.sistdown.action.Acao;
import dalosto.dnit.sistdown.service.LoggerConsoleService;


/**
 * Classe principal que faz a inicialização do Sist-down.       <p>
 * Para iniciar o sistema, basta rodar o método estático: iniciar()
 */
@Component
public final class Sistdown {

    @Autowired
    private List<Acao> acoes;

    @Autowired
    private LoggerConsoleService logger;


    /**
     * Inicia um loop com a chamada dos metodos que fazem o sistema funcionar.     <p>
     * @throws InterruptedException
     */
    public void inicia() throws Exception {
        while (true) {
            try {
                for (Acao acao : acoes) {
                    acao.run();
                }
            } catch (RuntimeException e) {
                System.out.println("\n\n");
                logger.printaMensagem("Sist-down encerrou com erro.");
                logger.printaMensagem(" Motivo:");
                logger.printaMensagem(e.getMessage());
                Thread.sleep(30000l);
                throw new RuntimeException(e);
            } catch (Exception e) {
                e.printStackTrace();
                logger.printaMensagem("Problema com o Sistdown. Favor, avisar o vitão.\n\n");
            }
        }
    }

}
