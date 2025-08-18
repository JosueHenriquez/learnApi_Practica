package IntegracionBackFront.backfront;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackfrontApplication {

	public static void main(String[] args) {
		//Codigo para cargar los valores del archivo .env sobre el archivo application.properties
		Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
		dotenv.entries().forEach(entry ->
				System.setProperty(entry.getKey(), entry.getValue())
		);

		//Esta linea no se borra
		SpringApplication.run(BackfrontApplication.class, args);
	}

}
