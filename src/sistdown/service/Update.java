package sistdown.service;
import java.io.File;
import java.nio.file.Paths;


/**
 *  Classe <b>Update</b> contendo as configurações para fazer a migration. <p>
 *  Caso algum usuário esteja utilizando uma versão antiga, ao rodar a versão em deploy, o programa se encarrega de realizar a atualização dos arquivos de configuração local.  
 */
public class Update {


    public static void now() {
        V1();
        V2();
        V2_2();
    }

    

    /**
     * Funcao que faz a atualização do Sistdown para a versão V1.
     * Depreciado since: v1.0.1
     */
    private static void V1() {
        File CODIGO_JAVA = Paths.get(Caminho.SISTDOWN_ROOT.toString(), "codigo-java").toFile();
        if (CODIGO_JAVA.isDirectory()) {
            Util.deleteFolder(CODIGO_JAVA);
            System.out.println(" * Sistdown atualizado para v1.0");
        }
    }



    /**
     * Funcao que faz a atualização do Sistdown para a versão V2.
     * Depreciado since: v2.0.0
     */
    private static void V2() {
        File CONFIG_OLD  = Paths.get(Caminho.SISTDOWN_ROOT.toString(), "configs").toFile();
        if (CONFIG_OLD.isFile()) {
            CONFIG_OLD.renameTo(Caminho.SISTDOWN_CONFIG_INFODOWNLOADS);
            System.out.println(" * Sistdown atualizado para v2.0");
        }
    }



    /**
     * Funcao que deleta o antigo runnable do Sistdown
     */
    private static void V2_2() {
        File oldRunnable = Paths.get(Caminho.SISTDOWN_ROOT.toString(), "jdk-18.0.2.1", "bin", "sist-down.jar").toFile();
        if (oldRunnable.isFile())
            Util.deleteFolder(oldRunnable);
    }
    
}
