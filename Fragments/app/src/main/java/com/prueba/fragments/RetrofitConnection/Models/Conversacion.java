package com.prueba.fragments.RetrofitConnection.Models;

import java.io.Serializable;
import java.util.ArrayList;

public class Conversacion {
    private Integer idconversacion;
    private Integer idgrupousuario;
    private String fecha;
    private String contenido;
    private String idleido;
    public Conversacion() {

    }

    public Conversacion(Integer idConversacion, Integer idgrupoUsuario, String fecha, String contenido, String idleido) {
        this.idconversacion = idConversacion;
        this.idgrupousuario = idgrupoUsuario;
        this.fecha = fecha;
        this.contenido = contenido;
        this.idleido = idleido;
    }
    public Conversacion(Integer idconversacion) {
        this.idconversacion = idconversacion;

    }

    public Integer getIdConversacion() {
        return idconversacion;
    }

    public Integer getIdGrupoUsuario() {
        return idgrupousuario;
    }


    public String getContenido() {
        return contenido;
    }

    public Integer getIdgrupousuario() {
        return idgrupousuario;
    }

    public String getFecha() {
        return fecha;
    }

    public String getIdleido() {
        return idleido;
    }
}
