package sistdown.Handler;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Predicate;
import sistdown.model.ApagaModel;
import sistdown.model.InputArgsModel;
import sistdown.model.TagsConfiguracao;
import sistdown.service.Util;


/**
 * Classe que guarda os inputs digitados na Action Prompt.
 */
public class PromptInputsHandler {
    
    private static Set<String> setInputs;
    private static Scanner scanner = new Scanner(System.in);

    private static String text;


    public static void obtemInputs() {
        setInputs = Collections.synchronizedSet(new HashSet<>());
        String fullInputConsoleLine = scanner.nextLine();
        fullInputConsoleLine = fullInputConsoleLine.replaceAll("\\s+", ",").replaceAll("[.<>;/?°]", ",");
        for (String input : fullInputConsoleLine.split(",")) {
            adicionaInputsValidosNaSetDeInputs(input);
        }
        System.out.println("\n\n");
    }


    private static void adicionaInputsValidosNaSetDeInputs(String input) {
        if (Util.textoEhValido(input)) {
            if (TagsConfiguracao.textEhUmaTag(input)) {
                setInputs.add(input.toUpperCase());
            } else {
                input = input.replaceAll("[^\\d.]", "");
                if (Util.textoEhValido(input)) {
                    setInputs.add(input);
                }
            }
        }
    }


    public static InputArgsModel verificaSeFoiSolicitado(Predicate<String> condicao) {
        InputArgsModel inputArgsModel = new InputArgsModel();
        for(String input : setInputs) {
            if (condicao.test(input)) {
                inputArgsModel.setStatus(true);
                inputArgsModel.setArgs(TagsConfiguracao.removeTagFromString(input));
            }
        }
        return inputArgsModel;
    }


    public static boolean foiSolicitadoLimpar() {
        setInputs.forEach(item -> {
            if (TagsConfiguracao.ehUmaTagDeLimpar(item)) {
            }
        });
        return setInputs.removeIf(i -> TagsConfiguracao.ehUmaTagDeLimpar(i));
    }


    public static ApagaModel foiSolicitadoApagar() {
        ApagaModel apaga;
        setInputs.forEach(i -> {
            if (TagsConfiguracao.ehUmaTagDeApagar(i)) {
                text = i;
            }
        });
        apaga = new ApagaModel(text);
        text = null;
        setInputs.removeIf(i -> TagsConfiguracao.ehUmaTagDeApagar(i));
        return apaga;
    }


    public static boolean foiSolicitadoAjuda() {
        return setInputs.removeIf(i -> TagsConfiguracao.ehUmaTagDeAjuda(i));
    }


    public static Set<String> obtemIdsDigitados() {
        return Collections.unmodifiableSet(setInputs);
    }

    public static boolean isEmpty() {
        return setInputs.size() == 0;
    }

}
