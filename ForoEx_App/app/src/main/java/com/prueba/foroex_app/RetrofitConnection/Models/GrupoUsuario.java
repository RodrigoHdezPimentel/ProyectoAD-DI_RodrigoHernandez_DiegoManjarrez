package com.prueba.foroex_app.RetrofitConnection.Models;

public class GrupoUsuario {
    private Integer idgrupousuario;
    private String nombre;
    private String fechabaja;
    private GrupoUsuarioFK id;

    public GrupoUsuario() {
    }

    public GrupoUsuario(Integer idgrupousuario,String nombre,String fechabaja, GrupoUsuarioFK id) {
        this.idgrupousuario = idgrupousuario;
        this.nombre = nombre;
        this.fechabaja = fechabaja;
        this.id = id;
    }

    public Integer getIdGrupoUsuario() {
        return idgrupousuario;
    }

    public String getNombre() {
        return nombre;
    }

    public GrupoUsuarioFK getGrupoUsuarioFK() {
        return id;
    }

    public String getFechabaja() {
        return fechabaja;
    }
}
