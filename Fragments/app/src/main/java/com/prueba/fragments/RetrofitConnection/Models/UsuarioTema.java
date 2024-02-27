package com.prueba.fragments.RetrofitConnection.Models;

public class UsuarioTema {

    private UsuarioTemaFK id;

    public UsuarioTema(UsuarioTemaFK id) {
        this.id = id;
    }

    public UsuarioTema() {
    }

    public UsuarioTemaFK getId() {
        return id;
    }

    public void setId(UsuarioTemaFK id) {
        this.id = id;
    }


}
