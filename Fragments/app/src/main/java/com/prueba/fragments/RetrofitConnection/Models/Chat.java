package com.prueba.fragments.RetrofitConnection.Models;

public class Chat {

    private Integer idchat;
    private Integer idDestino;
    private Integer idOrigen;
    private String fecha;
    private String contenido ;

    public Chat() {
    }

    public Chat(Integer id, Integer idDestino, Integer idOrigen, String contenido, String fecha) {
        this.idchat = id;
        this.idDestino = idDestino;
        this.idOrigen = idOrigen;
        this.fecha = fecha;
        this.contenido = contenido;
    }

    public Integer getId() {
        return idchat;
    }

    public void setId(Integer id) {
        this.idchat = id;
    }

    public Integer getIdDestino() {
        return idDestino;
    }

    public void setIdDestino(Integer idDestino) {
        this.idDestino = idDestino;
    }

    public Integer getIdOrigen() {
        return idOrigen;
    }

    public void setIdOrigen(Integer idOrigen) {
        this.idOrigen = idOrigen;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
}
