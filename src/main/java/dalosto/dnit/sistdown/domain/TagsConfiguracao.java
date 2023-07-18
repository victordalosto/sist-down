package dalosto.dnit.sistdown.domain;


/** Enum contendo os inputs para acionar as principais funcionalidades do sistema. */
public enum TagsConfiguracao {

    // LIMPA, AJUDA, APAGA
    LIMPA, AJUDA, APAGA;


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

    
    public static String removeTagFromString(String text) {
        for (TagsConfiguracao tag : TagsConfiguracao.values()) {
            text = text.toUpperCase().replaceAll(tag.toString(), "");
        }
        text = text.replaceAll("[:=-]", "");
        return text;
    }
}
