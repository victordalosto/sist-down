package service;
import java.io.File;
import java.nio.file.Paths;

public class Update {

    // Depreciado (since v1.0.1)
    public static File CONFIG_OLD  = Paths.get(Caminhos.SISTDOWN_ROOT.toString(), "configs").toFile();

    // Depreciado (since v2.0.0)
    public static File CODIGO_JAVA = Paths.get(Caminhos.SISTDOWN_ROOT.toString(), "codigo-java").toFile();


    /**
     * Funcao que permite a atualização do Sistdown para a versão V1.
     */
    public static void V1() {
        if (CODIGO_JAVA.isDirectory()) {
            Util.deleteFolder(CODIGO_JAVA);
            System.out.println(" * Sistdown atualizado para v1.0");
        }
    }



    /**
     * Funcao que permite a atualização do Sistdown para a V2.
     */
    public static void V2() {
        if (CONFIG_OLD.isFile()) {
            CONFIG_OLD.renameTo(Caminhos.SISTDOWN_CONFIG_INFODOWNLOADS);
            System.out.println(" * Sistdown atualizado para v2.0");
        }
    }


    
}
