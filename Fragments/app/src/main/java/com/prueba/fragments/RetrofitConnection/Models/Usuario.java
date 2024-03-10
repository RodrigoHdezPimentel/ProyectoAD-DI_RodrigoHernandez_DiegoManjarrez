package com.prueba.fragments.RetrofitConnection.Models;

import java.io.Serializable;

public class Usuario implements Serializable {
    private static Usuario instance;
    private Integer idusuario;
    private Integer year;
    private String name;

    private Boolean genero;
    private String descripcion = "";
    private String mail;
    private String pass;

    public Usuario(Integer idusuario, Integer year, String name, boolean genero, String descripcion, String mail, String pass) {
        this.idusuario = idusuario;
        this.year = year;
        this.name = name;
        this.genero = genero;
        this.descripcion = descripcion;
        this.mail = mail;
        this.pass = pass;
    }

    public Usuario() {
    }
    public static  Usuario getInstance() {
        if (instance == null) {
            instance = new Usuario();
        }
        return instance;
    }
    public Integer getId() {
        return idusuario;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + getId() +
                ", year=" + getYear() +
                ", name='" + getName() + '\'' +
                ", genero='" + getGenero() + '\'' +
                ", descripcion='" + getDescripcion() + '\'' +
                ", mail='" + getMail() + '\'' +
                ", pass='" + getPass() + '\'' +
                '}';
    }

    public void setId(Integer id) {
        this.idusuario = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getGenero() {
        return genero;
    }

    public void setGenero(Boolean genero) {
        this.genero = genero;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
    public static void setInstance(Usuario instance) {
        Usuario.instance = instance;
    }
}
