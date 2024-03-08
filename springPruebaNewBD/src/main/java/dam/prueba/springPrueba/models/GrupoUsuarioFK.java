package dam.prueba.springPrueba.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class GrupoUsuarioFK {

    @JoinColumn(name = "idusuario")
    private Integer idusuario;

    @JoinColumn(name = "idgrupo")
    private Integer idgrupo;

}
