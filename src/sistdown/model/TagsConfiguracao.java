package sistdown.model;


/**
 * Enum contendo os inputs para acionar as principais funcionalidades do sistema.
 */
public enum TagsConfiguracao {

    LIMPA, LIMPAR, LIMP, 
    HELP, AJUDA;

    /**
     * Verifica se o input enviado é uma Tag para uma funcionalidade.
     */
    public static boolean ehUmaTag(String text) {
        for (TagsConfiguracao tag : TagsConfiguracao.values()) {
            if (text.equalsIgnoreCase(tag.toString()))
                return true;
        }
        return false;
    }

    

    /**
     * Verifica se o input enviado é uma Tag para uma funcionalidade.
     */
    public static boolean ehUmaTagDeLimpar(String text) {
        if (text.toUpperCase().contains(LIMP.toString()))
            return true;
        return false;
    }

    
    /**
     * Verifica se o input enviado é uma Tag para uma funcionalidade.
     */
    public static boolean ehUmaTagDeAjuda(String text) {
        if (text.toUpperCase().contains(HELP.toString())
         || text.toUpperCase().contains(AJUDA.toString()))
            return true;
        return false;
    }
}
