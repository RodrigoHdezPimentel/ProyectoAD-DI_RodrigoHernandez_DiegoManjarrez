package com.prueba.fragments.RetrofitConnection.Models;

public class GrupoUsuarioFK {
    private Integer idusuario;
    private Integer idgrupo;
    private Usuario usuario;
    private Grupo grupo;

    public GrupoUsuarioFK() {
    }

    public GrupoUsuarioFK(Integer idusuario, Integer idgrupo, Usuario usuario, Grupo grupo) {
        this.idusuario = idusuario;
        this.idgrupo = idgrupo;
        this.usuario = usuario;
        this.grupo = grupo;
    }

    public GrupoUsuarioFK(Integer idusuario, Integer idgrupo) {
        this.idusuario = idusuario;
        this.idgrupo = idgrupo;
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

    public Integer getIdusuario() {
        return idusuario;
    }

    public Integer getIdgrupo() {
        return idgrupo;
    }
}
