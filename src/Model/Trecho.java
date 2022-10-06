package Model;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Trecho {

    private String HD;
    private String lote;
    private String id;
    private Path targetHD;
    private Path caminhoRede;


    
    public String getHD() {
        return HD;
    }
    public void setHD(String hD) {
        HD = hD;
    }


    public String getLote() {
        return lote;
    }
    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }


    public Path getTargetHD() {
        return targetHD;
    }
    public void setTargetHD(Path targetHD) {
        this.targetHD = targetHD;
    }

    public Path getCaminhoRede() {
        return caminhoRede;
    }
    public void setCaminhoRede(Path caminhoRede) {
        caminhoRede = Paths.get(caminhoRede.toString(), lote, 
                            String.valueOf(HD), targetHD.toString());
        this.caminhoRede = caminhoRede;
    }
    
}
