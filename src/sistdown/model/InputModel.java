package sistdown.model;
import java.util.List;


public class InputModel {

    private boolean status;
    private List<String> args;


    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<String> getArgs() {
        return args;
    }
    
    public void setArgs(List<String> args) {
        this.args = args;
    }

}
