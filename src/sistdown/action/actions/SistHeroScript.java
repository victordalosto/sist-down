package sistdown.action.actions;
import java.io.IOException;
import sistdown.service.Util;


/**
 * Inicializa o script de SistHero Basic que possibilita a sua correta execucao
 */
public class SistHeroScript implements Acao {

    public void executa() throws Exception {
        // if (Util.verificaSeEhAPrimeiraVezRodandoOPrograma()) {
        //     new Thread( () -> {
        //         try {
        //             String ahkExePath = "\\\\10.100.10.219\\Videos$\\Recebidos\\sistdown-config\\lib\\AutoHotkey.exe";
        //             String ahkScriptPath = "\\\\10.100.10.219\\Videos$\\Recebidos\\sistdown-config\\scripts\\basic.ahk";
        
        //             String cmd = ahkExePath + " \"" + ahkScriptPath + "\"";
        //             Runtime.getRuntime().exec(cmd);
        //         } catch (IOException e) {
        //             e.printStackTrace();
        //         }
        //     }).start();
        // }
    }

}
