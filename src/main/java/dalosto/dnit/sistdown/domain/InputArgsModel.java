package dalosto.dnit.sistdown.domain;
import static dalosto.dnit.sistdown.service.Util.textoEhValido;


public final class InputArgsModel {

    private boolean status = false;
    private String args = "";


    public boolean foiSolicitado() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getArgs() {
        return args;
    }
    
    public void setArgs(String args) {
        if (textoEhValido(args)) {
            this.args = args;
        }
    }

}
