package com.prueba.fragments.RetrofitConnection.Models;

public class GrupoUsuario {
    private Integer idgrupousuario;
    private Integer idusuario;
    private Integer idgrupo;
    private Usuario usuario;
    private Grupo grupo;
    public GrupoUsuario() {
    }

    public GrupoUsuario(Integer idgrupousuario, Integer idusuario, Integer idgrupo, Usuario usuario, Grupo grupo) {
        this.idgrupousuario = idgrupousuario;
        this.idusuario = idusuario;
        this.idgrupo = idgrupo;
        this.usuario = usuario;
        this.grupo = grupo;
    }

    public Integer getIdgrupousuario() {
        return idgrupousuario;
    }

    public Integer getIdusuario() {
        return idusuario;
    }

    public Integer getIdgrupo() {
        return idgrupo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Grupo getGrupo() {
        return grupo;
    }
}
