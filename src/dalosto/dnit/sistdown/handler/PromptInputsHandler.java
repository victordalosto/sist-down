package dalosto.dnit.sistdown.handler;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import dalosto.dnit.sistdown.domain.InputArgsModel;
import dalosto.dnit.sistdown.domain.TagsConfiguracao;
import dalosto.dnit.sistdown.framework.annotations.Autowired;
import dalosto.dnit.sistdown.framework.annotations.Component;
import dalosto.dnit.sistdown.service.LoggerConsoleService;
import dalosto.dnit.sistdown.service.Util;


/**
 * Classe que guarda os inputs digitados na Action Prompt.
 */
@Component
public class PromptInputsHandler {
    
    private static Set<String> setInputs;
    private static Scanner scanner = new Scanner(System.in);

    @Autowired
    private static LoggerConsoleService loggerConsoleService;

    
    public static void obtemInputs() {
        String fullInputConsoleLine = getKeyboardInput();
        setInputs = Collections.synchronizedSet(new HashSet<>());
        for (String input : fullInputConsoleLine.split(",")) {
            adicionaInputsValidosNaSetDeInputs(input);
        }
        loggerConsoleService.pulaLinha(2);
    }


    private static String getKeyboardInput() {
        return scanner.nextLine()
                      .replaceAll("\\s+", ",")
                      .replaceAll("[.<>;/?°]", ",");
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
