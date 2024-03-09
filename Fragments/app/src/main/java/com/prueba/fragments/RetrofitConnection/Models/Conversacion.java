package com.prueba.fragments.RetrofitConnection.Models;

public class Conversacion {
    private Integer idconversacion;
    private GrupoUsuario grupousuario;
    private String fecha;
    private String contenido;

    public Conversacion() {
    }

    public Conversacion(Integer idConversacion, GrupoUsuario grupoUsuario, String fecha, String contenido) {
        this.idconversacion = idConversacion;
        this.grupousuario = grupoUsuario;
        this.fecha = fecha;
        this.contenido = contenido;
    }
    public Conversacion(GrupoUsuario grupoUsuario, String fecha, String contenido) {
        this.grupousuario = grupoUsuario;
        this.fecha = fecha;
        this.contenido = contenido;
    }

    public Integer getIdConversacion() {
        return idconversacion;
    }

    public GrupoUsuario getGrupoUsuario() {
        return grupousuario;
    }

    public String getFecha() {
        return fecha;
    }

    public String getContenido() {
        return contenido;
    }
}
