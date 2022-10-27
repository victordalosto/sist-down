package model;

public enum Tags {

    LOCAL, REDE, LIMPA, LIMPAR;


    public static boolean isTag(String text) {
        for (Tags tag : Tags.values()) {
            if (text.equalsIgnoreCase(tag.toString()))
                return true;
        }
        return false;
    }
    
}
