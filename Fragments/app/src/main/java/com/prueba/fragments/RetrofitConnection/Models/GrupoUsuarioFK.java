package com.prueba.fragments.RetrofitConnection.Models;

public class GrupoUsuarioFK {
    private Grupo grupo;
    private Usuario usuario;

    public GrupoUsuarioFK() {
    }
    public GrupoUsuarioFK(Grupo grupo, Usuario usuario) {
        this.grupo = grupo;
        this.usuario = usuario;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

}
