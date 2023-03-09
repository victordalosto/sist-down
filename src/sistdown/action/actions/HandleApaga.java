package sistdown.action.actions;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import sistdown.Handler.PromptInputsHandler;
import sistdown.Handler.RecursosHandler;
import sistdown.model.InputArgsModel;
import sistdown.model.TagsConfiguracao;
import sistdown.repository.TrechoRepository;
import sistdown.service.Caminho;
import sistdown.service.Registrador;


/**
 * Funcionalidade - APAGA <p>
 * Classe que permite que o usuário limpe os trechos que estão armazenados na maquina local.
 */
public class HandleApaga implements Acao {


    public void executa() throws Exception {
        InputArgsModel input = PromptInputsHandler.verificaSeFoiSolicitado(
                                            (txt) -> TagsConfiguracao.textEhUmaTag(txt, TagsConfiguracao.APAGA));
        if (input.foiSolicitado()) {
            try {
                List<String> lines = Registrador.readAllLog();
                List<String> idsToRemove = new ArrayList<>();
                for (int i = 0; i < lines.size() && i < Integer.valueOf(input.getArgs()); i++) {
                    String id = lines.get(i).split(";")[0];
                    idsToRemove.add(lines.get(i));
                    RecursosHandler.delete(Paths.get(Caminho.DIR_TARGET_VIDEOS_ROOT.toString(), TrechoRepository.getPath(id)).toFile());
                    Registrador.printaMensagemConsole(id + " deletado.");
                }
                lines.removeAll(idsToRemove);
                Registrador.recriaLogApartirDeString(lines);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

}
