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

    //1. Constante para definir el tamaño maximo definido para los archivos (5MB)
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;

    //Declarar las extensiones de archivos permitidas para subir a cloudinary
    private static final String[] ALLOWED_EXTENSIONS = {".jpg", ".png", ".jpeg"};

    //3.Cliente cloudinary inyectando como la dependencia
    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    //Subir imagenes a la raiz de cloudinary
    public String uploadImage(MultipartFile file) throws IOException{
        validateImage(file);
        Map<?, ?> uploadResult = cloudinary.uploader()
                .upload(file.getBytes(), ObjectUtils.asMap(
                        "resource_type", "auto",
                        "quality", "auto:good"
                ));
        return (String) uploadResult.get("secure_url");
    }

    private void validateImage(MultipartFile file) {
        //1.verificar si el archivo esta vacio
        if (file.isEmpty())throw new IllegalArgumentException("El archivo no puede estar vacio");
        if (file.getSize() > MAX_FILE_SIZE) throw new IllegalArgumentException("El tanaño del archivo no puede exceder los 5MB");//Verificar si el tamaño del archivo excede el limite permitido
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null)throw  new IllegalArgumentException("Nombre del archivo no valido"); //Valida el nombre originar del archivo
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        if (!Arrays.asList(ALLOWED_EXTENSIONS).contains(extension))throw new IllegalArgumentException("Solo se permiten archivos .jpg, .jpeg o png");
        if(!file.getContentType().startsWith("image/"))throw new IllegalArgumentException("El archivo debe de ser una imagen valida");
    }

    //Subir imagenes a una carpeta de Cloudinary
    public String uploadImage(MultipartFile file, String folder) throws IOException{
        validateImage(file);
        String originalFilename = file.getOriginalFilename();
        String fileExtensions = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        String uniqueFilename = "img_" + UUID.randomUUID() + fileExtensions;

        Map<String, Object>options = ObjectUtils.asMap(
                "folder", folder, //Carpeta destino
                "public_id", uniqueFilename, //Nombre unico para el archivo
                "use_filename", false, //No usar el nombre original
                "overwrite", false, //No sobreescribir archivos existentes
                "quality", "auto:good"
        );
        Map<?, ?>uploadResult = cloudinary.uploader().upload(file.getBytes(), options);
        return (String) uploadResult.get("secure_url");
    }
}
