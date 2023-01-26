package sistdown.action.actions;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import sistdown.service.Util;


/**
 * Faz o Bootstrap da aplicação:                <p>
 * (i) Cria pastas para fazer a inicialização;  <p>
 * (ii) Carrega uma List com os possiveis trechos para download.
 */
public class Autorizacao implements Acao {


    public void executa() throws Exception {
        if (Util.verificaSeEhAPrimeiraVezRodandoOPrograma()) {
            String token = obtemTokenValidacao();
            if (!isAuthtenticated(token)){
                throw new RuntimeException("\n\n * Servidor " + Util.getAuthenticationIP() +" não autenticou o sist-down.\n * Servidor possivelmente fora do ar.\n\n");
            }
        }
    }



    private String obtemTokenValidacao() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
                    .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://" + Util.getAuthenticationIP() + "/"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        return response.body();
    }



    private boolean isAuthtenticated(String auth) {
        String pass = "zH$QeJwagH#$AB5!@#*45$asWjV+ab=c%&*ap0lJp~(b8r'Ti-Qas4ç";
        if (auth.contains(pass))
            return true;
        return false;
    }

}
