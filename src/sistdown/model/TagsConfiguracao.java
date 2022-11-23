package sistdown.model;


/**
 * Enum contendo os inputs para acionar as principais funcionalidades do sistema.
 */
public enum TagsConfiguracao {

    LOCAL, REDE, LIMPA;


    /**
     * Verifica se o input enviado é uma Tag para uma funcionalidade.
     */
    public static boolean isTag(String text) {
        for (TagsConfiguracao tag : TagsConfiguracao.values()) {
            if (text.equalsIgnoreCase(tag.toString()))
                return true;
        }
        return false;
    }

}
