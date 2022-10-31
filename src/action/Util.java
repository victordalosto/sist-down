package action;
import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import model.Trechos;

public class Util {

    /**
     * Carrega do banco local, os trechos que estão disponíveis para download.
     */
    public static void carregaTrechosDisponiveis() throws Exception {
        try(Scanner scanner = new Scanner(Caminhos.pathCSVComTrechosDisponiveis)) {
            while (scanner.hasNext()) {
                String[] row = scanner.nextLine().split(";");
                Trechos.addTrecho(row[0], row[1]);
            }
        }
    }



    /**
     * Verifica se o texto digitado é um input valido.
     * Pode ser um trecho ou um texto de config.
     */
    public static boolean isValid(String text) {
        if (text != null && !text.equals("") && !text.equals(" ")) 
            return true;
        return false;

    }


    /**
     * Printa os trechos que estão baixados na maquina local.
     */
    public static void printaTrechosQueEstaoNaMaquinaLocal() throws Exception {
        String trechosNaLocal = Files.readString(Paths.get(Caminhos.SISTDOWN_CONFIG_INFODOWNLOADS.toString())).replaceAll("\\s+", "").replaceAll(",$", "");
        if (trechosNaLocal.equals("")) {
            System.out.println(" * 0 trechos baixados.");
        } else {
            System.out.println(" * Trechos que estão na pasta:");
            Set<String> trechos = new HashSet<>();
            if (!trechosNaLocal.contains(",")) {
                trechos.add(trechosNaLocal);
            } else {
                String [] rows = trechosNaLocal.split(",");
                for (int i = 0; i < rows.length -1; i++) {
                    if (Util.isValid(rows[i]))
                        trechos.add(rows[i]);
                }
            }
            System.out.println(" * " + trechos);
        }
    }



    /**
     * Algoritmo para copiar as pastas da rede para a maquina local
     */
    public static void copyFolder(Path source, Path target, CopyOption... options) throws Exception {
        Files.walkFileTree(source, new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                Files.createDirectories(target.resolve(source.relativize(dir)));
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.copy(file, target.resolve(source.relativize(file)), options);
                return FileVisitResult.CONTINUE;
            }
        });
    }



    /**
     * Algoritmo para deletar uma pasta
     */
    public static void deleteFolder(File folder) {
        File[] files = folder.listFiles();
        if (files != null) { // some JVMs return null for empty dirs
            for (File f : files) {
                if (f.isDirectory())
                    deleteFolder(f);
                else
                    f.delete();
            }
        }
        folder.delete();
    }
}
