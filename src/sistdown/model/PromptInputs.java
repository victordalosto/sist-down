package sistdown.model;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import sistdown.service.Util;


/**
 * Classe que guarda os inputs digitados na Action Prompt.
 */
public class PromptInputs {
    
    private static Set<String> inputs = Collections.synchronizedSet(new HashSet<>());



    public static void adicionaInputs(String fullInput) {
        fullInput = fullInput.replaceAll("\\s+", ",").replaceAll("[.<>;:/?°-]", ",");
        for (String input : fullInput.split(","))
            adicionaInputsValidosNaLista(input);
    }



    private static void adicionaInputsValidosNaLista(String input) {
        if (Util.textEhValido(input)) {
            if (TagsConfiguracao.ehUmaTag(input)) {
                inputs.add(input.toUpperCase());
            } else {
                input = input.replaceAll("[^\\d.]", "");
                if (Util.textEhValido(input))
                    inputs.add(input);
            }
        }
    }



    public static void removeInputDaLista(String input) {
        inputs.remove(input);
    }



    public static boolean foiSolicitadoLimpar() {
        return inputs.removeIf(i -> TagsConfiguracao.ehUmaTagDeLimpar(i));
    }



    public static Set<String> obtemIdsDigitados() {
        return Collections.unmodifiableSet(inputs);
    }



    public static void reiniciaPromptDigitados() {
        inputs = Collections.synchronizedSet(new HashSet<>());
    }

}
