package sistdown.model;

public class ApagaModel {

    private Integer quantidade = -1;

    public ApagaModel (String txt) {
        if (txt != null) {
            txt = txt.replaceAll("[^0-9]","");
            if (!txt.isBlank()) {
                quantidade = Integer.parseInt(txt);
            } else {
                quantidade = 5;
            }
        }
    }

    
    public Integer getQuantidade() {
        return this.quantidade;
    }
    
}
