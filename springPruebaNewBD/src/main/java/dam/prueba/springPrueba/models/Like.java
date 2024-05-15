package dam.prueba.springPrueba.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "Likes") //Nombre de la tabla en la BD
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Like implements Serializable {
    @Id
    @Column(name = "idlike")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idLike;
    @Column(name = "idpublicacion")
    private Integer idPublicacion;
    @Column(name = "idusuario")
    private Integer idUsuario;
}
