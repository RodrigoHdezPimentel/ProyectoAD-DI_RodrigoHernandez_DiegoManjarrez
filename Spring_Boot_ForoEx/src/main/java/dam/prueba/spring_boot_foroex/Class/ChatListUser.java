package dam.prueba.spring_boot_foroex.Class;

import dam.prueba.spring_boot_foroex.models.Conversacion;
import dam.prueba.spring_boot_foroex.models.GrupoUsuario;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChatListUser {
    private GrupoUsuario chat;
    private Conversacion mensaje;
    private int numNewMessage;


    public ChatListUser(GrupoUsuario chat, Conversacion mensaje) {
        this.chat = chat;
        this.mensaje = mensaje;
    }

}
