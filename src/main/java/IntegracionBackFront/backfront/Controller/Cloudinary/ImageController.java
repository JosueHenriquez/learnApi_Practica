package IntegracionBackFront.backfront.Controller.Cloudinary;

import com.cloudinary.Cloudinary;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;

@RestController
@RequestMapping("/api/image")
public class ImageController {

    //1. definir el tamaño de las imagenes en MB
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;

    //2.Definir las extenciones permitidas
    private static final String[] ALLOWED_EXTENSIONS = {".jpg", ".jpeg", ".png"};

    //3.Atributo cloudinary
    private final Cloudinary cloudinary;

    //4.Crear un constructor de para inyeccion de independecias en cludinary
    public ImageController(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadImage(MultipartFile file) throws IOException {
        validateImage(file);
    }

    private void validateImage(MultipartFile file) {
        //1. verificar si el archivo esta vacio
        if (file.isEmpty()){
            throw new IllegalArgumentException(("El archivo esta vacio"));
        }

        //2. Verificar si el tamaño excede el limite permitido
        if (file.getSize() > MAX_FILE_SIZE){
            throw new IllegalArgumentException(("El tamaño del archivo no debe ser mayor a 5MB"));
        }

        //3. Verificar el nombre original del archivo
        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null){
            throw new IllegalArgumentException(("Nombre de archivo invalido"));
        }

        //4.Extraer y validar la extencion del archivo
        String extension = originalFileName.substring(originalFileName.lastIndexOf(".")).toLowerCase();
        if (!Arrays.asList(ALLOWED_EXTENSIONS).contains(extension)){
            throw new IllegalArgumentException(("Solo se permite archivos jpg, jpeg, png"));
        }

        //5.Verificar que el tipo MIME sea una imagen
        if (!file.getContentType().startsWith("image/")){
            throw new IllegalArgumentException("El archivo debe ser una imagen invalido");
        }
    }

}
