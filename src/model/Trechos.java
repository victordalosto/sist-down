package model;

import java.util.HashMap;
import java.util.Map;

public class Trechos {

    public static Map<String, String> hashTrechos = new HashMap<>();




    public static void addTrecho(String id, String cmainhoRede) {
        hashTrechos.put(id, cmainhoRede);
    }


    public static String getCaminho(String id) {
        return hashTrechos.get(id);
    }


    public static Integer tamanho() {
        System.out.println(hashTrechos);
        return hashTrechos.size();
    }


    
    
}
