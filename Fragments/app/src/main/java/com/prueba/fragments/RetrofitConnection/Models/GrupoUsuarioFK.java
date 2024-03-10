package com.prueba.fragments.RetrofitConnection.Models;

public class GrupoUsuarioFK {
    private Usuario usuario;
    private Grupo grupo;

    public GrupoUsuarioFK() {
    }
    public GrupoUsuarioFK( Usuario usuario, Grupo grupo) {
        this.usuario = usuario;
        this.grupo = grupo;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

}
