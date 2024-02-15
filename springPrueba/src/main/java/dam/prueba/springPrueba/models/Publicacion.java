package dam.prueba.springPrueba.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Publicaciones") //Nombre de la tabla en la BD
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Publicacion {
    @Id
    @Column(name = "idpublicacion")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "idusuario")
    private Integer idusuario;
    @Column(name = "idtema")
    private Integer idtema;

    @Column(name = "idpublirefer")
    private Integer idpublirefer;
    @Column(name = "numlikes")
    private Integer numlikes;
    @Column(name = "contenido")
    private String contenido;
    @Column(name="titulo")
    private  String titulo;

}
