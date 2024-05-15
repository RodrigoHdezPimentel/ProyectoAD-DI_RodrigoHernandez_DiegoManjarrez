package dam.prueba.springPrueba.Class;

import dam.prueba.springPrueba.models.Conversacion;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
public class Message {

    private Conversacion conversacion;
    private Integer idUsuario;
    private String nombreUsuario;

    public Message(Conversacion conversacion, Integer idUsuario, String nombreUsuario) {
        this.conversacion = conversacion;
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
    }

}
