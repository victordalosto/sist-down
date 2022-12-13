package sistdown.action.actions;
import java.nio.file.Files;
import sistdown.service.Caminho;
import sistdown.service.Util;


/**
 * Faz o Bootstrap da aplicação:                <p>
 * (i) Cria pastas para fazer a inicialização;  <p>
 * (ii) Carrega uma List com os possiveis trechos para download.
 */
public class Inicializacao implements Acao {


    public void executa() throws Exception {
        if (Util.verificaSeEhAPrimeiraVezRodandoOPrograma()) {
            Caminho.criarDiretorios();
            Caminho.criarArquivos();
            Util.contexto = Files.readAllLines(Caminho.SISTDOWN_CONFIG_CONTEXTO.toPath()).get(0);
        }
    }

}
