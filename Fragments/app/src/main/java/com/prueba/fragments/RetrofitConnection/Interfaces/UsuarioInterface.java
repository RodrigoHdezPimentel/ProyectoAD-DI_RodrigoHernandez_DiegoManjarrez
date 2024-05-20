package com.prueba.fragments.RetrofitConnection.Interfaces;

import com.prueba.fragments.RetrofitConnection.Models.Publicacion;
import com.prueba.fragments.RetrofitConnection.Models.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UsuarioInterface {
    @GET("all")
    Call<List<Usuario>> getAll();

    @GET("id/{id}")
    Call<Usuario> getUserById(@Path("id") Integer id);

    @GET("getUserPublication/{id}")
    Call<List<Publicacion>> getPublicationsFromUser(@Path("id") Integer id);

    //HAcer más comprobaciones por si las dudas de este resultado
    @GET("getUserPublicacionFromTema/{id}")
    Call<List<Publicacion>> getPublicationsFromUserTema(@Path("id") Integer id);

    @GET("getUserRegister/{name}/{pass}")
    Call<Usuario> getUserRegister(@Path("name") String name, @Path("pass") String pass);

    @GET("getUserPublicacionFromLike/{id}")
    Call<List<Publicacion>> getPublicationsFromUserLike(@Path("id") Integer id);

    @POST("save")
    Call<Usuario> create(@Body Usuario user);

    @PUT("update")
    Call<Usuario> update(@Body Usuario user);

    @GET("userLikedPublish/{idU}/{idP}")
    Call <Boolean> userLikedPublish(@Path("idU") Integer idU, @Path("idP") Integer idP);
    @DELETE("deleteById/{id}/{fecha}")
    Call<Void> delete(@Path("id") Integer id, @Path("fecha") String fecha);

}
