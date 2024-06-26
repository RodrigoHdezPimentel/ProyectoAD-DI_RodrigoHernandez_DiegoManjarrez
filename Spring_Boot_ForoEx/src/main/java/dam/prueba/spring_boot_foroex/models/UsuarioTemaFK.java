package dam.prueba.spring_boot_foroex.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class UsuarioTemaFK implements Serializable {
    @Column(name = "idusuario")
    private Integer idUsuario;

    @Column(name = "idtema")
    private  Integer idTema;

}
