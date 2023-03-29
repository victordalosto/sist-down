package dalosto.sistdown.Handler;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import dalosto.sistdown.model.InputArgsModel;
import dalosto.sistdown.model.TagsConfiguracao;
import dalosto.sistdown.service.Util;


/**
 * Classe que guarda os inputs digitados na Action Prompt.
 */
public class PromptInputsHandler {
    
    private static Set<String> setInputs;
    private static Scanner scanner = new Scanner(System.in);

    
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



    public static Set<String> obtemIdsDigitados() {
        return setInputs.stream().filter(i -> i.matches("[0-9]+")).collect(Collectors.toSet());
    }

    
    public static boolean isEmpty() {
        return setInputs.size() == 0;
    }

}
