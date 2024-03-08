package dam.prueba.springPrueba.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class GrupoUsuarioFK {
    @Column(name = "idgrupousuario")
    private Integer idgrupousuario;

    @Column(name = "idusuario")
    private Integer idUsuario;

    @Column(name = "idgrupo")
    private  Integer idgrupo;


    /*@OneToOne(targetEntity = GrupoUsuarioFK.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "idusuario")
    @PrimaryKeyJoinColumn
    private Usuario usuario;


    @OneToOne(targetEntity = GrupoUsuarioFK.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "idgrupo")
    @PrimaryKeyJoinColumn
    private  Grupo grupo;*/


}
