package dalosto.sistdown;
import java.io.IOException;
import dalosto.sistdown.app.Sistdown;


public class Main {

    public static void main(String[] args) throws ClassNotFoundException, IOException {
        // List<Class<? extends Acao>> implementations = ImplementationFinder
        //         .findClases("dalosto.sistdown", Acao.class);
        Sistdown.inicia();
    }

}