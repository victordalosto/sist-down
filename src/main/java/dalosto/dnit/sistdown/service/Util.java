package dalosto.dnit.sistdown.service;

/**
 *  Classe Util contendo métodos estáticos utilitários. <p>
 *  Funções gerais utilizadas fora dos contextos das classes construídas.  
 */
public class Util {

    private static boolean primeiraInicializacao = true;


    public static boolean ehAPrimeiraVezRodandoOPrograma() {
        return primeiraInicializacao;
    }

    
    public static void reiniciaOPrograma() {
        primeiraInicializacao = false;
    }


    public static boolean textoEhValido(String text) {
        return (text != null && !text.isBlank());
    }

    
}
