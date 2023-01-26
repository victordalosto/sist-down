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



    public static boolean textEhValido(String text) {
        if (text != null && !text.isBlank()) 
            return true;
        return false;
    }



    public static String getSistdownVersion() {
        Class<?> main = Main.class;
        Version version = main.getDeclaredAnnotation(Version.class);
        return version.value();
    }



    public static String getAuthenticationIP() {
        return "139.144.52.108";
    }
    
}
