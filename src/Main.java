import config.Acoes;

public class Main {

    public static void main(String[] args) throws Exception {
        Acoes.printaInicio();
        Acoes.buscaTrechosDisponiveis();
        Acoes.criaPromptPedindoTrechos();
        Acoes.verificaSeLimpaPasta();
        Acoes.downloadFolders();
        Acoes.printaFim();
    }
}