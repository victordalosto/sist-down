package sistdown.service;
import java.io.File;
import java.nio.file.Paths;


/**
 *  Classe <b>Update</b> contendo as configurações para fazer a migration. <p>
 *  Caso algum usuário esteja utilizando uma versão antiga, ao rodar a versão em deploy, o programa se encarrega de realizar a atualização dos arquivos de configuração local.  
 */
public class Update {

    // Depreciado (since v1.0.1)
    public static File CODIGO_JAVA = Paths.get(Caminho.SISTDOWN_ROOT.toString(), "codigo-java").toFile();
    
    // Depreciado (since v2.0.0)
    public static File CONFIG_OLD  = Paths.get(Caminho.SISTDOWN_ROOT.toString(), "configs").toFile();
    

    /**
     * Funcao que faz a atualização do Sistdown para a versão V1.
     */
    public static void V1() {
        if (CODIGO_JAVA.isDirectory()) {
            Util.deleteFolder(CODIGO_JAVA);
            System.out.println(" * Sistdown atualizado para v1.0");
        }
    }



    /**
     * Funcao que faz a atualização do Sistdown para a versão V2.
     */
    public static void V2() {
        if (CONFIG_OLD.isFile()) {
            CONFIG_OLD.renameTo(Caminho.SISTDOWN_CONFIG_INFODOWNLOADS);
            System.out.println(" * Sistdown atualizado para v2.0");
        }
    }



    /**
     * Funcao que adapta o Sist-down apos os problemas no banco
     * Data: 29/11/2022
     */
    public static void V2_2() {
        
    }
    
}
