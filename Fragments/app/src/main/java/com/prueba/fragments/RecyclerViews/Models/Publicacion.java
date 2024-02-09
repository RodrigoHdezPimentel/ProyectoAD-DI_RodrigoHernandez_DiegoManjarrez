package com.prueba.fragments.RecyclerViews.Models;


import org.springframework.data.annotation.Id;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "Publicaciones") //Nombre de la tabla en la BD
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Publicacion {
    @Id
    @Column(name = "idpublicacion")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "idusuario")
    private Integer idusuario;
    @Column(name = "idtema")
    private Integer idtema;

    @Column(name = "idpublirefer")
    private Integer idpublirefer;
    @Column(name = "numlikes")
    private Integer numlikes;
    @Column(name = "contenido")
    private String contenido;

    public Publicacion(Integer idusuario, Integer idtema, Integer idpublirefer, Integer numlikes, String contenido) {
        this.idusuario = idusuario;
        this.idtema = idtema;
        this.idpublirefer = idpublirefer;
        this.numlikes = numlikes;
        this.contenido = contenido;
    }

    public Integer getId() {
        return id;
    }

    public Integer getIdusuario() {
        return idusuario;
    }

    public Integer getIdtema() {
        return idtema;
    }

    public Integer getIdpublirefer() {
        return idpublirefer;
    }

    public Integer getNumlikes() {
        return numlikes;
    }

    public String getContenido() {
        return contenido;
    }
}


