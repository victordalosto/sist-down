import action.Acoes;

public class Main {

    public static void main(String[] args) throws Exception {
        Acoes.inicializacao();
        // Acoes.carregaTrechosDisponiveis();
        while (true) {
            try {
                Acoes.printaInicio();
                Acoes.criaPromptPedindoInputs();
                Acoes.verificaSeMudaContexto();
                Acoes.verificaSeLimpaPasta();
                Acoes.downloadFolders();
                Acoes.printaFim();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("\n\n\n * Reiniciando Sistdown - Avise o vitão que houve um erro");
            }
        }

    }
}