package dalosto.dnit.sistdown.helper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import dalosto.dnit.sistdown.service.Util;


/**
 * Classe <b>Caminho</b> contendo os caminhos dos arquivos de configuração,
 * bem como métodos utilitários para criar os arquivos de configuração.
 */
public class CaminhoHelper {

    public static final File DIR_SISTDOWN_ROOT    = Paths.get("D:", "sist-down").toFile();
    
    public static final File DIR_REDE_VIDEOS = Paths.get("\\\\10.100.10.219", "Videos").toFile();
    public static final File DIR_VIDEOS = Paths.get(DIR_SISTDOWN_ROOT.toString(), "Videos").toFile();
    public static final File DIR_VIDEOS_TEMP = Paths.get(DIR_SISTDOWN_ROOT.toString(), "Videos-temp").toFile();

    public static final File DIR_CONFIG = Paths.get(DIR_SISTDOWN_ROOT.toString(), "config").toFile();
    public static final File FILE_LOGS_DOWNLOADS = Paths.get(DIR_CONFIG.toString(), "logs-downloads").toFile();

    public static final File FILE_BANCO_CSV = Paths.get(DIR_REDE_VIDEOS.toString(), "Recebidos", "sistdown-config", "caminhos.csv").toFile();
    

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
        
        if (!DIR_VIDEOS.isDirectory()) {
            DIR_VIDEOS.mkdirs(); 
        }
        
        if (!DIR_CONFIG.isDirectory()) {
            DIR_CONFIG.mkdirs();
        }

        if (!FILE_LOGS_DOWNLOADS.exists()) {
            FILE_LOGS_DOWNLOADS.createNewFile();
        }
    }



}
