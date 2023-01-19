package sistdown.action.actions;
import java.nio.file.Files;
import java.nio.file.Paths;

import sistdown.service.Caminho;
import sistdown.service.Util;


/**
 * Faz o print na tela dos textos de inicialização.      <p>
 * Printa a logo de inicio ou a logo de reinicializacao,
 * bem como também printa os trechos que foram baixados.
 */
public class PrintaInicio implements Acao {
    
    
    public void executa() throws Exception {
        if (Util.verificaSeEhAPrimeiraVezRodandoOPrograma()) {
            System.out.println("");
            System.out.println(" SISTDOWN: " + Util.getVersion());
        }
        printaTrechosQueEstaoNaMaquinaLocal();
        System.out.print(" FLY-now> ");
    }




    private static void printaLinha() {
        System.out.println(" ================================================================================");
    }

    

    /**
     * Printa os trechos que estão baixados na maquina local.
     */
    private static void printaTrechosQueEstaoNaMaquinaLocal() throws Exception {
        String trechosNaLocal = Files.readString(Paths.get(Caminho.SISTDOWN_INFO_DOWNLOADS.toString()))
        .replaceAll("\\s+", "").replaceAll(",$", "");
        if (trechosNaLocal.equals("")) {
            printaLinha();
            System.out.println(" * 0 trechos baixados.");
        } else {
            System.out.println(" =============================== TRECHOS BAIXADOS ===============================");
            String [] rows = trechosNaLocal.split(",");
            for (int i = 0; i < rows.length; i++) {
                if (i == 0) System.out.print("     ");
                else if (i%4 == 0)  System.out.print("\n     ");
                else System.out.print("         ");
                System.out.print(rows[i]);
            }
        }
        System.out.println();
        printaLinha();
    }


}
