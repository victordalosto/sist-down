package dalosto.dnit.sistdown.service;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public final class LoggerConsoleService {

    @Autowired
    private ArquivoService arquivoService;


    public void printaMensagem(String text) {
        System.out.println(" * " + text);
    }


    public void pulaLinha(int qtd) {
        for (int i = 0; i < qtd; i++) {
            System.out.println();
        }
    }


    public void printaMensagemInicio() throws Exception {
        printaCabecalho();
        printaLinhaSeparadora();
        printaTrechosBaixados();
        printaLinhaSeparadora();
        System.out.print(" > ");
    }


    private void printaCabecalho() {
        if (Util.ehAPrimeiraVezRodandoOPrograma()) {
            System.out.println("\n SISTDOWN -  v2.6.0");
        } else {
            System.out.println("\n\n Downloads finalizados..");
        }
    }


    private void printaTrechosBaixados() throws IOException {
        var trechos = arquivoService.getTrechosBaixados();
        if (trechos == null || trechos.size() == 0) {
            printaMensagem("0 trechos baixados.");
        } else {
            for (int i = 0; i < trechos.size(); i++) {
                if (i == 0)
                    System.out.print("  ");
                else if (i % 4 == 0)
                    System.out.print("\n  ");
                else
                    System.out.print("    ");
                System.out.print(trechos.get(i).toString());
            }
            System.out.println();
        }
    }


    private void printaLinhaSeparadora() {
        System.out.println(" --------------------------------------------------------------------------------------");
    }

}
