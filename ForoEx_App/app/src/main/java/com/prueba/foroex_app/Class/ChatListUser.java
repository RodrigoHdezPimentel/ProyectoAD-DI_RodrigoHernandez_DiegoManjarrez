package com.prueba.foroex_app.Class;

import com.prueba.foroex_app.RetrofitConnection.Models.Conversacion;
import com.prueba.foroex_app.RetrofitConnection.Models.GrupoUsuario;

public class ChatListUser {
    private GrupoUsuario chat;
    private Conversacion mensaje;
    private int numNewMessage;

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

    public int getNumNewMessage() {
        return numNewMessage;
    }
}
