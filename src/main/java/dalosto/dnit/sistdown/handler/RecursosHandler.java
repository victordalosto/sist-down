package dalosto.dnit.sistdown.handler;
import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import org.springframework.stereotype.Component;


@Component
public final class RecursosHandler {

    
    /**
     * Algoritmo para copiar as pastas da rede para a maquina local.
     */
    public void walkAndCopy(Path source, Path target, CopyOption... options) throws Exception {
        Files.walkFileTree(source, new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                String pasta = dir.toString().toLowerCase();
                if (validDirectory(pasta)) {
                    Files.createDirectories(target.resolve(source.relativize(dir)));
                    return FileVisitResult.CONTINUE;
                }
                return FileVisitResult.SKIP_SUBTREE;
            }


            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                String fileIsValid = file.toString().toLowerCase();
                if (validFiles(fileIsValid)) {
                    Files.copy(file, target.resolve(source.relativize(file)), options);
                }
                return FileVisitResult.CONTINUE;
            }


            private boolean validDirectory(String pasta) {
                pasta = pasta.toLowerCase();
                return !pasta.endsWith("geo") && !pasta.endsWith("irap") && !pasta.endsWith("rinex") &&
                       !pasta.endsWith("arquivos") && !pasta.endsWith("dadosbrutos");
            }


            private boolean validFiles(String arquivo) {
                arquivo = arquivo.toLowerCase();
                return arquivo.endsWith(".mp4")  || arquivo.endsWith(".avi") || arquivo.endsWith(".flv") || 
                       arquivo.endsWith(".jpeg") || arquivo.endsWith(".jpg") || arquivo.endsWith(".png") || 
                       arquivo.endsWith("logstrecho.xml");
            }
        });
    }


    /**
     * Algoritmo para deletar uma pasta ou arquivo
     */
    public void delete(File folder) {
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
