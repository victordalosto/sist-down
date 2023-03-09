package sistdown.service;
import sistdown.Main;
import sistdown.Handler.PromptInputsHandler;
import sistdown.model.Version;


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
        PromptInputsHandler.reiniciaPromptDigitados();
        System.out.println("\n\n");
    }


    public static boolean textoEhValido(String text) {
        if (text != null && !text.isBlank()) {
            return true;
        }
        return false;
    }


    public static String getVersaoSistdown() {
        Class<?> main = Main.class;
        Version version = main.getDeclaredAnnotation(Version.class);
        return version.value();
    }
    
}
