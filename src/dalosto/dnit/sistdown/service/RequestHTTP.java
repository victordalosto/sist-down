package dalosto.dnit.sistdown.service;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import dalosto.dnit.sistdown.framework.annotations.Component;


@Component
public class RequestHTTP {

    public String obtemTokenValidacao() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://github.com/victordalosto/sist-down")).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        return response.body();
    }

}
