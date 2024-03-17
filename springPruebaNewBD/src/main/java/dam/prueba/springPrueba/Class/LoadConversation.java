package dam.prueba.springPrueba.Class;

import dam.prueba.springPrueba.models.Conversacion;
import dam.prueba.springPrueba.models.GrupoUsuario;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoadConversation {

    private Conversacion conversacion;
    private Integer idUsuario;
    private String nombreUsuario;

    public LoadConversation(Conversacion conversacion, Integer idUsuario, String nombreUsuario) {
        this.conversacion = conversacion;
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
    }
}
