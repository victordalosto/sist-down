package dalosto.sistdown.helper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import dalosto.sistdown.service.Util;


/**
 * Classe <b>Caminho</b> contendo os caminhos dos arquivos de configuração,
 * bem como métodos utilitários para criar os arquivos de configuração.
 */
public class CaminhoHelper {

    public static final File DIR_SISTDOWN_ROOT    = Paths.get("D:", "sist-down").toFile();
    
    public static final File DIR_REDE_VIDEOS_ROOT = Paths.get("\\\\10.100.10.219", "Videos").toFile();
    public static final File DIR_TARGET_VIDEOS_ROOT = Paths.get("D:", "sist-down", "Videos").toFile();

    public static final File FILE_TARGET_VIDEOS_TEMP = Paths.get("D:", "sist-down", "Videos-temp").toFile();

    public static final File DIR_TARGET_CONFIG = Paths.get("D:", "sist-down", "config").toFile();
    public static final File FILE_TARGET_INFO_DOWNLOADS = Paths.get("D:", "sist-down", "config", 
                                                                "logs-downloads").toFile();

    public static final File FILE_TARGET_BANCO_CSV = Paths.get(DIR_REDE_VIDEOS_ROOT.toString(), "Recebidos", 
                                                          "sistdown-config", "caminhos.csv").toFile();
    

    /**
     * Rotina para criar as pastas necessarias para rodar o Sistdown.
     */
    public void initialize() throws IOException {
        if (Util.verificaSeEhAPrimeiraVezRodandoOPrograma()) {
            criarArquivosIniciais();
        }
    }


    private void criarArquivosIniciais() throws IOException {
        if (!DIR_SISTDOWN_ROOT.isDirectory()) {
            DIR_SISTDOWN_ROOT.mkdirs();
        }
        
        if (!DIR_TARGET_VIDEOS_ROOT.isDirectory()) {
            DIR_TARGET_VIDEOS_ROOT.mkdirs(); 
        }
        
        if (!DIR_TARGET_CONFIG.isDirectory()) {
            DIR_TARGET_CONFIG.mkdirs();
        }

        if (!FILE_TARGET_INFO_DOWNLOADS.exists()) {
            FILE_TARGET_INFO_DOWNLOADS.createNewFile();
        }
    }



}
