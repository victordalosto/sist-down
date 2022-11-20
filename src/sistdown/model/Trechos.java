package model;

import java.util.HashMap;
import java.util.Map;

public class Trechos {

    public static Map<String, String> hashTrechos = new HashMap<>();

    /**
     * Adiciona os trechos possiveis para serem baixados
     * @param id - Id do trecho no Sistlev
     * @param caminhoRede - Caminho do trecho na rede
     */
    public static void addTrecho(String id, String caminhoRede) {
        hashTrechos.put(id, caminhoRede);
    }



    public static String getCaminho(String id) {
        return hashTrechos.get(id);
    }


    
}
