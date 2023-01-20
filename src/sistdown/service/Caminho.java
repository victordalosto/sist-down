package sistdown.service;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;


/**
 * Classe <b>Caminho</b> contendo os caminhos dos arquivos de configuração,
 * bem como métodos utilitários para criar os arquivos de configuração.
 */
public class Caminho {

    public static final File SISTDOWN_ROOT    = Paths.get("D:", "sist-down").toFile();
    
    public static final File INPUT_ROOT = Paths.get("\\\\10.100.10.219", "Videos").toFile();
    public static final File TARGET_DOWNLOAD = Paths.get(SISTDOWN_ROOT.toString(), "Videos").toFile();

    public static final File CONFIG_FOLDER = Paths.get(SISTDOWN_ROOT.toString(), "config").toFile();
    public static final File INFO_DOWNLOADS = Paths.get(CONFIG_FOLDER.toString(), "logs-downloads").toFile();

    public static final File PATH_BANCO = Paths.get(INPUT_ROOT.toString(), "Recebidos", "sistdown-config", "caminhos.csv").toFile();
    

    public void init() throws IOException {
        criarDiretorios();
        criarArquivos();
    }


    /**
     * Rotina para criar as pastas necessarias para rodar o Sistdown.
     */
    private void criarDiretorios() {
        if (!SISTDOWN_ROOT.isDirectory())
            SISTDOWN_ROOT.mkdirs();
        
        if (!TARGET_DOWNLOAD.isDirectory())
            TARGET_DOWNLOAD.mkdirs();
        
        if (!CONFIG_FOLDER.isDirectory())
            CONFIG_FOLDER.mkdirs();
    }



    /**
     * Rotina para criar os arquivos necessarios para rodar o Sistdown.
     */
    private void criarArquivos() throws IOException {
        if (!INFO_DOWNLOADS.exists())
            INFO_DOWNLOADS.createNewFile();
    }

}
