package IntegracionBackFront.backfront.Entities.Categories;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Table(name = "CATEGORIA")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_Categoria")
    @SequenceGenerator(sequenceName = "seq_Categoria", name = "seq_Categoria", allocationSize = 1)
    @Column(name = "IDCATEGORIA")
    private Long idCategoria;

    @Column(name = "NOMBRECATEGORIA")
    private String nombreCategoria;

    @Column(name = "DESCRIPCION")
    private String descripcion;

    @Column(name = "FECHACREACION")
    private LocalDate fechaCreacion;
}
