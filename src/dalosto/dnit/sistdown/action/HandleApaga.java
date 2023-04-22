package dalosto.dnit.sistdown.action;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import dalosto.dnit.sistdown.domain.InputArgsModel;
import dalosto.dnit.sistdown.domain.TagsConfiguracao;
import dalosto.dnit.sistdown.framework.annotations.Autowired;
import dalosto.dnit.sistdown.framework.annotations.Component;
import dalosto.dnit.sistdown.framework.annotations.Order;
import dalosto.dnit.sistdown.handler.PromptInputsHandler;
import dalosto.dnit.sistdown.handler.RecursosHandler;
import dalosto.dnit.sistdown.helper.CaminhoHelper;
import dalosto.dnit.sistdown.repository.TrechoRepository;
import dalosto.dnit.sistdown.service.LoggerArquivoService;
import dalosto.dnit.sistdown.service.LoggerConsoleService;


/**
 * Funcionalidade - APAGA <p>
 * Classe que permite que o usuário limpe os trechos que estão armazenados na maquina local.
 */
@Component
@Order(6)
public class HandleApaga implements Acao {

    @Autowired
    LoggerConsoleService loggerConsoleService;

    @Autowired
    LoggerArquivoService loggerArquivoService;


    public void executa() throws Exception {
        InputArgsModel input = PromptInputsHandler.verificaSeFoiSolicitado(
                                            (txt) -> TagsConfiguracao.textEhUmaTag(txt, TagsConfiguracao.APAGA));
        if (input.foiSolicitado()) {
            try {
                List<String> lines = loggerArquivoService.readAllLog();
                List<String> idsToRemove = new ArrayList<>();
                for (int i = 0; i < lines.size() && i < Integer.valueOf(input.getArgs()); i++) {
                    String id = lines.get(i).split(";")[0];
                    idsToRemove.add(lines.get(i));
                    RecursosHandler.delete(Paths.get(CaminhoHelper.DIR_TARGET_VIDEOS_ROOT.toString(), TrechoRepository.getPath(id)).toFile());
                    loggerConsoleService.printaMensagem(id + " deletado.");
                }
                lines.removeAll(idsToRemove);
                loggerArquivoService.recriaLogApartirDeString(lines);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

}
