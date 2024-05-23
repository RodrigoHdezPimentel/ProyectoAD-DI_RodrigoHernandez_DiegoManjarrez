package com.prueba.foroex_app.RetrofitConnection.Models;

import java.io.Serializable;

public class Like implements Serializable {
    private Integer idLike;
    private Integer idPublicacion;
    private Integer idUsuario;

    public Like() {
    }

    public Like(Integer idLike, Integer idPublicacion, Integer idUsuario) {
        this.idLike = idLike;
        this.idPublicacion = idPublicacion;
        this.idUsuario = idUsuario;
    }

    public Integer getIdLike() {
        return idLike;
    }

    public void setIdLike(Integer idLike) {
        this.idLike = idLike;
    }

    public Integer getIdPublicacion() {
        return idPublicacion;
    }

    public void setIdPublicacion(Integer idPublicacion) {
        this.idPublicacion = idPublicacion;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }
}
