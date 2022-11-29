package sistdown.service;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import sistdown.model.Context;


/**
 * Classe <b>Caminho</b> contendo os caminhos dos arquivos de configuração,
 * bem como métodos utilitários para criar os arquivos de configuração.
 */
public class Caminho {

    public static final File SISTDOWN_ROOT    = Paths.get("D:", "sist-down").toFile();
    public static final File REDE_VIDEO_FOLDER = Paths.get("\\\\10.100.10.219", "Videos").toFile();

    public static final File SISTDOWN_CURRENT = Paths.get(SISTDOWN_ROOT.toString(), "Videos").toFile();
    public static final File SISTDOWN_SHORTCUT_REDE   = Paths.get(SISTDOWN_ROOT.toString(), "Videos-rede").toFile();
    public static final File SISTDOWN_DOWNLOADS_LOCAL = Paths.get(SISTDOWN_ROOT.toString(), "Videos-local").toFile();

    public static final File SISTDOWN_CONFIG_FOLDER        = Paths.get(SISTDOWN_ROOT.toString(), "config").toFile();
    public static final File SISTDOWN_CONFIG_CONTEXTO      = Paths.get(SISTDOWN_ROOT.toString(), "config", "contexto").toFile();
    public static final File SISTDOWN_CONFIG_INFODOWNLOADS = Paths.get(SISTDOWN_ROOT.toString(), "config", "info-downloads").toFile();

    public static final File pathCSVComTrechosDisponiveis = Paths.get(REDE_VIDEO_FOLDER.toString(), "Recebidos", "sistdown-config", "caminhos.csv").toFile();
    


    /**
     * Rotina para criar as pastas necessarias para rodar o Sistdown.
     */
    public static void criarDiretorios() {
        if (!SISTDOWN_ROOT.isDirectory()) {
            SISTDOWN_ROOT.mkdirs();
        }
        if (!SISTDOWN_CURRENT.isDirectory() &&
            !SISTDOWN_SHORTCUT_REDE.isDirectory() &&
            !SISTDOWN_DOWNLOADS_LOCAL.isDirectory()) {
            SISTDOWN_CURRENT.mkdirs();
        }
        if (!SISTDOWN_CONFIG_FOLDER.isDirectory()) {
            SISTDOWN_CONFIG_FOLDER.mkdirs();
        }
    }



    /**
     * Rotina para criar os arquivos necessarios para rodar o Sistdown.
     */
    public static void criarArquivos() throws IOException {
        if (!SISTDOWN_CONFIG_CONTEXTO.exists()) {
            SISTDOWN_CONFIG_CONTEXTO.createNewFile();
            Files.write(SISTDOWN_CONFIG_CONTEXTO.toPath(), (Context.LOCAL.toString()).getBytes(), StandardOpenOption.APPEND);
        }
        if (!SISTDOWN_CONFIG_INFODOWNLOADS.exists()) {
            SISTDOWN_CONFIG_INFODOWNLOADS.createNewFile();
        }
    }

}
