package com.prueba.fragments.RetrofitConnection.Models;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.prueba.fragments.ChatActivity;
import com.prueba.fragments.Login_SignUP;
import com.prueba.fragments.RetrofitConnection.Interfaces.GrupoUsuarioInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GrupoUsuario {
    private Integer idgrupousuario;
    private String nombre;
    private GrupoUsuarioFK id;

    public GrupoUsuario() {
    }

    public GrupoUsuario(Integer idgrupousuario,String nombre, GrupoUsuarioFK id) {
        this.idgrupousuario = idgrupousuario;
        this.nombre = nombre;
        this.id = id;
    }

    public Integer getIdGrupoUsuario() {
        return idgrupousuario;
    }

    public String getNombre() {
        return nombre;
    }

    public GrupoUsuarioFK getGrupoUsuarioFK() {
        return id;
    }

}
