package sistdown.Handler;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import sistdown.model.ApagaModel;
import sistdown.model.TagsConfiguracao;
import sistdown.service.Util;


/**
 * Classe que guarda os inputs digitados na Action Prompt.
 */
public class PromptInputsHandler {
    
    private static Set<String> inputs = Collections.synchronizedSet(new HashSet<>());
    private static Scanner scanner = new Scanner(System.in);

    private static String text;



    public static void obtemInputs() {
        String fullInput = scanner.nextLine();
        fullInput = fullInput.replaceAll("\\s+", ",").replaceAll("[.<>;/?°]", ",");
        for (String input : fullInput.split(","))
            adicionaInputsValidosNaLista(input);
    }



    private static void adicionaInputsValidosNaLista(String input) {
        if (Util.textoEhValido(input)) {
            if (TagsConfiguracao.ehUmaTag(input)) {
                inputs.add(input.toUpperCase());
            } else {
                input = input.replaceAll("[^\\d.]", "");
                if (Util.textoEhValido(input))
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



    public static ApagaModel foiSolicitadoApagar() {
        ApagaModel apaga;
        inputs.forEach(i -> {
            if (TagsConfiguracao.ehUmaTagDeApagar(i)) {
                text = i;
            }
        });
        apaga = new ApagaModel(text);
        text = null;
        inputs.removeIf(i -> TagsConfiguracao.ehUmaTagDeApagar(i));
        return apaga;
    }


    public static boolean foiSolicitadoAjuda() {
        return inputs.removeIf(i -> TagsConfiguracao.ehUmaTagDeAjuda(i));
    }



    public static Set<String> obtemIdsDigitados() {
        return Collections.unmodifiableSet(inputs);
    }



    public static void reiniciaPromptDigitados() {
        inputs = Collections.synchronizedSet(new HashSet<>());
    }



    public static boolean isEmpty() {
        return inputs.size() == 0;
    }

}
