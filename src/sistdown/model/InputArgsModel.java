package sistdown.model;

import sistdown.service.Util;

public class InputArgsModel {

    private boolean status = false;
    private String args = "5";


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
        if (Util.textoEhValido(args)) {
            this.args = args;
        }
    }

}
