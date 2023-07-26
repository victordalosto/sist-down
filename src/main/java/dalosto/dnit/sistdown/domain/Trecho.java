package dalosto.dnit.sistdown.domain;
import java.util.ArrayList;
import java.util.List;
import dalosto.dnit.sistdown.service.Util;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(of = "id")
public final class Trecho {

    private String id;
    private String uf;
    private String br;


    public Trecho(String row) {
        if (Util.textoEhValido(row) &&  row.contains(";") && row.split(";").length == 3) {
            this.id = row.split(";")[0];
            this.uf = row.split(";")[1];
            this.br = row.split(";")[2];
        }
    }


    public static List<Trecho> factory(List<String> lines) {
        List<Trecho> trechos = new ArrayList<>();
        for (String line : lines) {
            trechos.add(new Trecho(line));
        }
        return trechos;
    }


    @Override
    public String toString() {
        if (id == null || uf == null || br == null) {
            return "";
        }
        while (id.length() < 5) {
            id = " " + id;
        }
        return "[ "+id+" - " + uf + "/" + br +" ]";
    }


    public String saveFormat() {
        return id+";"+uf+";"+br+"\n";
    }

}
