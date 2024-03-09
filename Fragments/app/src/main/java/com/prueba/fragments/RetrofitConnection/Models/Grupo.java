package com.prueba.fragments.RetrofitConnection.Models;

public class Grupo {
    private Integer idgrupo;
    private String nombre;
    private String foto;
    private String codigo;

    public Grupo() {
    }

    public Grupo(Integer idGrupo, String nombre, String foto, String codigo) {
        this.idgrupo = idGrupo;
        this.nombre = nombre;
        this.foto = foto;
        this.codigo = codigo;
    }

    public Integer getIdGrupo() {
        return idgrupo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getFoto() {
        return foto;
    }

    public String getCodigo() {
        return codigo;
    }
}
