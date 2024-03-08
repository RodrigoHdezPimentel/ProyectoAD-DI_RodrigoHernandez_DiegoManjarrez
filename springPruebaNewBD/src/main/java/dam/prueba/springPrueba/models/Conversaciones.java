package dam.prueba.springPrueba.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Conversaciones") //Nombre de la tabla en la BD
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Conversaciones {
    @Id
    @Column(name = "idconversacion")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idconversacion;

    @Column(name = "idgrupousuario")
    private String idgrupousuario;

    /*@OneToOne(targetEntity = GrupoUsuario.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "idgrupousuario")
    @PrimaryKeyJoinColumn
    private GrupoUsuario grupousuario;*/

    @Column(name = "fecha")
    private String fecha;

    @Column(name = "contenido")
    private String contenido;
}
