package com.prueba.fragments.RetrofitConnection.Interfaces;

import com.prueba.fragments.RetrofitConnection.Models.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UsuarioInterface {
    @GET("all")
    Call<List<Usuario>> getAll();
}
