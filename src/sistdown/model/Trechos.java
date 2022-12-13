package sistdown.model;
import java.util.HashMap;
import java.util.Map;


/**
 * Classe que contem os Trechos possiveis na rede que podem ser baixados para a maquina local. <p>
 * Com o id de identificação do trecho, é possível obter a partir do seu value, 
 * o target da pasta contendo o caminho do trecho para ser baixado na rede.
 */
public class Trechos {

    public static Map<String, String> hashTrechos = new HashMap<>();

    /**
     * Adiciona um trecho possivel para sere baixado.
     * @param id - Id do trecho no Sistlev.
     * @param caminhoRede - Caminho do trecho na rede.
     */
    public static void addTrechoNoBanco(String id, String caminhoRede) {
        hashTrechos.put(id, caminhoRede);
    }



    /**
     * Obtem o caminho do trecho na rede a partir do id.
     * @param id do trecho.
     * @return String - Caminho contendo o trecho para download na rede.
     */
    public static String getCaminho(String id) {
        return hashTrechos.get(id);
    }

    

    /** 
     * Reinicia a lista com trechos disponíveis
     */
    public static void reiniciaBancoTrechos() {
        hashTrechos = new HashMap<>();
    }
    
}
