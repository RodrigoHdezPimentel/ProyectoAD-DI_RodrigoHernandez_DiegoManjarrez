package com.prueba.fragments.RetrofitConnection.Models;

public class GrupoUsuario {
    private Integer idgrupousuario;
    private GrupoUsuarioFK id;

    public GrupoUsuario() {
    }

    public GrupoUsuario(Integer idgrupousuario, GrupoUsuarioFK id) {
        this.idgrupousuario = idgrupousuario;
        this.id = id;
    }

    public Integer getIdGrupoUsuario() {
        return idgrupousuario;
    }

    public GrupoUsuarioFK getGrupoUsuarioFK() {
        return id;
    }

}
