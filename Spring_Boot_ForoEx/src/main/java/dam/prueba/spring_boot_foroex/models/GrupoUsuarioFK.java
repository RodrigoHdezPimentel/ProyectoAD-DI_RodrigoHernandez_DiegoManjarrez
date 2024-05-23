package dam.prueba.spring_boot_foroex.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class GrupoUsuarioFK implements Serializable {
    @Column(name = "idusuario")
    private Integer idusuario;

    @Column(name = "idgrupo")
    private Integer idgrupo;

    @ManyToOne
    @JoinColumn(name = "idusuario", insertable = false, updatable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "idgrupo", insertable = false, updatable = false)
    private Grupo grupo;
}
