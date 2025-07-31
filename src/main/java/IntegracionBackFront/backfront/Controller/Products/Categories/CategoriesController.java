package IntegracionBackFront.backfront.Controller.Products.Categories;

import IntegracionBackFront.backfront.Models.DTO.Categories.CategoryDTO;
import IntegracionBackFront.backfront.Services.Categories.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/Category")
public class CategoriesController {

    //Inyectar la clase service
    @Autowired
    private CategoryService service;

    @GetMapping("/getDaraCategories")
    private ResponseEntity<Page<CategoryDTO>> getData(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size
    ){

        //parte 1: se evalua cuantos registros deasea por pagina el usuario
        //teniendo en cuenta maximo 50 registros de paginas
    if (size <= 0 || size > 50){
        ResponseEntity.badRequest().body(Map.of(
                "status", "El tamaño  de la pagina debe ser entre 1 y 50"
        ));
        return ResponseEntity.ok(null);
    }
    //Parte 2:Incvocando al metodo getAllCategories contenido en el service y guardamos los datos en el objeto
        //categorie
        //Si no hay datos categorie sera null(categorie == null) de lo contrario no sera nulo
    Page<CategoryDTO> category = service.getAllCategories(page, size);
    if (category == null){
        ResponseEntity.badRequest().body(Map.of(
                "status", "No hay categorias registradas"
        ));
    }
    return ResponseEntity.ok(category);
    }

}
