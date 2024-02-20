package com.prueba.fragments.RetrofitConnection.Models;

public class Tema {
    private Integer idtema;
    private String titulo;
    private Integer edadMinima;

    public Tema() {
    }
    public Tema(Integer id, String titulo){
        this.idtema = id;
        this.titulo = titulo;
    }
    public Tema(Integer id, String titulo, Integer edadMinima) {
        this.idtema = id;
        this.titulo = titulo;
        this.edadMinima = edadMinima;
    }

    public Integer getId() {
        return idtema;
    }

    public void setId(Integer id) {
        this.idtema = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getEdadMinima() {
        return edadMinima;
    }

    public void setEdadMinima(Integer edadMinima) {
        this.edadMinima = edadMinima;
    }
}
