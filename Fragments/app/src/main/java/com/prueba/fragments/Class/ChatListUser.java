package com.prueba.fragments.Class;

import com.prueba.fragments.RetrofitConnection.Models.Conversacion;
import com.prueba.fragments.RetrofitConnection.Models.GrupoUsuario;

public class ChatListUser {
    private GrupoUsuario chat;
    private Conversacion mensaje;

    public ChatListUser(GrupoUsuario chat, Conversacion mensaje) {
        this.chat = chat;
        this.mensaje = mensaje;
    }

    public GrupoUsuario getChat() {
        return chat;
    }

    public void setChat(GrupoUsuario chat) {
        this.chat = chat;
    }

    public Conversacion getMensaje() {
        return mensaje;
    }

    public void setMensaje(Conversacion mensaje) {
        this.mensaje = mensaje;
    }
}
