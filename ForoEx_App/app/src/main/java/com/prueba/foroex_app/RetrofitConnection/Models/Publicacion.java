package com.prueba.foroex_app.RetrofitConnection.Models;


import java.util.ArrayList;

public class Publicacion {
    private Integer idpublicacion;
    private Integer idusuario;
    private Integer idtema;
    private Integer idpublirefer;
    private String fecha;
    private Integer numlikes;
    private String contenido;
    private String titulo;
    private Tema tema;
    private Usuario usuario;
    private Publicacion[] comentarios;
    private ArrayList<Like> likes;

    public Publicacion() {
    }


    public Publicacion(Integer id, Integer idusuario, Integer idtema, Integer idpublirefer, Integer numlikes, String contenido, String titulo) {
        this.idpublicacion = id;
        this.idusuario = idusuario;
        this.idtema = idtema;
        this.idpublirefer = idpublirefer;
        this.numlikes = numlikes;
        this.contenido = contenido;
        this.titulo = titulo;
    }
    public Publicacion(Integer idusuario, Integer idtema, Integer idpublirefer,String fecha, Integer numlikes, String contenido, String titulo, Tema tema, Usuario usuario, Publicacion[] comentarios) {
        this.idusuario = idusuario;
        this.idtema = idtema;
        this.idpublirefer = idpublirefer;
        this.fecha = fecha;
        this.numlikes = numlikes;
        this.contenido = contenido;
        this.titulo = titulo;
        this.tema = tema;
        this.usuario = usuario;
        this.comentarios = comentarios;
    }
    public Publicacion(Integer id, Integer idusuario, Integer idtema, Integer idpublirefer,String fecha, Integer numlikes, String contenido, String titulo, Tema tema, Usuario usuario, Publicacion[] comentarios) {
        this.idpublicacion = id;
        this.idusuario = idusuario;
        this.idtema = idtema;
        this.idpublirefer = idpublirefer;
        this.fecha = fecha;
        this.numlikes = numlikes;
        this.contenido = contenido;
        this.titulo = titulo;
        this.tema = tema;
        this.usuario = usuario;
        this.comentarios = comentarios;
    }
    public Publicacion(Integer id, Integer idusuario, Integer idtema, Integer idpublirefer,String fecha, Integer numlikes, String contenido, String titulo, Tema tema, Usuario usuario, Publicacion[] comentarios, ArrayList<Like> likes) {
        this.idpublicacion = id;
        this.idusuario = idusuario;
        this.idtema = idtema;
        this.idpublirefer = idpublirefer;
        this.fecha = fecha;
        this.numlikes = numlikes;
        this.contenido = contenido;
        this.titulo = titulo;
        this.tema = tema;
        this.usuario = usuario;
        this.comentarios = comentarios;
        this.likes = likes;
    }


    public Integer getId() {
        return idpublicacion;
    }

    public void setId(Integer id) {
        this.idpublicacion = id;
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

    public Tema getTema() {
        return tema;
    }

    public void setTema(Tema tema) {
        this.tema = tema;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Publicacion[] getComentarios() {
        return comentarios;
    }

    public void setComentarios(Publicacion[] comentarios) {
        this.comentarios = comentarios;
    }

    public ArrayList<Like> getLikes() {
        return likes;
    }

    public void setLikes(ArrayList<Like> likes) {
        this.likes = likes;
    }
}
