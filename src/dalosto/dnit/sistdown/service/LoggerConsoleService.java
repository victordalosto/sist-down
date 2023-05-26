package dalosto.dnit.sistdown.service;
import java.io.IOException;
import java.util.List;
import dalosto.dnit.sistdown.framework.annotations.Autowired;
import dalosto.dnit.sistdown.framework.annotations.Component;


@Component
public class LoggerConsoleService {

    @Autowired
    LoggerArquivoService loggerArquivoService;


    public void printaMensagem(String text) {
        System.out.println(" * " + text);
    }

    
    public void pulaLinha(int qtd) {
        for (int i = 0; i < qtd; i++) {
            System.out.println();
        }
    }


    public void printaMensagemInicio() throws Exception {
        printaheader();
        printaMensagemTrechos();
        printaLinhaConsole();
        System.out.print(" > ");
    }


    private void printaheader() {
        if (Util.verificaSeEhAPrimeiraVezRodandoOPrograma()) {
            System.out.println("\n SISTDOWN");
        } else {
            System.out.println("\n\n Downloads finalizados..");
        }
    }


    private void printaMensagemTrechos() throws IOException {
        List<String> lines = loggerArquivoService.readAllLog();
        if (lines == null || lines.size() == 0) {
            printaLinhaConsole();
            printaMensagem("0 trechos baixados.");
        } else {
            printaLinhaConsole();
            for (int i = 0; i < lines.size(); i++) {
                if (i == 0)
                    System.out.print("  ");
                else if (i % 4 == 0)
                    System.out.print("\n  ");
                else
                    System.out.print("    ");
                System.out.print(loggerArquivoService.formataNomeTrecho(lines.get(i)));
            }
            System.out.println();
        }
    }


    private void printaLinhaConsole() {
        System.out.println(" --------------------------------------------------------------------------------------");
    }

}
