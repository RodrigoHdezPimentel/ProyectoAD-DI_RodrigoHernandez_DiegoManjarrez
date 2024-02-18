package com.prueba.fragments.RetrofitConnection.Interfaces;

import com.prueba.fragments.RetrofitConnection.Models.Publicacion;
import com.prueba.fragments.RetrofitConnection.Models.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UsuarioInterface {
    @GET("all")
    Call<List<Usuario>> getAll();


    @GET("id/{id}")
    Call<Usuario> getUserById(@Path("id") Integer id);

    @GET("getUserPublications/{id}")
    Call<List<Publicacion>> getPublicationsFromUser(@Path("id") Integer id);
}
