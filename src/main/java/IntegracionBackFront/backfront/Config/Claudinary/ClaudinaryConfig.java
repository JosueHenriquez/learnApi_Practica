package IntegracionBackFront.backfront.Config.Claudinary;

import com.cloudinary.Cloudinary;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

//si algo en la configuracion falla, la api no corre
@Configuration
public class ClaudinaryConfig {

    private String cloudName;
    private String apiKey;
    private String apiSecret;

    @Bean
    public Cloudinary cloudinary() {

        //carga las variables del archivo .env
        Dotenv dotenv = Dotenv.load();

        //mapa para almacenar la ubicacion
        Map<String, String> config = new HashMap<>();

        //obtener las credenciales desde las variables de entorno
        config.put("cloud_name", dotenv.get("CLAUDINARY_CLOUD_NAME"));
        config.put("api_key", dotenv.get("CLAUDINARY_API_KEY"));
        config.put("api_secret", dotenv.get("CLAUDINARY_API_SECRET"));

        //retornar una nueva instancia de cloudinary con la configuracion cargada
        return new Cloudinary(config);

    }
}
