package dalosto.dnit.sistdown;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import dalosto.dnit.sistdown.app.Sistdown;
import jakarta.annotation.PostConstruct;


@SpringBootApplication
public class SistdownApplication {

	@Autowired
	private Sistdown sistdown;


	@PostConstruct
	public void initSistdown() throws Exception {
		sistdown.inicia();
	}


	public static void main(String[] args) {
		SpringApplication.run(SistdownApplication.class, args);
	}

}
