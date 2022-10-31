package action;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Caminhos {

    public static File REDE_VIDEOS_FOLDER = Paths.get("\\\\10.100.10.219", "Videos").toFile();

    public static File SISTDOWN_ROOT      = Paths.get("D:", "sist-down").toFile();
    public static File SISTDOWN_DOWNLOADS = Paths.get(SISTDOWN_ROOT.toString(), "Videos").toFile();

    public static File SISTDOWN_SHORTCUT_REDE       = Paths.get(SISTDOWN_ROOT.toString(), "Videos.lnk").toFile();
    public static File SISTDOWN_DOWNLOADS_TEMPORARY = Paths.get(SISTDOWN_ROOT.toString(), "Videos-local").toFile();

    public static File SISTDOWN_CONFIG_FOLDER        = Paths.get(SISTDOWN_ROOT.toString(), "config").toFile();
    public static File SISTDOWN_CONFIG_CONTEXTO      = Paths.get(SISTDOWN_ROOT.toString(), "config", "contexto").toFile();
    public static File SISTDOWN_CONFIG_INFODOWNLOADS = Paths.get(SISTDOWN_ROOT.toString(), "config", "info-downloads").toFile();

    public static File pathCSVComTrechosDisponiveis = Paths.get(REDE_VIDEOS_FOLDER.toString(),
                                                                  "Recebidos", 
                                                                  "sistdown-config",
                                                                  "caminhos2.csv").toFile();
    


    /**
     * Rotina para criar as pastas necessarias para rodar o Sistdown
     */
    public static void criarPastas() {
        if (!SISTDOWN_ROOT.exists() && !SISTDOWN_ROOT.isDirectory()) {
            SISTDOWN_ROOT.mkdirs();
        }
        if (!SISTDOWN_DOWNLOADS.exists() && !SISTDOWN_DOWNLOADS.isDirectory() &&
            !SISTDOWN_SHORTCUT_REDE.isFile() && !SISTDOWN_SHORTCUT_REDE.isDirectory() &&
            !SISTDOWN_DOWNLOADS_TEMPORARY.isFile() && !SISTDOWN_DOWNLOADS_TEMPORARY.isDirectory()) {
            SISTDOWN_DOWNLOADS.mkdirs();
        }
        if (!SISTDOWN_CONFIG_FOLDER.exists() && !SISTDOWN_CONFIG_FOLDER.isDirectory()) {
            SISTDOWN_CONFIG_FOLDER.mkdirs();
        }
    }



    /**
     * Rotina para criar os arquivos necessarios para rodar o Sistdown
     */
    public static void criarArquivos() throws IOException {
        if (!SISTDOWN_CONFIG_CONTEXTO.exists()) {
            SISTDOWN_CONFIG_CONTEXTO.createNewFile();
            Files.write(SISTDOWN_CONFIG_CONTEXTO.toPath(), ("local").getBytes(), StandardOpenOption.APPEND);
        }
        if (!SISTDOWN_CONFIG_INFODOWNLOADS.exists()) {
            SISTDOWN_CONFIG_INFODOWNLOADS.createNewFile();
        }
    }
}
