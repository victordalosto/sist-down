package dalosto.sistdown.service;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import dalosto.sistdown.helper.CaminhoHelper;


public class LoggerConsoleService {


    public static void printaLinhaConsole() {
        System.out.println(" --------------------------------------------------------------------------------------");
    }


    public static void printaMensagemConsole(String text) {
        System.out.println(" * " + text);
    }


    public static void appendMensagemToLog(String msg) throws IOException {
        Files.write(CaminhoHelper.FILE_TARGET_INFO_DOWNLOADS.toPath(), (msg+"\n").getBytes(), StandardOpenOption.APPEND);
    }


    public static List<String> readAllLog() throws IOException {
        return Files.readAllLines(Paths.get(CaminhoHelper.FILE_TARGET_INFO_DOWNLOADS.toString()));
    }


    public static void clearLog() throws IOException {
        FileWriter f = new FileWriter(CaminhoHelper.FILE_TARGET_INFO_DOWNLOADS, false);
        f.close();
    }

    
    public static void printaInicio() throws Exception {
        if (Util.verificaSeEhAPrimeiraVezRodandoOPrograma()) {
            System.out.println("\n SISTDOWN");
        } else {
            System.out.println("\n Downloads finalizados..");
        }
        LoggerConsoleService.printaTrechosQueEstaoNaMaquinaLocal();
        System.out.print(" > ");
    }

    
    public static synchronized String logaUmDownload(String idTrecho, Path target) throws IOException {
        String hash = target.toString().replaceAll(".+_", "");
        String uf = hash.substring(3,5);
        String br = hash.substring(0, 3);
        String append = idTrecho+";"+uf+";"+br;
        if (logEhUnicoNoArquivo(append))
            appendMensagemToLog(append);
        return formataNomeTrecho(append);
    }


    private static String formataNomeTrecho(String row) {
        if (!Util.textoEhValido(row) || !row.contains(";") || row.split(";").length != 3)
            return "[" + row + "]";
        String[] array = row.split(";");
        while (array[0].length() < 5) {
            array[0] = " " + array[0];
        }
        return "["+array[0]+" - " + array[1] + "/" + array[2] +" ]";
    }


    private static boolean logEhUnicoNoArquivo(String append) throws IOException {
        List<String> lines = readAllLog();
        for (String l : lines) {
            if (append.equals(l))
                return false;
        }
        return true;
    }

    public static void recriaLogApartirDeString(List<String> list) throws IOException {
        clearLog();
        for (String item : list) {
            Files.write(CaminhoHelper.FILE_TARGET_INFO_DOWNLOADS.toPath(), (item+"\n").getBytes(), StandardOpenOption.APPEND);
        }
    }


    public static void printaTrechosQueEstaoNaMaquinaLocal() throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(CaminhoHelper.FILE_TARGET_INFO_DOWNLOADS.toString()));
        if (lines == null || lines.size() == 0) {
            LoggerConsoleService.printaLinhaConsole();
            printaMensagemConsole("0 trechos baixados.    Digite o numero dos ids para baixar trechos");
        } else {
            LoggerConsoleService.printaLinhaConsole();
            for (int i = 0; i < lines.size(); i++) {
                if (i == 0) System.out.print("  ");
                else if (i%4 == 0)  System.out.print("\n  ");
                else System.out.print("    ");
                System.out.print(LoggerConsoleService.formataNomeTrecho(lines.get(i)));
            }
            System.out.println();
        }
        LoggerConsoleService.printaLinhaConsole();
    }
    
}
