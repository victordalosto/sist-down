import action.Acoes;

public class Main {

    public static void main(String[] args) throws Exception {
        Acoes.inicializacao();
        Acoes.carregaTrechosDisponiveis();
        while (true) {
            Acoes.printaInicio();
            Acoes.criaPromptPedindoInputs();
            Acoes.verificaSeMudaContexto();
            Acoes.verificaSeLimpaPasta();
            Acoes.downloadFolders();
            Acoes.printaFim();
        }

    }
}