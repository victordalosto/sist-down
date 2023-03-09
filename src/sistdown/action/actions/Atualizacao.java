package sistdown.action.actions;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import sistdown.Handler.RecursosHandler;
import sistdown.service.Caminho;
import sistdown.service.Registrador;
import sistdown.service.Util;


/**
 * Faz a atualizacao do Sistdown, permitindo realizar migration. <p>
 * Caso algum usuário esteja rodando uma versão antiga, ao rodar a versão em deploy, 
 * o programa se encarrega de realizar a atualização dos arquivos de configuração para rodar a versão mais recente.  
 */
public class Atualizacao implements Acao {

    
    public void executa() throws Exception {
        if (Util.verificaSeEhAPrimeiraVezRodandoOPrograma()) {
            v1_0_0();
            v2_0_0();
            v2_2_0();
            v2_2_9();
            v2_3_0();
            v2_4_0();
            v2_4_2();
        }
    }


    /**
     * Funcao que faz a atualização do Sistdown para a versão V1.
     * Motivo: Deploy jdk era colocado junto ao sist-down
     * Depreciado since: v1.0.1
     */
    private void v1_0_0() {
        File CODIGO_JAVA = Paths.get(Caminho.DIR_SISTDOWN_ROOT.toString(), "codigo-java").toFile();
        if (CODIGO_JAVA.isDirectory()) {
            RecursosHandler.delete(CODIGO_JAVA);
            Registrador.printaMensagemConsole("Sistdown atualizado para v1.0.0");
        }
    }


    /**
     * Funcao que faz a atualização do Sistdown para a versão V2.
     * Motivo: Atualizado a forma como era colocado os configs na arquitetura atual
     * Depreciado since: v2.0.0
     */
    private void v2_0_0() {
        File CONFIG_OLD = Paths.get(Caminho.DIR_SISTDOWN_ROOT.toString(), "configs").toFile();
        if (CONFIG_OLD.isFile()) {
            CONFIG_OLD.renameTo(Caminho.FILE_TARGET_INFO_DOWNLOADS);
            Registrador.printaMensagemConsole("Sistdown atualizado para v2.0.0");
        }
    }


    /**
     * Funcao que deleta o antigo runnable do Sistdown
     * Motivo: Deploy não é mais colocado na maquina local de cada usuario, mas sim, no servidor
     * Depreciado since: v2.2.0
     */
    private void v2_2_0() {
        File oldRunnable = Paths.get(Caminho.DIR_SISTDOWN_ROOT.toString(), "jdk-18.0.2.1", "bin", "sist-down.jar").toFile();
        if (oldRunnable.isFile()) {
            RecursosHandler.delete(oldRunnable);
            Registrador.printaMensagemConsole("Sistdown atualizado para v2.2.0");
        }
    }


    /**
     * Deleta arquivos depreciados para troca de contexto entre local e rede
     * Motivo: Função toggler entre local e rede foi desativada devido ao sucesso do sistdown não necessitar mais do uso na rede
     * Depreciado since: v2.2.7
     */
    private void v2_2_9() {
        File shortcut_rede  = Paths.get(Caminho.DIR_SISTDOWN_ROOT.toString(), "Videos-rede").toFile();
        if (shortcut_rede.isFile()) {
            RecursosHandler.delete(shortcut_rede);
        }
        File temp_download_local = Paths.get(Caminho.DIR_SISTDOWN_ROOT.toString(), "Videos-local").toFile();
        if (temp_download_local.isDirectory()) {
            RecursosHandler.delete(temp_download_local);
        }
        File context = Paths.get(Caminho.DIR_SISTDOWN_ROOT.toString(), "config", "contexto").toFile();
        if (context.isFile()) {
            RecursosHandler.delete(context);
        }
    }


    /**
     * Deleta antigo formato de arquivo de logs local
     * Motivo: Atualizacao no formato de logs de download local para padronizar
     * Depreciado since: v2.3.0
     * @throws IOException
     */
    private void v2_3_0() throws IOException {
        Path oldInfo = Paths.get(Caminho.DIR_TARGET_CONFIG.toString(), "info-downloads");
        if (oldInfo.toFile().isFile()) {
            if (!Caminho.FILE_TARGET_INFO_DOWNLOADS.exists()) {
                Caminho.FILE_TARGET_INFO_DOWNLOADS.createNewFile();
            }
            String trechosNaLocal = Files.readString(oldInfo).replaceAll("\\s+", "").replaceAll(",$", "");
            for (String trecho : trechosNaLocal.split(",")) {
                Registrador.logaUmDownload(trecho.split("-")[0], Paths.get(trecho.split("-")[1]));
            }
            Registrador.printaMensagemConsole("Sistdown atualizado para v2.3.0");
            RecursosHandler.delete(oldInfo.toFile());
        }
    }


    /**
     * Deleta pasta de downloads temporarios
     * Depreciado since: v2.3.3
     * @throws IOException
     */
    private void v2_4_0() {
        File downloadTemp = Caminho.FILE_TARGET_VIDEOS_TEMP;
        if (downloadTemp.isFile()) {
            RecursosHandler.delete(downloadTemp);
        }
    }


    /**
     * Deleta pasta de order66 temporaria
     * Depreciado since: v2.4.1
     * @throws IOException
     */
    private void v2_4_2() {
        File downloadTemp = Paths.get(Caminho.DIR_TARGET_CONFIG.toString(), "order66").toFile();
        if (downloadTemp.isFile()) {
            RecursosHandler.delete(downloadTemp);
            Registrador.printaMensagemConsole("Sistdown atualizado para v2.4.2");
        }
    }
}
