package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import service.Util;

public class Inputs {
    
    public static List<String> listaComInputs = Collections.synchronizedList(new ArrayList<>());


    /**
     * Adiciona na lista os inputs digitados
     */
    public static void adicionaNaListaEscolhida(String trecho) {
        if (Util.isValid(trecho)) {
            if (TagsConfiguracao.isTag(trecho)) {
                    listaComInputs.add(trecho.toUpperCase());
            } else {
                trecho = trecho.replaceAll("[^\\d.]", "");
                if (Util.isValid(trecho))
                    listaComInputs.add(trecho);
            }
        }
    }


    public static int size() {
        return listaComInputs.size();
    }
}
