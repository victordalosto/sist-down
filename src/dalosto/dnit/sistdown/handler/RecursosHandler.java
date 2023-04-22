package dalosto.dnit.sistdown.handler;
import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;


public class RecursosHandler {

    
    /**
     * Algoritmo para copiar as pastas da rede para a maquina local.
     */
    public static void walkAndCopy(Path source, Path target, CopyOption... options) throws Exception {
        Files.walkFileTree(source, new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                String pasta = dir.toString().toLowerCase();
                if (!pasta.endsWith("geo") && !pasta.endsWith("irap") && !pasta.endsWith("rinex") &&
                    !pasta.endsWith("arquivos") && !pasta.endsWith("DadosBrutos")) {
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
                    arquivo.endsWith("logstrecho.xml")) {
                        Files.copy(file, target.resolve(source.relativize(file)), options);
                    }
                return FileVisitResult.CONTINUE;
            }
        });
    }


    /**
     * Algoritmo para deletar uma pasta ou arquivo
     */
    public static void delete(File folder) {
        File[] files = folder.listFiles();
        if (files != null) { // some JVMs return null for empty dirs
            for (File f : files) {
                if (f.isDirectory()) {
                    delete(f);
                }
                else {
                    f.delete();
                }
            }
        }
        folder.delete();
    }
    
}
