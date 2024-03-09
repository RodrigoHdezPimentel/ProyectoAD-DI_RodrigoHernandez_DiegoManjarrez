package com.prueba.fragments.RetrofitConnection.Interfaces;

import com.prueba.fragments.RetrofitConnection.Models.Conversacion;
import com.prueba.fragments.RetrofitConnection.Models.Like;
import com.prueba.fragments.RetrofitConnection.Models.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ConversacionInterface {
    @GET("all")
    Call<List<Conversacion>> getAll();

    @GET("getConversacionesByGroupId/{id}")
    Call <List<Conversacion>> getConversacionesByGroupId(@Path("id") Integer id);
    @POST("save")
    Call<Conversacion> create(@Body Conversacion conversacion);

}
