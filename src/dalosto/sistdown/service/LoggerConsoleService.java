package dalosto.sistdown.service;
import java.util.List;
import dalosto.sistdown.framework.annotations.Autowired;
import dalosto.sistdown.framework.annotations.Component;


@Component
public class LoggerConsoleService {

    @Autowired
    LoggerArquivoService loggerArquivoService;


    public void printaLinhaConsole() {
        System.out.println(" --------------------------------------------------------------------------------------");
    }


    public void printaMensagem(String text) {
        System.out.println(" * " + text);
    }


    public void printaMensagemInicio() throws Exception {
        if (Util.verificaSeEhAPrimeiraVezRodandoOPrograma()) {
            System.out.println("\n SISTDOWN");
        } else {
            System.out.println("\n Downloads finalizados..");
        }
        List<String> lines = loggerArquivoService.readAllLog();
        if (lines == null || lines.size() == 0) {
            printaLinhaConsole();
            printaMensagem("0 trechos baixados.");
        } else {
            printaLinhaConsole();
            for (int i = 0; i < lines.size(); i++) {
                if (i == 0) System.out.print("  ");
                else if (i%4 == 0)  System.out.print("\n  ");
                else System.out.print("    ");
                System.out.print(loggerArquivoService.formataNomeTrecho(lines.get(i)));
            }
            System.out.println();
        }
        printaLinhaConsole();
        System.out.print(" > ");
    }

}
