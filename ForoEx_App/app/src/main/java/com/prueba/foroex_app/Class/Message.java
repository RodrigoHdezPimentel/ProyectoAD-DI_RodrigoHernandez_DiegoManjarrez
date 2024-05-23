package com.prueba.foroex_app.Class;

import com.prueba.foroex_app.RetrofitConnection.Models.Conversacion;

public class Message {

    private Conversacion conversacion;
    private Integer idUsuario;
    private String nombreUsuario;

    public Message(Conversacion conversacion, Integer idUsuario, String nombreUsuario) {
        this.conversacion = conversacion;
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
    }
    public Conversacion getConversacion() {
        return conversacion;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }
}

