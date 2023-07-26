package dalosto.dnit.sistdown.handler;
import static dalosto.dnit.sistdown.service.Util.textoEhValido;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import dalosto.dnit.sistdown.domain.InputArgsModel;
import dalosto.dnit.sistdown.domain.TagsConfiguracao;
import dalosto.dnit.sistdown.service.LoggerConsoleService;


/**
 * Classe que guarda os inputs digitados na Action Prompt.
 */
@Component
public final class PromptInputsHandler {
    
    @Autowired
    private LoggerConsoleService loggerConsoleService;

    private Set<String> inputs;
    private Scanner scanner = new Scanner(System.in);

    
    public void obtemInputs() {
        inputs = Collections.synchronizedSet(new HashSet<>());
        fillInputs();
        loggerConsoleService.pulaLinha(2);
    }


    private void fillInputs() {
        String fullInputConsoleLine = getKeyboardInput();
        for (String input : fullInputConsoleLine.split(",")) {
            adicionaInputsValidosNaSetDeInputs(input);
        }
    }


    private String getKeyboardInput() {
        return scanner.nextLine().replaceAll("apaga ", "apaga")
                                 .replaceAll("\\s+", ",")
                                 .replaceAll("[.<>;/?°]", ",");
    }


    private void adicionaInputsValidosNaSetDeInputs(String input) {
        if (textoEhValido(input)) {
            if (TagsConfiguracao.textEhUmaTag(input)) {
                inputs.add(input.toUpperCase());
            } else {
                input = input.replaceAll("[^\\d.]", "");
                if (textoEhValido(input)) {
                    inputs.add(input);
                }
            }
        }
    }


    public InputArgsModel verificaSeFoiSolicitado(Predicate<String> condicao) {
        InputArgsModel inputArgsModel = new InputArgsModel();
        for(var input : inputs) {
            if (condicao.test(input)) {
                inputArgsModel.setStatus(true);
                inputArgsModel.setArgs(TagsConfiguracao.removeTagFromString(input));
                break;
            }
        }
        return inputArgsModel;
    }



    public Set<String> obtemIdsDigitados() {
        return inputs.stream().filter(i -> i.matches("[0-9]+")).collect(Collectors.toSet());
    }

    
    public boolean isEmpty() {
        return inputs.size() == 0;
    }

}
