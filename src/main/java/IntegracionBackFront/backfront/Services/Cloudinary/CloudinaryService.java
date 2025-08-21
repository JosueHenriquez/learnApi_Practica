package IntegracionBackFront.backfront.Services.Cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

@Service
public class CloudinaryService {

    //1.constante para permitir el maximo tamaño permitido par los archivos (5MB)
    private static final long MAX_FILE_SIZE =  5*1024*1024;

    //2.extensiones de archivo permitidas para subir a cloudinary
    private static final String[] Allowed_Extensions = {".jpg",".png",".jpeg"};

    //3. Cliente de Cloudinary inyectado como dependecia
    private final Cloudinary cloudinary;

    //constructor para inyeccion de despendencias de Cloudinary
    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    // subir imagenes a la raiz de Cloudinary
    public String uploadImage(MultipartFile file) throws IOException {
        validateImage(file);
        Map<?,?> uploadResult = cloudinary.uploader()
                .upload(file.getBytes(), ObjectUtils.asMap(
                        "resource_type", "auto",
                        "quality", "auto:good"
                ));
        return (String) uploadResult.get("secure_url");
    }

    //contructor
    private void validateImage(MultipartFile file) {
        //1. verificar si el archivo esta vacio
        if (file.isEmpty()) throw new IllegalArgumentException("El archivo esta vacio");

        //2. verifica si el tamaño excede el limite
        if (file.getSize() > MAX_FILE_SIZE) throw new IllegalArgumentException("El tamaño del archivo no debe ser mayor a 5MB");

        //3. verificar el nombre original del archivo
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) throw new IllegalArgumentException("Nombre de archivo invalido");

        //4. Extraer y validar la extension del archivo
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        if (!Arrays.asList(Allowed_Extensions).contains(extension)) throw new IllegalArgumentException("Solo se permite archivos jpg, jpeg o png");

        //5. verificar que es tipo MIME sea una imagen
        if (!file.getContentType().startsWith("image/")) throw new IllegalArgumentException("El archivo debe ser una imagen válida");
    }


    // Subir imagenes a una carpeta de Clodunary
}
