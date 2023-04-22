package dalosto.dnit.sistdown.service;

/**
 *  Classe <b>Util</b> contendo métodos estáticos utilitários. <p>
 *  Funções gerais utilizadas fora dos contextos das classes construídas.  
 */
public class Util {

    private static boolean primeiraInicializacao = true;


    public static boolean verificaSeEhAPrimeiraVezRodandoOPrograma() {
        if (primeiraInicializacao) {
            return true;
        }
        return false;
    }

    
    public static void iniciaNovamenteOPrograma() {
        primeiraInicializacao = false;
        System.out.println("\n");
    }


    public static boolean textoEhValido(String text) {
        if (text != null && !text.isBlank()) {
            return true;
        }
        return false;
    }

    
}
