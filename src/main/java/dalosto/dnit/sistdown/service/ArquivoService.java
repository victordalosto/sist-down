package dalosto.dnit.sistdown.service;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import org.springframework.stereotype.Component;
import dalosto.dnit.sistdown.domain.Trecho;


@Component
public final class ArquivoService {

    public synchronized List<Trecho> getTrechosBaixados() throws IOException {
        var lines = Files.readAllLines(CaminhoService.SISTDOWN_LOGS_DOWNLOADS.toPath());
        return Trecho.factory(lines);
    }


    public synchronized void saveTrechoBaixado(Trecho trecho) throws IOException {
        Files.write(CaminhoService.SISTDOWN_LOGS_DOWNLOADS.toPath(), 
                    (trecho.saveFormat()).getBytes(), 
                    StandardOpenOption.APPEND);
    }


    public synchronized void deletaTrechosDoLogs(List<Trecho> trechosDeletados) throws IOException {
        var trechosBaixados = getTrechosBaixados();
        trechosBaixados.removeAll(trechosDeletados);
        clearTrechosBaixados();
        for (Trecho trecho : trechosBaixados) {
            saveTrechoBaixado(trecho);
        }
    }


    public synchronized void clearTrechosBaixados() throws IOException {
        FileWriter f = new FileWriter(CaminhoService.SISTDOWN_LOGS_DOWNLOADS, false);
        f.close();
    }

    
    public synchronized String logaTrechoBaixado(String idTrecho, Path target) throws IOException {
        String hash = target.toString().replaceAll(".+_", "");
        String uf = hash.substring(3, 5);
        String br = hash.substring(0, 3);
        String append = idTrecho+";"+uf+";"+br;
        Trecho trecho = new Trecho(append);
        return logaTrechoBaixado(trecho);
    }

    
    public synchronized String logaTrechoBaixado(Trecho trecho) throws IOException {
        if (ehUnico(trecho)) {
            saveTrechoBaixado(trecho);
        }
        return trecho.toString();
    }


    private synchronized boolean ehUnico(Trecho trecho) throws IOException {
        return !getTrechosBaixados().contains(trecho);
    }

}
