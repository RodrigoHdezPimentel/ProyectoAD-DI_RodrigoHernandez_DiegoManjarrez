package com.prueba.fragments.RetrofitConnection.Interfaces;

import com.prueba.fragments.RetrofitConnection.Models.Grupo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface GrupoInterface {
    @GET("all")
    Call<List<Grupo>> getAll();
    @GET("id/{id}")
    Call<Grupo> getById(@Path("id") Integer id);
    @POST("save")
    Call<Grupo> create(@Body Grupo grupo);
    @DELETE("deleteGroup/{id}")
    Call<Boolean> delete(@Path("id") Integer id);

}
