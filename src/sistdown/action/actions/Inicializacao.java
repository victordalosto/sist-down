package sistdown.action.actions;
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
        }
    }

}
