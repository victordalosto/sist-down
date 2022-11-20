package action.actions;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import service.Caminhos;
import service.FrasesPedrao;
import service.Util;

public class PrintaInicio implements Acao {
    

    /**
     * Faz o print na tela dos textos de inicialização.
     * Printa a logo de inicio ou a logo de reinicializacao,
     * bem como também printa os trechos que foram baixados.
     */
    public void executa() throws Exception {
        if (Util.primeiraRun()) {
            printaLinha();
            System.out.print(getLogo());
        } else {
            System.out.println(" * SISTDOWN Reiniciado. MODO: " + Util.contexto);
        }
        System.out.println(" * " + FrasesPedrao.getRandom()); 
        printaTrechosQueEstaoNaMaquinaLocal();
        if (Util.primeiraRun()) {
            System.out.println(" * Para 'baixar' trechos, digite os ids separados por virgula.  Ex: 100, 101, 102");
            System.out.println(" * Para 'limpar' e também baixar acrescente o parametro limpa.  Ex: limpa, 85, 86");
            printaLinha();    
        }
        System.out.print(" FLY-now> ");
    }



    private static void printaLinha() {
        System.out.println(" ================================================================================");
    }

    

    /**
     * Printa os trechos que estão baixados na maquina local.
     */
    private static void printaTrechosQueEstaoNaMaquinaLocal() throws Exception {
        printaLinha();
        String trechosNaLocal = Files.readString(Paths.get(Caminhos.SISTDOWN_CONFIG_INFODOWNLOADS.toString())).replaceAll("\\s+", "").replaceAll(",$", "");
        if (trechosNaLocal.equals("")) {
            System.out.println(" * 0 trechos baixados.");
        } else {
            System.out.print(" * Baixados: ");
            Set<String> trechos = new HashSet<>();
            if (!trechosNaLocal.contains(",")) {
                trechos.add(trechosNaLocal);
            } else {
                String [] rows = trechosNaLocal.split(",");
                for (int i = 0; i < rows.length; i++) {
                    if (Util.isValid(rows[i]))
                        trechos.add(rows[i]);
                }
            }
            System.out.println(trechos);
        }
        printaLinha();
    }



    private static String getLogo() {
        LocalDate hoje = LocalDate.now();
        DayOfWeek dia = hoje.getDayOfWeek();
        String message;
        if (dia.equals(DayOfWeek.FRIDAY)) {
            message = """
                       _____               _                                           _ 
                      |  ___|_ _ _   _ ___| |_ ___    _ __ ___   __ _ _ __   ___   ___| |
                      | |_ / _` | | | / __| __/ _ \\  | '_ ` _ \\ / _` | '_ \\ / _ \\ / _ \\ |
                      |  _| (_| | |_| \\__ \\ || (_) | | | | | | | (_| | | | | (_) |  __/ |
                      |_|  \\__,_|\\__,_|___/\\__\\___/  |_| |_| |_|\\__,_|_| |_|\\___/ \\___|_|
                                                                                     
                    """;
        } else {
            message = """
                      __  _
                    /  _|| |_   _   _ __   _____      __
                    | |_ | | | | | | '_ \\ / _ \\ \\ /\\ / /
                    |  _|| | |_| | | | | | (_) \\ V  V /
                    |_|  |_|\\__, | |_| |_|\\___/ \\_/\\_/                     
                            |___/
                
                """;
        }
        return message;
    }



}
