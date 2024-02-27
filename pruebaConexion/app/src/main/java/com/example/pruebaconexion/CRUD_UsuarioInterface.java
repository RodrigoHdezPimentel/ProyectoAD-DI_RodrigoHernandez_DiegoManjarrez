package com.example.pruebaconexion;

import java.util.List;
import java.util.Optional;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CRUD_UsuarioInterface {
    @GET("all")
    Call<List<Usuario>> getAll();

    @GET("id/{id}")
    Call<Usuario> getUserOne(@Path("id") Integer id);

    @POST("save")
    Call<Usuario> create(@Body Usuario user);

    @PUT("update")
    Call<Usuario> update(@Body Usuario user);

    @DELETE("deleteById/{id}")
    Call<Boolean> delete(@Path("id") Integer id);

}