package com.prueba.fragments.RetrofitConnection.Interfaces;

import com.prueba.fragments.RetrofitConnection.Models.Publicacion;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PublicacionInterface {
    @GET("all")
    Call<List<Publicacion>> getAll();
}
