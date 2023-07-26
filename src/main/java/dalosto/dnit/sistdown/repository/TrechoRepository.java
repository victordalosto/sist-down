package dalosto.dnit.sistdown.repository;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import org.springframework.stereotype.Component;
import dalosto.dnit.sistdown.service.CaminhoService;


/**
 * Reposito com os Trechos baixaveis na rede para a maquina local.     <p>
 * Com o id de identificação do trecho, é possível obter o target da pasta
 * contendo o caminho (Path) do trecho para ser baixado na rede.
 */
@Component
public final class TrechoRepository {

    public Map<String, String> trechos;
    private long lastModifiedDate = -1;


    /**
     * Obtem o caminho do trecho na rede a partir do id.
     * @param id do trecho.
     * @return String - Caminho contendo o trecho para download na rede.
     * @throws FileNotFoundException
     */
    public String getPath(String id) throws FileNotFoundException {
        atualizaTrechosDisponiveisBanco();
        return trechos.get(id);
    }

    
    /**
     * Carrega os trechos que estão disponíveis para download. <p>
     */
    private void atualizaTrechosDisponiveisBanco() throws FileNotFoundException {
        File pathCSV = new File(CaminhoService.SISTDOWN_BANCO_CSV.toString());
        if (pathCSV.lastModified() != lastModifiedDate) {
            this.trechos = new HashMap<>();
            lastModifiedDate = pathCSV.lastModified();
            fillTrechosFromDB(pathCSV);
        }
    }


    private void fillTrechosFromDB(File pathCSV) throws FileNotFoundException {
        try(Scanner scanner = new Scanner(pathCSV)) {
            while (scanner.hasNextLine()) {
                String[] row = scanner.nextLine().split(";");
                String id = row[0];
                String path = row[1];
                trechos.put(id, path);
            }
        }
    }
    
}
