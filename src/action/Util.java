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

public class Util {


    public static boolean isValid(String text) {
        if (text != null && !text.equals("") && !text.equals(" ")) 
            return true;
        return false;

    }


    public static void printaTrechosNaMaquinaLocal(String contexto) throws Exception {
        String trechosNaLocal = Files.readString(Paths.get(Caminhos.SISTDOWN_CONFIG_INFODOWNLOADS.toString())).replaceAll(", $", "");
        if (contexto.equalsIgnoreCase("local")) {
            if (trechosNaLocal.equals("")) {
                System.out.println(" * 0 trechos baixados.");
            } else {
                System.out.println(" * Trechos que estão na pasta:");
                System.out.println(" * " + trechosNaLocal);
            }
        }
    }



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
