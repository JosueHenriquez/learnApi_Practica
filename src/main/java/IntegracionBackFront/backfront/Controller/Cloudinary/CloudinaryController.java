package IntegracionBackFront.backfront.Controller.Cloudinary;

import IntegracionBackFront.backfront.Services.Cloudinary.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/image")
public class CloudinaryController {

    @Autowired
    private final CloudinaryService service;

    public CloudinaryController(CloudinaryService service) {
        this.service = service;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("image")MultipartFile file) {
        try{
            //Llamando al servicio para subir la imagen y obtener la URL
            String imageUrl = service.uploadImage(file);
            return ResponseEntity.ok(Map.of(
                    "message", "Imagen subida exitosamente",
                    "url", imageUrl
            ));
        }catch (IOException e){
            return ResponseEntity.internalServerError().body("Error al subir la imagen");
        }
    }
}
