package dalosto.dnit.sistdown.repository;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import dalosto.dnit.sistdown.framework.annotations.Component;
import dalosto.dnit.sistdown.helper.CaminhoHelper;


/**
 * Classe que contem os Trechos possiveis na rede que podem ser baixados para a maquina local. <p>
 * Com o id de identificação do trecho, é possível obter a partir do seu value, 
 * o target da pasta contendo o caminho do trecho para ser baixado na rede.
 */
@Component
public class TrechoRepository {

    public Map<String, String> trechos = new HashMap<>();
    private long lastModified = -1;


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
        File pathCSV = new File(CaminhoHelper.FILE_BANCO_CSV.toString());
        if (pathCSV.lastModified() != lastModified) {
            trechos = new HashMap<>();
            lastModified = pathCSV.lastModified();
            fillTrechosFromDB(pathCSV);
        }
    }


    private void fillTrechosFromDB(File pathCSV) throws FileNotFoundException {
        try(Scanner scanner = new Scanner(pathCSV)) {
            while (scanner.hasNextLine()) {
                String[] row = scanner.nextLine().split(";");
                trechos.put(row[0], row[1]);
            }
        }
    }
    
}
