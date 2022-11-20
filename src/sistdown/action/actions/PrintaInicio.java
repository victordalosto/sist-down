package action.actions;

import model.Trechos;
import service.Util;

public class PrintaInicio implements Acao {
    

    /**
     * Faz o print na tela dos textos de inicialização.
     * Printa a logo de inicio ou a logo de reinicializacao,
     * bem como também printa os trechos que foram baixados.
     */
    public void executa() throws Exception {
        if (Util.primeiraRun()) {
            printaLinha();
            String message = """
                     *      __  _
                     *    /  _|| |_   _   _ __   _____      __
                     *    | |_ | | | | | | '_ \\ / _ \\ \\ /\\ / /
                     *    |  _|| | |_| | | | | | (_) \\ V  V /
                     *    |_|  |_|\\__, | |_| |_|\\___/ \\_/\\_/                     
                     *            |___/                                              SIST-DOWN v2.1.0
                    """;
            System.out.print(message);
        } else {
            System.out.println(" * SISTDOWN Reiniciado");
        }
        printaLinha();
        System.out.println(" * MODO: " + Util.contexto);
        Trechos.printaTrechosQueEstaoNaMaquinaLocal();
        printaLinha();
        if (Util.primeiraRun()) {
            System.out.println(" * Para 'baixar' trechos, digite os ids separados por virgula.  Ex: 100, 101, 102");
            System.out.println(" * Para 'limpar' e também baixar acrescente o parametro limpa.  Ex: limpa, 85, 86");    
        }
        System.out.println(" * Para mudar o contexto do Sistlev, digite 'rede' ou 'local'");
        printaLinha();
        System.out.print(" FLY-now> ");
    }



    private static void printaLinha() {
        System.out.println(" ================================================================================");
    }



}
