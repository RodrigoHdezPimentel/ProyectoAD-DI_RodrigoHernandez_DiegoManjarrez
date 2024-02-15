package com.example.pruebaconexion;

public class Usuario {


    private Integer id;
    private Integer year;
    private String name;

    private String genero;
    private String descripcion;
    private String mail;
    private String pass;

    public Usuario( Integer year, String name, String genero, String descripcion, String mail, String pass) {

        this.year = year;
        this.name = name;
        this.genero = genero;
        this.descripcion = descripcion;
        this.mail = mail;
        this.pass = pass;
    }

    public Usuario() {
    }

    public Integer getId() {
        return id;
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
        this.id = id;
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

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
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
}
