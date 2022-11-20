package model;

public enum TagsConfiguracao {

    LOCAL, REDE, LIMPA, LIMPAR, LIMPE, LIMP;


    
    /** Verifica se o input enviado é um arquivo de Configuracao */
    public static boolean isTag(String text) {
        for (TagsConfiguracao tag : TagsConfiguracao.values()) {
            if (text.equalsIgnoreCase(tag.toString()))
                return true;
        }
        return false;
    }
    
}
