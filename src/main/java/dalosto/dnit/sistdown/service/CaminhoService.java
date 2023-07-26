package dalosto.dnit.sistdown.service;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import org.springframework.stereotype.Service;


/**
 * Classe Caminho contendo os caminhos dos arquivos de configuração,
 * bem como métodos utilitários para criar os arquivos de configuração.
 * 
 * D:\sist-down           
 *   \Videos              
 *   \Videos-temp         
 *   \config              
 *       + logs-downloads 
 */
@Service
public final class CaminhoService {

    public static final File DIR_SISTDOWN_ROOT = Paths.get("D:", "sist-down").toFile();

    // Target root dir to be copied into sist-down rot
    public static final File DIR_REDE_VIDEOS = Paths.get("\\\\10.100.10.219", "Videos").toFile();

    // Stores the same file structure of the network
    public static final File DIR_SISTDOWN_VIDEOS = Paths.get(DIR_SISTDOWN_ROOT.toString(), "Videos").toFile();

    // Holds temporary files marked to be deleted
    public static final File DIR_SISTDOWN_VIDEOS_TEMP = Paths.get(DIR_SISTDOWN_ROOT.toString(), "Videos-temp").toFile();

    // Configuration files
    public static final File DIR_SISTDOWN_CONFIG = Paths.get(DIR_SISTDOWN_ROOT.toString(), "config").toFile();

    // Local database that marks downloaded files
    public static final File SISTDOWN_LOGS_DOWNLOADS = Paths.get(DIR_SISTDOWN_CONFIG.toString(), "logs-downloads").toFile();

    // PlaceHolder CSV of a Database with network files to download
    public static final File SISTDOWN_BANCO_CSV = Paths.get(DIR_REDE_VIDEOS.toString(), "Recebidos", "sistdown-config", "caminhos.csv").toFile();


    

    /**
     * Rotina para criar as pastas necessarias para rodar o Sistdown.
     */
    public void initialize() throws IOException {
        if (Util.ehAPrimeiraVezRodandoOPrograma()) {
            criarArquivosIniciais();
        }
    }


    private void criarArquivosIniciais() throws IOException {
        if (!DIR_SISTDOWN_ROOT.isDirectory()) {
            DIR_SISTDOWN_ROOT.mkdirs();
        }
        
        if (!DIR_SISTDOWN_VIDEOS.isDirectory()) {
            DIR_SISTDOWN_VIDEOS.mkdirs(); 
        }
        
        if (!DIR_SISTDOWN_CONFIG.isDirectory()) {
            DIR_SISTDOWN_CONFIG.mkdirs();
        }

        if (!SISTDOWN_LOGS_DOWNLOADS.exists()) {
            SISTDOWN_LOGS_DOWNLOADS.createNewFile();
        }
    }



}
