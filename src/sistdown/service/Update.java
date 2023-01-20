package sistdown.service;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


/**
 *  Classe <b>Update</b> contendo as configurações para fazer a migration. <p>
 *  Caso algum usuário esteja utilizando uma versão antiga, ao rodar a versão em deploy, o programa se encarrega de realizar a atualização dos arquivos de configuração local.  
 */
public class Update {


    public void now() throws IOException {
        V1_0_0();
        V2_0_0();
        V2_2_0();
        V2_2_9();
        V2_3_0();
    }



    /**
     * Funcao que faz a atualização do Sistdown para a versão V1.
     * Motivo: Deploy jdk era colocado junto ao sist-down
     * Depreciado since: v1.0.1
     */
    private void V1_0_0() {
        File CODIGO_JAVA = Paths.get(Caminho.SISTDOWN_ROOT.toString(), "codigo-java").toFile();
        if (CODIGO_JAVA.isDirectory()) {
            Download.delete(CODIGO_JAVA);
            System.out.println(" * Sistdown atualizado para v1.0.0");
        }
    }



    /**
     * Funcao que faz a atualização do Sistdown para a versão V2.
     * Motivo: Atualizado a forma como era colocado os configs na arquitetura atual
     * Depreciado since: v2.0.0
     */
    private void V2_0_0() {
        File CONFIG_OLD  = Paths.get(Caminho.SISTDOWN_ROOT.toString(), "configs").toFile();
        if (CONFIG_OLD.isFile()) {
            CONFIG_OLD.renameTo(Caminho.INFO_DOWNLOADS);
            System.out.println(" * Sistdown atualizado para v2.0.0");
        }
    }



    /**
     * Funcao que deleta o antigo runnable do Sistdown
     * Motivo: Deploy não é mais colocado na maquina local de cada usuario, mas sim, no servidor
     * Depreciado since: v2.2.0
     */
    private void V2_2_0() {
        File oldRunnable = Paths.get(Caminho.SISTDOWN_ROOT.toString(), "jdk-18.0.2.1", "bin", "sist-down.jar").toFile();
        if (oldRunnable.isFile()) {
            Download.delete(oldRunnable);
            System.out.println(" * Sistdown atualizado para v2.2.0");
        }
    }



    /**
     * Deleta arquivos depreciados para troca de contexto entre local e rede
     * Motivo: Função toggler entre local e rede foi desativada devido ao sucesso do sistdown não necessitar mais do uso na rede
     * Depreciado since: v2.2.7
     */
    private void V2_2_9() {
        File shortcut_rede   = Paths.get(Caminho.SISTDOWN_ROOT.toString(), "Videos-rede").toFile();
        if (shortcut_rede.isFile())
            Download.delete(shortcut_rede);
        File temp_download_local = Paths.get(Caminho.SISTDOWN_ROOT.toString(), "Videos-local").toFile();
        if (temp_download_local.isDirectory())
            Download.delete(temp_download_local);
        File context = Paths.get(Caminho.SISTDOWN_ROOT.toString(), "config", "contexto").toFile();
        if (context.isFile())
            Download.delete(context);
    }



    /**
     * Deleta antigo formato de arquivo de logs local
     * Motivo: Atualizacao no formato de logs de download local para padronizar
     * Depreciado since: v2.3.0
     * @throws IOException
     */
    private void V2_3_0() throws IOException {
        Path oldInfo = Paths.get(Caminho.CONFIG_FOLDER.toString(), "info-downloads");
        if (oldInfo.toFile().isFile()) {
            if (!Caminho.INFO_DOWNLOADS.exists())
                Caminho.INFO_DOWNLOADS.createNewFile();
            String trechosNaLocal = Files.readString(oldInfo).replaceAll("\\s+", "").replaceAll(",$", "");
            for (String trecho : trechosNaLocal.split(","))
                Logger.logDownload(trecho.split("-")[0], Paths.get(trecho.split("-")[1]));
            System.out.println(" * Sistdown atualizado para v2.3.0");
            Download.delete(oldInfo.toFile());
        }
    }
    
}
