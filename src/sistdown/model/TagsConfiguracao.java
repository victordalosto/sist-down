package sistdown.model;


/** Enum contendo os inputs para acionar as principais funcionalidades do sistema. */
public enum TagsConfiguracao {

    // LIMPA, HELP, AJUDA, APAGA
    LIMPA, HELP, AJUDA, APAGA;


    /**
     * Verifica se o input enviado é uma Tag para uma funcionalidade.
     */
    public static boolean textEhUmaTag(String text) {
        for (TagsConfiguracao tag : TagsConfiguracao.values()) {
            if (text.toUpperCase().contains(tag.toString())) {
                return true;
            }
        }
        return false;
    }

    
    /**
     * Verifica se o input enviado é uma Tag para uma funcionalidade.
     */
    public static boolean textEhUmaTag(String text, TagsConfiguracao tag) {
        if (text.toUpperCase().contains(tag.toString())) {
            return true;
        }
        return false;
    }

    /**
     * Verifica se o input enviado é uma Tag para uma funcionalidade.
     */
    public static boolean ehUmaTagDeLimpar(String text) {
        if (text.toUpperCase().contains(LIMPA.toString())) {
            return true;
        }
        return false;
    }

    
    /**
     * Verifica se o input enviado é uma Tag para uma funcionalidade.
     */
    public static boolean ehUmaTagDeApagar(String text) {
        if (text.toUpperCase().contains(APAGA.toString()))
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


    public static String removeTagFromString(String text) {
        for (TagsConfiguracao tag : TagsConfiguracao.values()) {
            text = text.replaceAll(tag.toString(), "");
        }
        text = text.replaceAll("[:=-]", "");
        return text;
    }
}
