package sistdown.service;
import java.io.File;
import java.nio.file.Paths;


/**
 *  Classe <b>Update</b> contendo as configurações para fazer a migration. <p>
 *  Caso algum usuário esteja utilizando uma versão antiga, ao rodar a versão em deploy, o programa se encarrega de realizar a atualização dos arquivos de configuração local.  
 */
public class Update {


    public void now() {
        V1();
        V2();
        V2_2();
        V2_3();
    }



    /**
     * Funcao que faz a atualização do Sistdown para a versão V1.
     * Depreciado since: v1.0.1
     */
    private void V1() {
        File CODIGO_JAVA = Paths.get(Caminho.SISTDOWN_ROOT.toString(), "codigo-java").toFile();
        if (CODIGO_JAVA.isDirectory()) {
            Downloads.delete(CODIGO_JAVA);
            System.out.println(" * Sistdown atualizado para v1.0");
        }
    }



    /**
     * Funcao que faz a atualização do Sistdown para a versão V2.
     * Depreciado since: v2.0.0
     */
    private void V2() {
        File CONFIG_OLD  = Paths.get(Caminho.SISTDOWN_ROOT.toString(), "configs").toFile();
        if (CONFIG_OLD.isFile()) {
            CONFIG_OLD.renameTo(Caminho.SISTDOWN_INFO_DOWNLOADS);
            System.out.println(" * Sistdown atualizado para v2.0");
        }
    }



    /**
     * Funcao que deleta o antigo runnable do Sistdown
     */
    private void V2_2() {
        File oldRunnable = Paths.get(Caminho.SISTDOWN_ROOT.toString(), "jdk-18.0.2.1", "bin", "sist-down.jar").toFile();
        if (oldRunnable.isFile())
            Downloads.delete(oldRunnable);
    }



    /**
     * Deleta arquivos depreciados para troca de contexto entre local e rede
     * Depreciado since: v2.2.7
     */
    private void V2_3() {
        File shortcut_rede   = Paths.get(Caminho.SISTDOWN_ROOT.toString(), "Videos-rede").toFile();
        if (shortcut_rede.isFile())
            Downloads.delete(shortcut_rede);
        File temp_download_local = Paths.get(Caminho.SISTDOWN_ROOT.toString(), "Videos-local").toFile();
        if (temp_download_local.isDirectory())
            Downloads.delete(temp_download_local);
        File context = Paths.get(Caminho.SISTDOWN_ROOT.toString(), "config", "contexto").toFile();
        if (context.isFile())
            Downloads.delete(context);
        System.out.println(context);
    }
    
}
