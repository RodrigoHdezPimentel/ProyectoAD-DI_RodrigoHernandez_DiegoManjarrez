package dam.prueba.springPrueba.Class;

import dam.prueba.springPrueba.models.Conversacion;
import dam.prueba.springPrueba.models.GrupoUsuario;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChatLastMessage {
    private GrupoUsuario chat;
    private Conversacion mensaje;

    public ChatLastMessage(GrupoUsuario chat, Conversacion mensaje) {
        this.chat = chat;
        this.mensaje = mensaje;
    }

}
