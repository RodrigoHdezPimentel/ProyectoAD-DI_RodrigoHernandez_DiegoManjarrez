package com.prueba.fragments.RetrofitConnection.Interfaces;

import com.prueba.fragments.RetrofitConnection.Models.Chat;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ChatInterface {
    @GET("all")
    Call<List<Chat>> getAll();
}
