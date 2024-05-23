package com.prueba.foroex_app.RetrofitConnection.Interfaces;

import com.prueba.foroex_app.RetrofitConnection.Models.Grupo;

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
    @GET("findGroup/{codigo}")
    Call<Grupo> findGroup(@Path("codigo") String codigo);
    @POST("save")
    Call<Grupo> create(@Body Grupo grupo);
    @DELETE("deleteGroup/{id}")
    Call<Boolean> delete(@Path("id") Integer id);

}
