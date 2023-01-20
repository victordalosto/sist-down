package sistdown.model;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import sistdown.service.Util;


/**
 * Classe que guarda os inputs digitados na Action Prompt.
 */
public class PromptInputs {
    
    private static Set<String> listaComInputs;



    public static void reiniciaPromptEAdicionaInputs(String fullInput) {
        listaComInputs = Collections.synchronizedSet(new HashSet<>());
        fullInput = fullInput.replaceAll("\\s+", ",").replaceAll("[.<>;:/?°-]", ",");
        for (String input : fullInput.split(","))
            adicionaInputsValidosNaLista(input);
    }



    private static void adicionaInputsValidosNaLista(String input) {
        if (Util.isValid(input)) {
            if (TagsConfiguracao.ehUmaTag(input)) {
                listaComInputs.add(input.toUpperCase());
            } else {
                input = input.replaceAll("[^\\d.]", "");
                if (Util.isValid(input))
                    listaComInputs.add(input);
            }
        }
    }


    
    public static void removeInputDaLista(String input) {
        listaComInputs.remove(input);
    }



    public static boolean foiSolicitadoLimpar() {
        return listaComInputs.removeIf(i -> TagsConfiguracao.ehUmaTagDeLimpar(i));
    }



    public static Set<String> obtemIdsDigitados() {
        return Collections.unmodifiableSet(listaComInputs);
    }

}
