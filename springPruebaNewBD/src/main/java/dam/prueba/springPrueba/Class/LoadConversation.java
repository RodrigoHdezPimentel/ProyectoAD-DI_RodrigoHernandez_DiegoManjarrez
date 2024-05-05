package dam.prueba.springPrueba.Class;

import dam.prueba.springPrueba.models.Conversacion;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class LoadConversation implements Serializable {

    private Conversacion conversacion;
    private Integer idUsuario;
    private String nombreUsuario;

    public LoadConversation(Conversacion conversacion, Integer idUsuario, String nombreUsuario) {
        this.conversacion = conversacion;
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
    }
}
