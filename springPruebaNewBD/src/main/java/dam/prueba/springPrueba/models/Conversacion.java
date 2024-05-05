package dam.prueba.springPrueba.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Conversaciones") //Nombre de la tabla en la BD
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Conversacion implements Serializable {
    @Id
    @Column(name = "idconversacion")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idconversacion;

    @Column(name = "idgrupousuario")
    private Integer idgrupousuario;

    /*@ManyToOne(targetEntity = GrupoUsuario.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "idgrupousuario")
    private GrupoUsuario grupousuario;*/

    @Column(name = "fecha")
    private String fecha;

    @Column(name = "contenido")
    private String contenido;

    @Column(name = "idleido")
    private String idleido;

}

