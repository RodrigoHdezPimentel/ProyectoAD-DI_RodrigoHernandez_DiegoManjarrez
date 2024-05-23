package com.prueba.foroex_app.RetrofitConnection.Interfaces;

import com.prueba.foroex_app.RetrofitConnection.Models.Tema;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TemaInterface {
    @GET("all")
    Call<List<Tema>> getAll();

}
