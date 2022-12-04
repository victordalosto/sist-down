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
                    .uri(URI.create("https://github.com/victordalosto/sist-down/blob/master/assets/value.json"))
                    .GET()
                    .build();


            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

            if (!response.body().contains("*zH$QeJwagH#$AB5")) {
                System.out.println("\n\n ****** ERRO DE AUTORIZACAO");
                throw new RuntimeException("Usuario não autenticado");
            }
        }
    }

}
