package model;

public enum Tags {

    LOCAL, REDE, LIMPA, LIMPAR, LIMPE, LIMP;


    public static boolean isTag(String text) {
        for (Tags tag : Tags.values()) {
            if (text.equalsIgnoreCase(tag.toString()))
                return true;
        }
        return false;
    }
    
}
