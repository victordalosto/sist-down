import action.Acoes;

public class Main {

    public static void main(String[] args) {
        try {
            Acoes.inicializacao();
            new Thread(() -> {
                while (true) {
                    try {
                        Acoes.printaInicio();
                        Acoes.criaPromptPedindoInputs();
                        Acoes.inputsDeConfiguracao();
                        Acoes.inputsDeDownload();
                        Acoes.printaFim();
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("\n\n\n * Reiniciando Sistdown - Avise o vitão que houve um erro");
                    }
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("\n\n\n * Problema ao inicializar o Sistdown.");
        }

        

    }
}