package com.prueba.fragments.RetrofitConnection.Interfaces;

import com.prueba.fragments.RetrofitConnection.Models.Chat;
import com.prueba.fragments.RetrofitConnection.Models.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ChatInterface {
    @GET("all")
    Call<List<Chat>> getAll();
    @GET("getUserChats/{id}")
    Call<List<Chat>> getUserChats(@Path("id") Integer id);

    @GET("getUsersConversation/{id}/{id1}")
    Call<List<Chat>> getUsersConversation(@Path("id") Integer id1, @Path("id1") Integer id2);

}
