package com.prueba.foroex_app.RetrofitConnection.Interfaces;

import com.prueba.foroex_app.RetrofitConnection.Models.Publicacion;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PublicacionInterface {
    @GET("all")
    Call<List<Publicacion>> getAll();
    @POST("save")
    Call<Publicacion> save(@Body Publicacion publicacion);

    @GET("getAllPublication")
    Call<List<Publicacion>> getAllPublications();
    @GET("getPublishTrending")
    Call<List<Publicacion>> getPublishTrending();

    @GET("allComentariosFromPublicacion/{id}")
    Call<List<Publicacion>> getAllComentsFromPublish(@Path("id") Integer id);

    @GET("id/{id}")
    Call<Publicacion> getPublicationById(@Path("id") Integer id);

    @GET("getFiltroPublication/{titulo}/{contenido}/{usuario}/{tema}")
    Call <List<Publicacion>> getFiltroPublication(@Path("titulo") String titulo, @Path("contenido") String contenido, @Path("usuario") String usuario, @Path("tema") String tema);


}
