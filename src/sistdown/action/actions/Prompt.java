package action.actions;

import java.util.Scanner;

import model.Inputs;

public class Prompt implements Acao {
    

    /**
     * Cria na tela um prompt pedindo inputs de:
     * (i)  Trechos para baixar na maquina local,
     * (ii) Configurações que manipulam o Sistdown.
     */
    @SuppressWarnings({ "resource" })
    public void executa() throws Exception {
        Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine().replaceAll("\\s+", ",").replaceAll("[;]", ",").replaceAll("[/]", ",").replaceAll("[.]", ",");
            if (!input.contains(",")) {
                Inputs.adicionaNaListaEscolhida(input);
            } else {
                String[] rows = input.split(",");
                for (String trecho : rows) {
                    Inputs.adicionaNaListaEscolhida(trecho);
                }
            }
            System.out.println();
    }

}