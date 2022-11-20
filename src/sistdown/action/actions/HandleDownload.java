package action.actions;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;

import model.Inputs;
import model.Trechos;
import service.Caminhos;
import service.Util;

public class HandleDownload implements Acao {

    /**
     * Faz o download dos trechos na maquina local
     */
    public void executa() throws Exception {
        if (Inputs.size() > 0) {
            System.out.println(" * ...Iniciando o download dos trechos");
        }
        while (Inputs.size() > 0) {
            String idTrecho = Inputs.getFirst();
            String caminho = Trechos.getCaminho(idTrecho);
            Inputs.removeFirst();;
            if (caminho == null) {
                System.out.println(" * ...Não foi encontrado o trecho de id: "+idTrecho+".");
                continue;
            } else {
                Path origin = Paths.get(Caminhos.REDE_VIDEO_FOLDER.toString(), caminho);
                Path target = getTarget(caminho);
                Util.deleteFolder(target.toFile());
                Util.copyFolder(origin, target, StandardCopyOption.REPLACE_EXISTING);
                informaQueTrechoFoiBaixado(idTrecho, target);
            } 
        } 
    }



    private Path getTarget(String caminhoTrecho) {
        if (Util.contexto.equalsIgnoreCase("LOCAL"))
            return Paths.get(Caminhos.SISTDOWN_CURRENT.toString(), caminhoTrecho);
        return Paths.get(Caminhos.SISTDOWN_DOWNLOADS_LOCAL.toString(), caminhoTrecho);
    }



    private void informaQueTrechoFoiBaixado(String idTrecho, Path target) throws IOException {
        String nomeTrecho = idTrecho + "-" + target.toString().replaceAll(".+_", "").substring(0, 5);
        Files.write(Caminhos.SISTDOWN_CONFIG_INFODOWNLOADS.toPath(), (nomeTrecho + ",  ").getBytes(), StandardOpenOption.APPEND);
        System.out.println(" * ...>Baixado " + nomeTrecho);
    }

}
