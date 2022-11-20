package action.actions;

import java.nio.file.Files;

import service.Caminhos;
import service.Util;

public class Inicializacao implements Acao {
    

    /**
     * Configurações de inicializacao:
     * (i)  Cria pastas de inicialização, e
     * (ii) Carrega os possiveis trechos para download.
     */
    public void executa() throws Exception {
        if (Util.primeiraRun()) {
            Caminhos.criarDiretorios();
            Caminhos.criarArquivos();
            Util.contexto = Files.readAllLines(Caminhos.SISTDOWN_CONFIG_CONTEXTO.toPath()).get(0);
            Util.carregaDoLocalUmaListComTrechosDisponiveis();
        }
    }



}
