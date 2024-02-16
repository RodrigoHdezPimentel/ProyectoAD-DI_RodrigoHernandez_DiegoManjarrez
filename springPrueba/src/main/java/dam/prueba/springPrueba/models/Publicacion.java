package dam.prueba.springPrueba.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Publicaciones") //Nombre de la tabla en la BD
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Publicacion {
    @Id
    @Column(name = "idpublicacion", insertable=false, updatable=false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "idusuario", insertable=false, updatable=false)
    private Integer idusuario;
    @Column(name = "idtema", insertable=false, updatable=false)
    private Integer idtema;

    @Column(name = "idpublirefer", insertable=false, updatable=false)
    private Integer idpublirefer;
    @Column(name = "numlikes")
    private Integer numlikes;
    @Column(name = "contenido")
    private String contenido;
    @Column(name = "titulo")
    private String titulo;

//    @ManyToOne(targetEntity = Usuario.class, cascade = CascadeType.ALL)
//    @JoinColumn(name = "idusuario")
//    private Usuario usuario;

//    @ManyToOne(targetEntity = Tema.class, cascade = CascadeType.ALL)
//    @JoinColumn(name = "idtema")
//    private Tema tema;
//
//    @OneToMany(targetEntity = Like.class, cascade = CascadeType.ALL)
//    @JoinColumn(name = "idpublicacion")
//    private List<Like> likes;
//
//    @OneToMany(targetEntity = Publicacion.class, cascade = CascadeType.ALL)
//    @JoinColumn(name = "idpublirefer")
//    private List<Publicacion> comentarios;

//    @ManyToOne
//    @JsonBackReference
//    @JoinColumn(name = "idusuario")
//    private Usuario usuario;
}
