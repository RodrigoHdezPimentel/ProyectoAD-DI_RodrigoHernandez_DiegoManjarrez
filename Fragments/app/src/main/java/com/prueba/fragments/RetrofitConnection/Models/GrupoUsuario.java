package com.prueba.fragments.RetrofitConnection.Models;

public class GrupoUsuario {
    private Integer idgrupousuario;
    private GrupoUsuarioFK id;

    public GrupoUsuario() {
    }

    public GrupoUsuario(Integer idGrupoUsuario, GrupoUsuarioFK grupoUsuarioFK) {
        this.idgrupousuario = idGrupoUsuario;
        this.id = grupoUsuarioFK;
    }

    public Integer getIdGrupoUsuario() {
        return idgrupousuario;
    }

    public GrupoUsuarioFK getGrupoUsuarioFK() {
        return id;
    }

}
