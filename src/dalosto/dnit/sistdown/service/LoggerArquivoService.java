package dalosto.dnit.sistdown.service;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import dalosto.dnit.sistdown.framework.annotations.Autowired;
import dalosto.dnit.sistdown.framework.annotations.Component;
import dalosto.dnit.sistdown.helper.CaminhoHelper;


@Component
public class LoggerArquivoService {

    @Autowired
    LoggerConsoleService loggerConsoleService;


    public void appendMensagemToLog(String msg) throws IOException {
        Files.write(CaminhoHelper.FILE_LOGS_DOWNLOADS.toPath(), 
                    (msg+"\n").getBytes(), 
                    StandardOpenOption.APPEND);
    }


    public List<String> readAllLog() throws IOException {
        return Files.readAllLines(CaminhoHelper.FILE_LOGS_DOWNLOADS.toPath());
    }


    public void clearLog() throws IOException {
        FileWriter f = new FileWriter(CaminhoHelper.FILE_LOGS_DOWNLOADS, false);
        f.close();
    }

    
    public synchronized String logaUmDownload(String idTrecho, Path target) throws IOException {
        String hash = target.toString().replaceAll(".+_", "");
        String uf = hash.substring(3,5);
        String br = hash.substring(0, 3);
        String append = idTrecho+";"+uf+";"+br;
        if (logEhUnicoNoArquivo(append)) {
            appendMensagemToLog(append);
        }
        return formataNomeTrecho(append);
    }


    public String formataNomeTrecho(String row) {
        if (!Util.textoEhValido(row) || !row.contains(";") || row.split(";").length != 3)
            return "[" + row + "]";
        String[] array = row.split(";");
        while (array[0].length() < 5) {
            array[0] = " " + array[0];
        }
        return "["+array[0]+" - " + array[1] + "/" + array[2] +" ]";
    }


    private boolean logEhUnicoNoArquivo(String append) throws IOException {
        List<String> lines = readAllLog();
        for (String l : lines) {
            if (append.equals(l))
                return false;
        }
        return true;
    }

    public void recriaLogApartirDeString(List<String> list) throws IOException {
        clearLog();
        for (String item : list) {
            Files.write(CaminhoHelper.FILE_LOGS_DOWNLOADS.toPath(), (item+"\n").getBytes(), StandardOpenOption.APPEND);
        }
    }


    
}
