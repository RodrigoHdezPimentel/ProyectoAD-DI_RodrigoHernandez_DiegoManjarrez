package com.prueba.foroex_app.RetrofitConnection.Interfaces;

import com.prueba.foroex_app.Class.ChatListUser;
import com.prueba.foroex_app.RetrofitConnection.Models.Grupo;
import com.prueba.foroex_app.RetrofitConnection.Models.GrupoUsuario;
import com.prueba.foroex_app.RetrofitConnection.Models.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface GrupoUsuarioInterface {
    @GET("all")
    Call<List<Grupo>> getAll();
    @GET("getById/{id}")
    Call<GrupoUsuario> getById(@Path("id") Integer id);
    @GET("getListChatFromUser/{id}")
    Call<List<ChatListUser>> getListChatFromUser(@Path("id") Integer id);
    @GET("getGroupUsers/{id}")
    Call<List<Usuario>> getGroupUsers(@Path("id") Integer id);
    @POST("save")
    Call<GrupoUsuario> create(@Body GrupoUsuario grupoUsuario);
    @GET("getLoadChat/{idU}/{idV}")
    Call<List<List<Integer>>> getLoadChat(@Path("idU") Integer idU,@Path("idV") Integer idV);
    @GET("updateGroupName/{newName}/{id}")
    Call<Void> updateGroupName(@Path("newName") String newName, @Path("id") Integer id);

    @GET("rejoinChat/{id}")
    Call<GrupoUsuario> rejoinGroup(@Path("id") Integer id);

    @GET("findByIdUserIdGroup/{idUsuario}/{idGrupo}")
    Call<GrupoUsuario> findByIdUserIdGroup(@Path("idUsuario") Integer idUsuario, @Path("idGrupo") Integer idGrupo);

    @GET("salirGrupo/{idGrupoUsuario}/{fecha}")
    Call<Void> salitGrupo(@Path("idGrupoUsuario") Integer idGrupoUsuario, @Path("fecha") String fecha);

    @GET("numMessageNews/{idG}/{idU}")
    Call<Integer> numMessageNews(@Path("idG") Integer idG, @Path("idU") Integer idU);

}
