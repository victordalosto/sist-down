package service;
import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import model.Trechos;

public class Util {

    public static String contexto;
    public static AtomicInteger inicializacoes = new AtomicInteger(0);

    
    /**
     * Verifica se é a primeira vez rodando
     * ou se é uma reinicialização do Sistdown.
     */
    public static boolean primeiraRun() {
        if (inicializacoes.get() == 0)
            return true;
        return false;
    }



    /**
     * Carrega do CSV local, os trechos que estão disponíveis para download.
     * CSV format  :  id;path  :  13373;\\10.100.10.219\Videos\Recebidos\2022\Lote2\3205\28_09_202213373_020RO0000000
     */
    public static void carregaDoLocalUmaListComTrechosDisponiveis() throws Exception {
        try(Scanner scanner = new Scanner(Caminhos.pathCSVComTrechosDisponiveis)) {
            while (scanner.hasNextLine()) {
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
        if (text != null && !text.isBlank()) 
            return true;
        return false;

    }



    /**
     * Algoritmo para copiar as pastas da rede para a maquina local
     */
    public static void copyFolder(Path source, Path target, CopyOption... options) throws Exception {
        Files.walkFileTree(source, new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                String pasta = dir.toString().toLowerCase();
                if (!pasta.endsWith("geo") && !pasta.endsWith("irap")) {
                    Files.createDirectories(target.resolve(source.relativize(dir)));
                    return FileVisitResult.CONTINUE;
                }
                return FileVisitResult.SKIP_SUBTREE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                String arquivo = file.toString().toLowerCase();
                if (arquivo.endsWith(".mp4")  || arquivo.endsWith(".avi") || arquivo.endsWith(".flv") || 
                    arquivo.endsWith(".jpeg") || arquivo.endsWith(".jpg") || arquivo.endsWith(".png") || 
                    arquivo.endsWith("logstrecho.xml"))
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
