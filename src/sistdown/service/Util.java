package sistdown.service;
import sistdown.Main;
import sistdown.model.Version;


/**
 *  Classe <b>Util</b> contendo métodos estáticos utilitários. <p>
 *  Funções gerais utilizadas fora dos contextos das classes construídas.  
 */
public class Util {

    private static boolean primeiraInicializacao = true;


    public static boolean verificaSeEhAPrimeiraVezRodandoOPrograma() {
        if (primeiraInicializacao)
            return true;
        return false;
    }


    
    public static void iniciaNovamenteOPrograma() {
        primeiraInicializacao = false;
    }



    /**
     * Verifica se o texto digitado é um input valido. Pode ser um trecho ou uma Tag.
     */
    public static boolean isValid(String text) {
        if (text != null && !text.isBlank()) 
            return true;
        return false;
    }


    
    /**
     * @return Current version of Sistdown
     */
    public static String getVersion() {
        Class<?> main = Main.class;
        Version version = main.getDeclaredAnnotation(Version.class);
        return version.value();
    }
    
}
