package sistdown.model;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import sistdown.service.Util;


/**
 * Classe que guarda os inputs digitados na Action Prompt.
 */
public class PromptInputs {
    
    public static List<String> listaComInputs = Collections.synchronizedList(new ArrayList<>());


    /**
     * Adiciona na lista os inputs digitados no prompt.
     */
    public static void adicionaNaLista(String trecho) {
        if (Util.isValid(trecho)) {
            if (TagsConfiguracao.ehUmaTag(trecho)) {
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
    public static String getNextInListAndDeleteIt() {
        String id = listaComInputs.get(0);
        listaComInputs.remove(0);
        return id;
    }




    public static boolean foiSolicitadoLimpar() {
        boolean hasClearTag = false;
        for (int i=0; i<listaComInputs.size(); i++) {
            if (TagsConfiguracao.ehUmaTagDeLimpar(listaComInputs.get(i))) {
                listaComInputs.remove(i);
                hasClearTag = true;
            }
        }
        return hasClearTag;
    }


}
