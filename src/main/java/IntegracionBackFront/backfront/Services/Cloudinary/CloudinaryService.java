package IntegracionBackFront.backfront.Services.Cloudinary;

import com.cloudinary.Cloudinary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;

@Service
public class CloudinaryService {

    //1. definir el tamaño de las imagenes en MB
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; //5 MB

    //2. extenciones permitidas
    private static final String[] ALLOWEB_EXTENSIONS = {".jpg",", jpeg", ".png"};

    //3. atributo Cloudinary
    private final Cloudinary cloudinary;

    //constructor para inyeccion de despendencias de Cloudinary
    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadImage(MultipartFile file) throws IOException {
        validateImage(file);
    }

    private void validateImage(MultipartFile file) {
        //1. verificar si el archivo esta vacio
        if (file.isEmpty()) {
            throw new IllegalArgumentException("El archivo esta vacio");
        }

        //2. verifica si el tamaño excede el limite
        if (file.getSize() > MAX_FILE_SIZE){
            throw new IllegalArgumentException("El tamaño del archivo no debe ser mayor a 5MB");
        }

        //3. verificar el nombre original del archivo
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            throw new IllegalArgumentException("Nombre de archivoo invalido");

        }

        //4. Extraer y validar la extension del archivo
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        if (!Arrays.asList(ALLOWEB_EXTENSIONS).contains(extension)){
            throw new IllegalArgumentException("Solo se permite archivos JPG, JPEG, PNG");
        }

        //5. verificar que es tipo MIME sea una imagen
        if (!file.getContentType().startsWith("image/")){
            throw new IllegalArgumentException("El archivo debe ser una imagen válida");
        }
    }

}
