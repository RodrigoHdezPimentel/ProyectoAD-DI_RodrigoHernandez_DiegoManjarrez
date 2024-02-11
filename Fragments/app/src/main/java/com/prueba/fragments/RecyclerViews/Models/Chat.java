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
@Table(name = "Chat") //Nombre de la tabla en la BD
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Chat {
    @Column(name = "idOrigen")
    private Integer idOrigen;
    @Column(name = "idDestino")
    private Integer idDestino;

    @Column(name = "Contenido")
    private String Contenido;
    @Column(name = "fecha")
    private String fecha;

    public Chat(Integer idOrigen, Integer idDestino, String contenido, String fecha) {
        this.idOrigen = idOrigen;
        this.idDestino = idDestino;
        Contenido = contenido;
        this.fecha = fecha;
    }

    public Integer getIdOrigen() {
        return idOrigen;
    }

    public Integer getIdDestino() {
        return idDestino;
    }

    public String getContenido() {
        return Contenido;
    }

    public String getFecha() {
        return fecha;
    }
}


