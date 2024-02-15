package com.prueba.fragments.RetrofitConnection.Models;

public class Publicacion {
    private Integer id;
    private Integer idusuario;
    private Integer idtema;
    private Integer idpublirefer;
    private Integer numlikes;
    private String contenido;
    private String titulo;

    public Publicacion() {
    }


    public Publicacion(Integer id, Integer idusuario, Integer idtema, Integer idpublirefer, Integer numlikes, String contenido, String titulo) {
        this.id = id;
        this.idusuario = idusuario;
        this.idtema = idtema;
        this.idpublirefer = idpublirefer;
        this.numlikes = numlikes;
        this.contenido = contenido;
        this.titulo = titulo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Integer idusuario) {
        this.idusuario = idusuario;
    }

    public Integer getIdtema() {
        return idtema;
    }

    public void setIdtema(Integer idtema) {
        this.idtema = idtema;
    }

    public Integer getIdpublirefer() {
        return idpublirefer;
    }

    public void setIdpublirefer(Integer idpublirefer) {
        this.idpublirefer = idpublirefer;
    }

    public Integer getNumlikes() {
        return numlikes;
    }

    public void setNumlikes(Integer numlikes) {
        this.numlikes = numlikes;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        titulo = titulo;
    }
}
