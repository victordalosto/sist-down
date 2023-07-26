package dalosto.dnit.sistdown.service;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import org.springframework.stereotype.Component;


@Component
public final class HTTPService {

    public String obtemTokenValidacao() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                                         .uri(URI.create("https://github.com/victordalosto/sist-down"))
                                         .GET()
                                         .build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        System.out.println(response.body());
        return response.body();
    }

}
