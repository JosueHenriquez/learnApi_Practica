package IntegracionBackFront.backfront.Models.DTO.Categories;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString @EqualsAndHashCode
public class CategoryDTO {

    private Long idCategoria;

    @NotBlank
    private String nombreCategoria;

    @NotBlank
    private String descripcion;

    @PastOrPresent(message = "La fecha no puede ser futura")
    private LocalDate fechaCreacion;
}
