package com.prueba.fragments.RetrofitConnection.Models;

public class Grupo {
    private Integer idgrupo;
    private String foto;
    private String codigo;

    public Grupo() {
    }

    public Grupo(Integer idGrupo, String foto, String codigo) {
        this.idgrupo = idGrupo;
        this.foto = foto;
        this.codigo = codigo;
    }

    public Integer getIdGrupo() {
        return idgrupo;
    }


    public String getFoto() {
        return foto;
    }

    public String getCodigo() {
        return codigo;
    }
}
