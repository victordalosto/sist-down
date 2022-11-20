package model;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import service.Caminhos;
import service.Util;

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


    /**
     * Printa os trechos que estão baixados na maquina local.
     */
    public static void printaTrechosQueEstaoNaMaquinaLocal() throws Exception {
        String trechosNaLocal = Files.readString(Paths.get(Caminhos.SISTDOWN_CONFIG_INFODOWNLOADS.toString())).replaceAll("\\s+", "").replaceAll(",$", "");
        if (trechosNaLocal.equals("")) {
            System.out.println(" * 0 trechos baixados.");
        } else {
            System.out.print(" * Baixados: ");
            Set<String> trechos = new HashSet<>();
            if (!trechosNaLocal.contains(",")) {
                trechos.add(trechosNaLocal);
            } else {
                String [] rows = trechosNaLocal.split(",");
                for (int i = 0; i < rows.length; i++) {
                    if (Util.isValid(rows[i]))
                        trechos.add(rows[i]);
                }
            }
            System.out.println(trechos);
        }
    }


    
}
