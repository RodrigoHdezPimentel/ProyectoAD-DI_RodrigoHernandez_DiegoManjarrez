package com.prueba.foroex_app.RetrofitConnection.Interfaces;

import com.prueba.foroex_app.RetrofitConnection.Models.Like;
import com.prueba.foroex_app.RetrofitConnection.Models.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface LikeInterface {
    @GET("all")
    Call<List<Usuario>> getAll();

    @POST("save")
    Call<Like> create(@Body Like like);

    @DELETE("removeLikeUser/{idP}/{idU}")
    Call<Boolean> removeLikeUser(@Path("idP") Integer idP, @Path("idU") Integer idU);

}
