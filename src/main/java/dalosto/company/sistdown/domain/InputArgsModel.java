package dalosto.company.sistdown.domain;
import static dalosto.company.sistdown.service.Util.textoEhValido;
import lombok.Getter;
import lombok.Setter;


public final class InputArgsModel {

    @Getter @Setter
    private boolean status = false;

    @Getter
    private String args = "";


    public void setArgs(String args) {
        if (textoEhValido(args)) {
            this.args = args;
        }
    }

}
