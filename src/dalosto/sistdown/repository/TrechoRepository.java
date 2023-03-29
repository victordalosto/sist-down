package dalosto.sistdown.repository;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import dalosto.sistdown.helper.CaminhoHelper;


/**
 * Classe que contem os Trechos possiveis na rede que podem ser baixados para a maquina local. <p>
 * Com o id de identificação do trecho, é possível obter a partir do seu value, 
 * o target da pasta contendo o caminho do trecho para ser baixado na rede.
 */
public class TrechoRepository {

    public static Map<String, String> hashTrechos = new HashMap<>();
    private static long lastModified = 0;


    /**
     * Obtem o caminho do trecho na rede a partir do id.
     * @param id do trecho.
     * @return String - Caminho contendo o trecho para download na rede.
     * @throws FileNotFoundException
     */
    public static String getPath(String id) throws FileNotFoundException {
        TrechoRepository.atualizaTrechosDisponiveisBanco();
        return hashTrechos.get(id);
    }

    
    /**
     * Carrega os trechos que estão disponíveis para download. <p>
     */
    private static void atualizaTrechosDisponiveisBanco() throws FileNotFoundException {
        File pathCSV = new File(CaminhoHelper.FILE_TARGET_BANCO_CSV.toString());
        if (pathCSV.lastModified() != lastModified) {
            lastModified = pathCSV.lastModified();
            hashTrechos = new HashMap<>();
            try(Scanner scanner = new Scanner(pathCSV)) {
                while (scanner.hasNextLine()) {
                    String[] row = scanner.nextLine().split(";");
                    hashTrechos.put(row[0], row[1]);
                }
            }
        }
    }
    
}
