package dam.prueba.spring_boot_foroex.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Temas") //Nombre de la tabla en la BD
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Tema {
    @Id
    @Column(name = "idtema")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idtema;
    @Column(name = "Titulo")
    private String titulo;
    @Column(name = "edadminima")
    private Integer edadMinima;

}
