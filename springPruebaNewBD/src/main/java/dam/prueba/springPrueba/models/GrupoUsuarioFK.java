package dam.prueba.springPrueba.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class GrupoUsuarioFK implements Serializable {
    @Column(name = "idusuario", insertable=false, updatable=false)
    private Integer idusuario;

    @Column(name = "idgrupo", insertable=false, updatable=false)
    private Integer idgrupo;

    @OneToOne(targetEntity = Usuario.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "idusuario")
    private Usuario usuario;

    @OneToOne(targetEntity = Grupo.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "idgrupo")
    private Grupo grupo;

}
