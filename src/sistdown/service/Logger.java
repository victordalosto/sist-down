package sistdown.service;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;


public class Logger {

    
    public static synchronized String logDownload(String idTrecho, Path target) throws IOException {
        String hash = target.toString().replaceAll(".+_", "");
        String uf = hash.substring(3,5);
        String br = hash.substring(0, 3);
        String append = idTrecho+";"+uf+";"+br;
        if (logEhUnicoNoArquivo(append))
            Files.write(Caminho.INFO_DOWNLOADS.toPath(), (append+"\n").getBytes(), StandardOpenOption.APPEND);
        return format(append);
    }



    private static boolean logEhUnicoNoArquivo(String append) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(Caminho.INFO_DOWNLOADS.toString()));
        for (String l : lines) {
            if (append.equals(l))
                return false;
        }
        return true;
    }



    private static String format(String row) {
        if (!Util.textEhValido(row) || !row.contains(";") || row.split(";").length != 3)
            return "[" + row + "]";
        String[] array = row.split(";");
        while (array[0].length() < 5) {
            array[0] = " " + array[0];
        }
        return "[ "+array[0]+" - " + array[1] + "/" + array[2] +" ]";
    }



    public static void clear() throws IOException {
        FileWriter f = new FileWriter(Caminho.INFO_DOWNLOADS, false);
        f.close();
    }



    public static void printaTrechosQueEstaoNaMaquinaLocal() throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(Caminho.INFO_DOWNLOADS.toString()));
        if (lines == null || lines.size() == 0) {
            System.out.println(" ======================================================================================");
            System.out.println(" * 0 trechos baixados.    Digite o numero dos ids para baixar trechos");
        } else {
            System.out.println(" ================================== TRECHOS BAIXADOS ==================================");
            for (int i = 0; i < lines.size(); i++) {
                if (i == 0) System.out.print("  ");
                else if (i%4 == 0)  System.out.print("\n  ");
                else System.out.print("    ");
                System.out.print(Logger.format(lines.get(i)));
            }
            System.out.println();
        }
        System.out.println(" ======================================================================================");
    }

    
}
