package sistdown.model;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import sistdown.service.Util;


/**
 * Classe que guarda os inputs digitados na Action Prompt.
 */
public class InputsPrompt {
    
    public static List<String> listaComInputs = Collections.synchronizedList(new ArrayList<>());


    /**
     * Adiciona na lista os inputs digitados no prompt.
     */
    public static void adicionaNaLista(String trecho) {
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



    /**
     * @return int contendo a quantidade de inputs validos digitados no prompt.
     */
    public static int sizeList() {
        return listaComInputs.size();
    }



    /**
     * @return String contendo o primeiro input digitado na List.
     */
    public static String getFirstInList() {
        return listaComInputs.get(0);
    }



    /**
     * Remove o primeiro input da List.
     */
    public static void removeFirstInList() {
        listaComInputs.remove(0);
    }

}
