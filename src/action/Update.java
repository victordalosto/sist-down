package action;
import java.io.File;
import java.nio.file.Paths;

public class Update {

    public static File CONFIG_OLD  = Paths.get(Caminhos.SISTDOWN_ROOT.toString(), "configs").toFile();
    public static File CODIGO_JAVA = Paths.get(Caminhos.SISTDOWN_ROOT.toString(), "codigo-java").toFile();



    /**
     * Funcao que permite a atualização do Sistdown da versão V1 para a V2.
     */
    public static void V2() {
        if (CODIGO_JAVA.isDirectory()) {
            Util.deleteFolder(CODIGO_JAVA);
        }
        Caminhos.criarPastas();
        if (CONFIG_OLD.isFile()) {
            CONFIG_OLD.renameTo(Caminhos.SISTDOWN_CONFIG_INFODOWNLOADS);
            System.out.println(" * Sistdown atualizado para v2.0");
        }
    }


    
}
