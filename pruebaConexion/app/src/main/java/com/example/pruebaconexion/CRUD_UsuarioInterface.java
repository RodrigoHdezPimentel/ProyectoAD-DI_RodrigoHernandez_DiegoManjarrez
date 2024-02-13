package com.example.pruebaconexion;

import java.util.List;
import java.util.Optional;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CRUD_UsuarioInterface {
    @GET("all")
    Call<List<Usuario>> getAll();

    @GET("id/{id}")
    Call<Usuario> getUserOne(@Path("id") Integer id);
}