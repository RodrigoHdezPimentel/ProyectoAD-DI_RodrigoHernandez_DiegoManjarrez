package com.prueba.fragments.RetrofitConnection.Models;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.prueba.fragments.ChatActivity;
import com.prueba.fragments.Login_SignUP;
import com.prueba.fragments.RetrofitConnection.Interfaces.ConversacionInterface;
import com.prueba.fragments.RetrofitConnection.Interfaces.GrupoUsuarioInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Conversacion {
    private Integer idconversacion;
    private Integer idgrupousuario;
    private String fecha;
    private String contenido;

    public Conversacion() {
    }

    public Conversacion(Integer idConversacion, Integer idgrupoUsuario, String fecha, String contenido) {
        this.idconversacion = idConversacion;
        this.idgrupousuario = idgrupoUsuario;
        this.fecha = fecha;
        this.contenido = contenido;
    }
    public Conversacion(Integer idgrupoUsuario, String fecha, String contenido) {
        this.idgrupousuario = idgrupoUsuario;
        this.fecha = fecha;
        this.contenido = contenido;
    }

    public Integer getIdConversacion() {
        return idconversacion;
    }

    public Integer getIdGrupoUsuario() {
        return idgrupousuario;
    }

    public String getFecha() {
        return fecha;
    }

    public String getContenido() {
        return contenido;
    }
}
