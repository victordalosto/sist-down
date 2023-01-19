package sistdown.service;
import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import sistdown.Main;
import sistdown.model.Version;


/**
 *  Classe <b>Util</b> contendo métodos estáticos utilitários. <p>
 *  Funções gerais utilizadas fora dos contextos das classes construídas.  
 */
public class Util {

    public static String contexto;
    private static boolean primeiraInicializacao = true;


    public static boolean verificaSeEhAPrimeiraVezRodandoOPrograma() {
        if (primeiraInicializacao)
            return true;
        return false;
    }


    /**
     * Reinicia o Loop do programa, informando que não é a primeira vez rodando o programa
     */
    public static void iniciaNovamenteOPrograma() {
        primeiraInicializacao = false;
    }



    /**
     * Verifica se o texto digitado é um input valido. Pode ser um trecho ou uma Tag.
     */
    public static boolean isValid(String text) {
        if (text != null && !text.isBlank()) 
            return true;
        return false;

    }



    /**
     * Algoritmo para copiar as pastas da rede para a maquina local.
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


    
    /**
     * @return Current version of Sistdown
     */
    public static String getVersion() {
        Class<?> main = Main.class;
        Version version = main.getDeclaredAnnotation(Version.class);
        return version.value();
    }
    
}
