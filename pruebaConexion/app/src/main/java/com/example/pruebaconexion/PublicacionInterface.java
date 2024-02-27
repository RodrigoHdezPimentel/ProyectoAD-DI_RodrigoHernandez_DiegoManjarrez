package com.example.pruebaconexion;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PublicacionInterface {
    @GET("all")
    Call<List<Publicacion>> getAll();
}
