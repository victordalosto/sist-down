package action;

import java.io.File;
import java.nio.file.Paths;

public class Update {

    public static File CONFIG = Paths.get(Caminhos.SISTDOWN_ROOT.toString(), "configs").toFile();
    public static File CODIGO_JAVA = Paths.get(Caminhos.SISTDOWN_ROOT.toString(), "codigo-java").toFile();



    public static void v2() {
        Util.deleteFolder(CODIGO_JAVA);
        Caminhos.criarPastas();
        if (CONFIG.isFile())
            CONFIG.renameTo(Caminhos.SISTDOWN_CONFIG_INFODOWNLOADS);
    }


    
}
