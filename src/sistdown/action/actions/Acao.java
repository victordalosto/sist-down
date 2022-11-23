package sistdown.action.actions;


/**
 * Interface <b>Acao</b> usada para chamada de metodos usando o Chain of responsability
 * em conjunto com o Design Pattern de Strategy.
 */
public interface Acao {

    /**
     * Define o metodo a ser executado na cadeia de ações.
     * @throws Exception
     */
    void executa() throws Exception ;
    
}
