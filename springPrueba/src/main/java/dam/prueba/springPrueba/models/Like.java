package dam.prueba.springPrueba.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    private Integer idLike;
    @Column(name = "idpublicacion")
    private Integer idPublicacion;
    @Column(name = "idusuario")
    private Integer idUsuario;
}
