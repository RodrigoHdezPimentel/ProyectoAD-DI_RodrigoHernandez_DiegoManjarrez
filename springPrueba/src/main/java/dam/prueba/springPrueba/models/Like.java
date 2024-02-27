package dam.prueba.springPrueba.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Likes") //Nombre de la tabla en la BD
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Like {
    @Id
    @Column(name = "idlike")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idLike;
    @Column(name = "idpublicacion")
    private Integer idPublicacion;
    @Column(name = "idusuario")
    private Integer idUsuario;
}
