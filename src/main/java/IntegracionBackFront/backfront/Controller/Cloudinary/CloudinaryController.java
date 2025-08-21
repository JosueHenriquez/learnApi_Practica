package IntegracionBackFront.backfront.Controller.Cloudinary;

import IntegracionBackFront.backfront.Services.Cloudinary.CloudinaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/image")
public class CloudinaryController {
    private final CloudinaryService service;

    public CloudinaryController(CloudinaryService service) {
        this.service = service;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uuploadImage(@RequestParam("image")MultipartFile file){
        try {
            String imageUrl = service.uploadImage(file);
            return ResponseEntity.ok(Map.of(
                    "mesage", "Imagen subida exitosamente",
                    "url", imageUrl
            ));

        }catch (IOException e){
            return ResponseEntity.internalServerError().body("Error al subir la imagen");
        }
    }

    @PostMapping("/upload-to-folder")
    public ResponseEntity<?> uploadImageToFolder(
            @RequestParam("image") MultipartFile file,
            @RequestParam String folder

    ){
      try{
          String imageUrl = service.uploadImage(file, folder);
          return ResponseEntity.ok(Map.of(
                  "message" , "Imagen subida exitosamente",
                  "url" , imageUrl
          ));
      }catch (IOException e){
          return ResponseEntity.internalServerError().body("Error al subir la imagen");
      }

    }
}
