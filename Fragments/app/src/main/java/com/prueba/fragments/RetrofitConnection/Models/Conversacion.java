package com.prueba.fragments.RetrofitConnection.Models;

public class Conversacion {
    private Integer idconversacion;
    private Integer idgrupousuario;
    private String fecha;
    private String contenido;

    public Conversacion() {
    }

    public Conversacion(Integer idConversacion, Integer idgrupoUsuario, String fecha, String contenido) {
        this.idconversacion = idConversacion;
        this.idgrupousuario = idgrupoUsuario;
        this.fecha = fecha;
        this.contenido = contenido;
    }
    public Conversacion(Integer idgrupoUsuario, String fecha, String contenido) {
        this.idgrupousuario = idgrupoUsuario;
        this.fecha = fecha;
        this.contenido = contenido;
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

    public String getFecha() {
        return fecha;
    }

    public String getContenido() {
        return contenido;
    }



}
