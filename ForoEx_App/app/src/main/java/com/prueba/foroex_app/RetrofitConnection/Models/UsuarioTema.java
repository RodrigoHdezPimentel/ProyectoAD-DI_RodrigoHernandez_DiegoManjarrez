package com.prueba.foroex_app.RetrofitConnection.Models;

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
