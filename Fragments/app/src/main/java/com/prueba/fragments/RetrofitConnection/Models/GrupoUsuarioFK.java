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

    public Integer getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Integer idusuario) {
        this.idusuario = idusuario;
    }

    public Integer getIdgrupo() {
        return idgrupo;
    }

    public void setIdgrupo(Integer idgrupo) {
        this.idgrupo = idgrupo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }
}
