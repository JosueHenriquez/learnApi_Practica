package IntegracionBackFront.backfront.Config.Cloudinary;

import com.cloudinary.Cloudinary;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


//si hay un error en el config la API ya no se ejecuta
@Configuration
public class CloudinaryConfig {

    private String cloudName;
    private String apiKey;
    private String apiSecret;


    @Bean // se auto ejecuta
    //leer direcatamente el archivo .env
    public Cloudinary cloudinary(){
        //Objeto para leer las variables del .env
        Dotenv dotenv = Dotenv.load();

        //Crear un Map para almacenar las configuracion necesaria para cloudinary
        Map<String, String> config = new HashMap<>();

        config.put("cloud_name", dotenv.get("CLAUDINARY_CLOUD_NAME"));
        config.put("api_key", dotenv.get("CLAUDINARY_API_KEY"));
        config.put("api_secret", dotenv.get("CLAUDINARY_API_SECRET"));

        return new Cloudinary(config);


    }

}
