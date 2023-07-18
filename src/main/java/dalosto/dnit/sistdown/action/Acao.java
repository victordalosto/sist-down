package dalosto.dnit.sistdown.action;


/**
 * Interface Acao usada para chamada de metodos usando o 
 * Chain of responsability em conjunto com os Design Pattern 
 * de Strategy e Template Method.
 */
public abstract class Acao {

    /**
     * Roda o metodo a ser executado na cadeia de ações.
     * @throws Exception
     */
    public final void run() throws Exception {
        if (isCalled()) {
            executa();
        }
    }


    protected abstract void executa() throws Exception ;

    protected abstract boolean isCalled();

    
}
