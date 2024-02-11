package com.example.pruebaconexion;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CRUD_UsuarioInterface {
    @GET("all")
    Call<List<Usuario>> getAll();
}
