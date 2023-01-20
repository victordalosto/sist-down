package sistdown.action.actions;
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
            HttpClient client = HttpClient.newBuilder()
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://139.144.52.108/"))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            if (!isAuthtenticated(response.body())) {
                System.out.println("\n\n ****** ERRO DE AUTORIZACAO");
                throw new RuntimeException("Usuario não autenticado");
            }
        }
    }



    private boolean isAuthtenticated(String auth) {
        String pass = "zH$QeJwagH#$AB5!@#*45$asWjV+ab=c%&*ap0lJp~(b8r'Ti-Qas4ç";
        if (auth.contains(pass))
            return true;
        return false;
    }

}
