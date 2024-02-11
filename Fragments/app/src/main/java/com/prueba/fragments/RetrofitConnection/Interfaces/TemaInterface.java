package com.prueba.fragments.RetrofitConnection.Interfaces;

import com.prueba.fragments.RetrofitConnection.Models.Tema;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TemaInterface {
    @GET("all")
    Call<List<Tema>> getAll();
}
