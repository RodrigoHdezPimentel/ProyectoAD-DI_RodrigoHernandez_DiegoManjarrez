package com.prueba.fragments.RetrofitConnection.Models;

public class UsuarioTemaFK {

    private int idUsuario;

    private  int idTema;

    public UsuarioTemaFK(int idUsuario, int idTema) {
        this.idUsuario = idUsuario;
        this.idTema = idTema;
    }

    public UsuarioTemaFK() {
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdTema() {
        return idTema;
    }

    public void setIdTema(int idTema) {
        this.idTema = idTema;
    }
}
