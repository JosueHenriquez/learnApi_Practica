package IntegracionBackFront.backfront.Controller.Categories;

import IntegracionBackFront.backfront.Models.DTO.Categories.CategoryDTO;
import IntegracionBackFront.backfront.Services.Categories.CategoryService;
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
@RequestMapping("Api/category")
public class CategoriesController {

    //Inyectar la clase service
    @Autowired
    private CategoryService service;

    @GetMapping("/getDataCategories")
    private ResponseEntity<Page<CategoryDTO>> getData(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        //Parte 1. Se evalua cuantos registros desea por pagina el usuario
        //teniendo como máximo 50 registros por página
        if (size <= 0 || size > 50) {
            ResponseEntity.badRequest().body(Map.of(
                    "Status", "El tamaño de la pagina debe estar entre 1 y 50"
            ));
            return ResponseEntity.ok().body(null);
        }
        //Parte 2. Invocando al metodo getAllCategories contenido en el service y guardamos en el
        //objeto category
        //Si no hay datos category = null de lo contrario no sera nulo;
        Page<CategoryDTO> category = service.getAllCategories(page, size);
        if(category == null){
            ResponseEntity.badRequest().body(Map.of(
                    "Status", "No hay categorias registradas"
            ));
        }
        return ResponseEntity.ok(category);
    }
}
