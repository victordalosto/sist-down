package sistdown.action.actions;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import sistdown.model.ApagaModel;
import sistdown.model.PromptInputs;
import sistdown.service.Caminho;
import sistdown.service.TrechoRepository;
import sistdown.service.RecursosHandler;
import sistdown.service.Logger;


/**
 * Funcionalidade - APAGA <p>
 * Classe que permite que o usuário limpe os trechos que estão armazenados na maquina local.
 */
public class Apaga implements Acao {


    public void executa() throws Exception {
        ApagaModel apaga = PromptInputs.foiSolicitadoApagar();
        if (apaga.getQuantidade() >= 0) {
            try {
                List<String> lines = Files.readAllLines(Paths.get(Caminho.FILE_TARGET_INFO_DOWNLOADS.toString()));
                List<Integer> idsToRemove = new ArrayList<>();
                for (int i = 0; i < lines.size() && i < apaga.getQuantidade(); i++) {
                    String id = lines.get(i).split(";")[0];
                    idsToRemove.add(i);
                    File path = Paths.get(Caminho.DIR_TARGET_VIDEOS_ROOT.toString(), TrechoRepository.getPath(id)).toFile();
                    RecursosHandler.delete(path);
                    System.out.println(" * " + id + " deletado.");
                }
                for (int i = idsToRemove.size() -1; i>=0; i--) {
                    lines.remove(i);
                }
                Logger.clearLog();
                for (String item : lines) {
                    Files.write(Caminho.FILE_TARGET_INFO_DOWNLOADS.toPath(), (item+"\n").getBytes(), StandardOpenOption.APPEND);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

}
