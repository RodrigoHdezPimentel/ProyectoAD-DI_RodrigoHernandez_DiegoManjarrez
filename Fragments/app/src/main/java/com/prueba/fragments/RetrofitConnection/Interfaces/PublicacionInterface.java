package com.prueba.fragments.RetrofitConnection.Interfaces;

import com.prueba.fragments.RetrofitConnection.Models.Publicacion;
import com.prueba.fragments.RetrofitConnection.Models.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PublicacionInterface {
    @GET("all")
    Call<List<Publicacion>> getAll();

    @GET("getAllPublication")
    Call<List<Publicacion>> getAllPublications();

    @GET("getUserPublication/{id}")
    Call<List<Publicacion>> getPublicationsFromUser(@Path("id") Integer id);

    @GET("allComentariosFromPublicacion/{id}")
    Call<List<Publicacion>> getAllComentsFromPublish(@Path("id") Integer id);

    @GET("id/{id}")
    Call<Publicacion> getPublicationById(@Path("id") Integer id);
}
