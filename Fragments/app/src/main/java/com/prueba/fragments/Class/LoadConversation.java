package com.prueba.fragments.Class;

import com.prueba.fragments.RetrofitConnection.Models.Conversacion;
import com.prueba.fragments.RetrofitConnection.Models.GrupoUsuario;

public class LoadConversation {

    private Conversacion conversacion;
    private Integer idUsuario;
    private String nombreUsuario;

    public LoadConversation(Conversacion conversacion, Integer idUsuario, String nombreUsuario) {
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
