package com.prueba.fragments.RetrofitConnection.Interfaces;

import com.prueba.fragments.Class.LoadConversation;
import com.prueba.fragments.RetrofitConnection.Models.Conversacion;

import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ConversacionInterface {
    @GET("all")
    Call<List<Conversacion>> getAll();

    @GET("getConversacionesByGroupId/{id}")
    Call <ArrayList<LoadConversation>> getConversacionesByGroupId(@Path("id") Integer id);
    @POST("save")
    Call<Conversacion> save(@Body Conversacion conversacion);

    //PARA SACAR EL ULTIMO MENSAJE DEL GRUPO
    @GET("getLastMessage/{id}")
    Call <LoadConversation> getLastMessage(@Path("id") Integer id);

    @GET("readMessages/{idUsuario}/{idGU}")
    Call<Void> readMessages(@Path("idUsuario") Integer idUsuario, @Path("idGU") Integer idGU );

    @GET("updateContent/{idConv}/{contenido}")
    Call<Void> updateContent(@Path("idConv") Integer idConv, @Path("contenido") String contenido);

    @GET("deleteConversation/{idConv}")
    Call<Void> deleteConversation(@Path("idConv") Integer idConv);
}
