package IntegracionBackFront.backfront.Controller.Categories;

import IntegracionBackFront.backfront.Models.DTO.Categories.CategoryDTO;
import IntegracionBackFront.backfront.Services.Categories.CategoryService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/category")

public class CategoriesController {
    //Inyectar la clase service

    @Autowired
    private CategoryService service;

    //Creando el endpoint
    @GetMapping ("/getDataCategories")

    private ResponseEntity<Page<CategoryDTO>> getData (
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size
    ){

            if (size <= 0 || size > 50){
                ResponseEntity.badRequest().body(Map.of(
                   "status", "El tamaño de la pagina debe estar entre 1 y 50"

                ));
                return  ResponseEntity.ok(null);
            }
            Page<CategoryDTO> category = service.getAllCategories(page, size);
        if (category == null){
            ResponseEntity.badRequest().body(Map.of(
               "status", "No hay categorias registradas"
            ));
        }
        return  ResponseEntity.ok(category);
    }
}