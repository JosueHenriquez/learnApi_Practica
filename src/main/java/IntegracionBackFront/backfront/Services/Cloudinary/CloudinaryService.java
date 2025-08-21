package IntegracionBackFront.backfront.Services.Cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

@Service
public class CloudinaryService {

    //1.Constante para definir el tamaño maximo permitido para los archivos (5MB)
    private static  final long MAX_FILE_SIZE = 5 * 1024 * 1024;

    //2. Extensiones de archivo permitidas para subir a Cloudinary
    private static final String[] ALLOWE_EXTENSIONS = {".jpg", ".png", ".jpeg"};

    //Extension del archivo sirve para identificar los archivos y

    //3. Cliente de cloudinary inyectado como dependecia
    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    //Subir imagenes a la raiz de Cloudinary
    public String uploadImage(MultipartFile file) throws IOException{
        validateImage(file);
        Map<?, ?> uploadResult = cloudinary.uploader()
                .upload(file.getBytes(), ObjectUtils.asMap(
                        "resource_type", "auto",
                        "quality", "auto:good"
                ));
        return (String) uploadResult.get("secure_url");
    }

    public String uploadImage(MultipartFile file, String folder) throws IOException{
        validateImage(file);
        String originalFilename = file.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        String uniqueFilenames = "img_" + UUID.randomUUID() + fileExtension;

        Map<String, Object> options = ObjectUtils.asMap(
                "folder", folder,        //Carpeta de destino
                "public_id", uniqueFilenames,    //Nombre unico para el archio
                "user_filename", false,         //No usar el nombre original
                "unique_filename", false,       //No generar nombre unico (ya lo hicimos)
                "overwrite", false,            //No sobreescribir archivos existetes
                "resource_type", "auto",
                "quality", "auto:good"
        );
        Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), options);
        return  (String) uploadResult.get("secure_url");
    }

    private void validateImage(MultipartFile file) {
        //1. Verificar si e archivo esta vacio
        if(file.isEmpty()) throw  new IllegalArgumentException(("El archivo no puede estar vacio"));
        //2.verificar si el tamaño del archivo excede el limite permitido
        if (file.getSize() > MAX_FILE_SIZE) throw new IllegalArgumentException("El tamaño del archivo no puede exceder lo 5MBS");
        //3. Validar el nombre original del archivo
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) throw  new IllegalArgumentException("Nombre de archivo no valido");
        //4. Validar que
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        if (!Arrays.asList(ALLOWE_EXTENSIONS).contains(extension)) throw new IllegalArgumentException("Solo se permite archivos jpg, jpeg o png");
        if (!file.getContentType().startsWith("image/")) throw  new IllegalArgumentException("El archivo debe ser una imagen valida");

    }
}
