package dalosto.dnit.sistdown.action;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import dalosto.dnit.sistdown.domain.InputArgsModel;
import dalosto.dnit.sistdown.domain.TagsConfiguracao;
import dalosto.dnit.sistdown.domain.Trecho;
import dalosto.dnit.sistdown.handler.PromptInputsHandler;
import dalosto.dnit.sistdown.handler.RecursosHandler;
import dalosto.dnit.sistdown.repository.TrechoRepository;
import dalosto.dnit.sistdown.service.ArquivoService;
import dalosto.dnit.sistdown.service.CaminhoService;
import dalosto.dnit.sistdown.service.LoggerConsoleService;


/**
 * Funcionalidade - APAGA                            <p>
 * Classe que permite que o usuário limpe os trechos que estão armazenados na maquina local.
 */
@Component
@Order(6)
public final class HandleApaga extends Acao {

    @Autowired
    private LoggerConsoleService loggerConsoleService;

    @Autowired
    private ArquivoService arquivoService;

    @Autowired
    private RecursosHandler recursosHandler;

    @Autowired
    private PromptInputsHandler prompt;

    @Autowired
    private TrechoRepository trechoRepository;


    @Override
    public boolean isCalled() {
        var input = prompt.verificaSeFoiSolicitado(TagsConfiguracao.APAGA);
        return input.isStatus();
    }


    @Override
    public void executaCLI() throws Exception {
        try {
            InputArgsModel input = prompt.verificaSeFoiSolicitado(TagsConfiguracao.APAGA);
            int quantidadeDeTrechosParaRemover = getQuantidadeDeTrechosParaRemover(input);
            executa(quantidadeDeTrechosParaRemover);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void executa(int quantidadeDeTrechosParaRemover) throws IOException, FileNotFoundException {
        var trechos = getTrechos(quantidadeDeTrechosParaRemover);
        for (var trecho : trechos) {
            try {
                recursosHandler.delete(Paths.get(CaminhoService.DIR_SISTDOWN_VIDEOS.toString(),
                                       trechoRepository.getPath(trecho.getId())).toFile());
                loggerConsoleService.printaMensagem(trecho + " deletado.");
            } catch (NullPointerException e) {
                loggerConsoleService.printaMensagem("\n\nNão foi possível deletar o trecho " + trecho);
                loggerConsoleService.printaMensagem("Por favor, execute o comando limpa");
            }
        }
        arquivoService.deletaTrechosDoLogs(trechos);
    }


    private int getQuantidadeDeTrechosParaRemover(InputArgsModel input) {
        int quantidadeDeTrechosParaRemover;
        if (input.getArgs() == null || input.getArgs().isEmpty()) {
            quantidadeDeTrechosParaRemover = 5;
        } else {
            quantidadeDeTrechosParaRemover = Integer.valueOf(input.getArgs());
        }
        return quantidadeDeTrechosParaRemover;
    }


    private List<Trecho> getTrechos(int quantidadeDeTrechosParaRemover) throws IOException {
        var trechos = arquivoService.getTrechosBaixados()
                                    .stream()
                                    .limit(quantidadeDeTrechosParaRemover)
                                    .collect(Collectors.toList());
        return trechos;
    }

}
