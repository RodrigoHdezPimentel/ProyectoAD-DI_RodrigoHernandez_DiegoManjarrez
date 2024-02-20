package com.prueba.fragments.RetrofitConnection.Interfaces;

import com.prueba.fragments.RetrofitConnection.Models.UsuarioTema;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UsuarioTemaInterface {

    @POST("save")
    Call<UsuarioTema> create(@Body UsuarioTema userTema);


    @GET("all")
    Call<List<UsuarioTema>> getAll();


}
