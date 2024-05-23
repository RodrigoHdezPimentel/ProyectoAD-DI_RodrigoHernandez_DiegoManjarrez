package dam.prueba.spring_boot_foroex.Class;

import dam.prueba.spring_boot_foroex.models.Conversacion;
import lombok.Getter;


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
