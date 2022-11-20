package action.actions;

public interface Acao {

    /**
     * Determina o metodo a ser executado na Chain.
     * Construção usando o design pattern = Strategy
     * @throws Exception
     */
    void executa() throws Exception ;
    
}
