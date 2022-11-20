package action.actions;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;

import model.Inputs;
import model.TagsConfiguracao;
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
            int index = 0;
            String idTrecho = Inputs.listaComInputs.get(index);
            String caminhoTrecho = Trechos.getCaminho(idTrecho);
            if (caminhoTrecho != null) {
                Path caminhoRede = Paths.get(Caminhos.REDE_VIDEO_FOLDER.toString(), caminhoTrecho);
                Path caminhoSistdown = Paths.get(Caminhos.SISTDOWN_CURRENT.toString(), caminhoTrecho);
                if (Util.contexto.equalsIgnoreCase("LOCAL"))
                    caminhoSistdown = Paths.get(Caminhos.SISTDOWN_CURRENT.toString(), caminhoTrecho);
                else 
                    caminhoSistdown = Paths.get(Caminhos.SISTDOWN_DOWNLOADS_LOCAL.toString(), caminhoTrecho);
                Util.deleteFolder(caminhoSistdown.toFile());
                Util.copyFolder(caminhoRede, caminhoSistdown, StandardCopyOption.REPLACE_EXISTING);
                String nomeTrecho = idTrecho + "-" + caminhoSistdown.toString().replaceAll(".+_", "").substring(0, 5);
                Files.write(Caminhos.SISTDOWN_CONFIG_INFODOWNLOADS.toPath(), (nomeTrecho + ",  ").getBytes(), StandardOpenOption.APPEND);
                System.out.println(" * ...>Baixado " + nomeTrecho);
            } else {
                if (!TagsConfiguracao.isTag(idTrecho)) {
                    System.out.println(" * ...Não foi encontrado o trecho de id: " + Inputs.listaComInputs.get(index) + ".");
                }
            }
            Inputs.listaComInputs.remove(index);
        } 
    }


}
