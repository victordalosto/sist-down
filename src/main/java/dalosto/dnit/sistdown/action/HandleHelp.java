package dalosto.dnit.sistdown.action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import dalosto.dnit.sistdown.domain.InputArgsModel;
import dalosto.dnit.sistdown.domain.TagsConfiguracao;
import dalosto.dnit.sistdown.handler.PromptInputsHandler;


/**
 * Funcionalidade - Limpa <p>
 * Classe que permite que o usuário limpe os trechos que estão armazenados na maquina local.
 */
@Component
@Order(7)
public final class HandleHelp extends Acao {

    @Autowired
    private PromptInputsHandler prompt;


    @Override
    public boolean isCalled() {
        InputArgsModel input = prompt.verificaSeFoiSolicitado(TagsConfiguracao.AJUDA);
        return input.isStatus();
    }

    @Value("${sistdown.root.path}")
    private String rootPath;


    @Value("${sistdown.rede.path}")
    private String redePath;


    @Override
    public void executaCLI() throws Exception {
        System.out.println("==============================================");
        System.out.println();
        System.out.println(" COMANDOS:");
        System.out.println(" Para baixar trechos, digite os ids separado por virgula ou espaco. Exemplo:\n > 1234,1235,1236,1237 1238");
        System.out.println();
        System.out.println(" Para limpar o sist-down, basta digitar 'limpa' Exemplo:\n > limpa");
        System.out.println(" > limpa, 1234, 1235, 1236, 1237");
        System.out.println();
        System.out.println(" Para apaga os ultimos 8 trechos, faça :\n > apaga-8");
        System.out.println();
        System.out.println(" Para colocar o sistlev na rede ou na local, siga os seguinte passo a passo:");
        System.out.println("  (i)   Abre a pasta do sistlev : Botao direito no sist-lev: 'Abrir local do arquivo'");
        System.out.println("  (ii)  Edite o arquivo: SistLevEscritorio.exe.config");
        System.out.println("  (iii) Altene [\"raizVideos\" value=] entre  " + redePath + "   ou   " + rootPath);
    }


}
