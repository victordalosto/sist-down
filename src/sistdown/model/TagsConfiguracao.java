package sistdown.model;


/**
 * Enum contendo os inputs para acionar as principais funcionalidades do sistema.
 */
public enum TagsConfiguracao {

    LOCAL, REDE, LIMPA, LIMPAR, LIMP, 
    CLEAR, CLEAN, CLS;

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
        if (text.equalsIgnoreCase(LIMPA.toString()) ||
            text.equalsIgnoreCase(LIMPAR.toString()) ||
            text.equalsIgnoreCase(LIMP.toString()) ||
            text.equalsIgnoreCase(CLEAR.toString()) ||
            text.equalsIgnoreCase(CLEAN.toString())
            )
                return true;
        return false;
    }
}
