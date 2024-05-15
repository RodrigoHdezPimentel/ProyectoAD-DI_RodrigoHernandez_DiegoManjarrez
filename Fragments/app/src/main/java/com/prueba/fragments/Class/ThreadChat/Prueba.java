package com.prueba.fragments.Class.ThreadChat;

import java.io.Serializable;

public class Prueba implements Serializable {

    private String nombre;
    private String id;


    public  Prueba (){

    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
