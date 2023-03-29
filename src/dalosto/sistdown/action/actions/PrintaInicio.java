package dalosto.sistdown.action.actions;
import dalosto.sistdown.service.Registrador;


/**
 * Faz o print na tela dos textos de inicialização.      <p>
 * Printa a logo de inicio ou a logo de reinicializacao,
 * bem como também printa os trechos que foram baixados.
 */
public class PrintaInicio implements Acao {
    
    
    public void executa() throws Exception {
        Registrador.printaInicio();
    }

}
