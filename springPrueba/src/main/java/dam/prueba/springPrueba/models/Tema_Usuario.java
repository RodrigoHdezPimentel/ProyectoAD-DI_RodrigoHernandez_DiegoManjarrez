package dam.prueba.springPrueba.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Tema_Usuario") //Nombre de la tabla en la BD
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Embeddable
public class Tema_Usuario {
    @Column(name = "idtema")
    private Integer idtema;
    @Column(name = "idusuario")
    private Integer idusuario;

}
