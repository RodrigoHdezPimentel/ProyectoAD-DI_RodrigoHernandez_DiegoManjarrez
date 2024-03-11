package com.prueba.fragments.RetrofitConnection.Interfaces;

import com.prueba.fragments.RetrofitConnection.Models.Conversacion;
import com.prueba.fragments.RetrofitConnection.Models.Grupo;
import com.prueba.fragments.RetrofitConnection.Models.GrupoUsuario;
import com.prueba.fragments.RetrofitConnection.Models.Usuario;

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
    @GET("getUserGroups/{id}")
    Call<List<GrupoUsuario>> getUserGroups(@Path("id") Integer id);
    @GET("getGroupUsers/{id}")
    Call<List<Usuario>> getGroupUsers(@Path("id") Integer id);
    @POST("save")
    Call<GrupoUsuario> create(@Body GrupoUsuario grupoUsuario);
    @GET("getCommonGroups/{idU}/{idV}")
    Call<List<List<Integer>>> getCommonGroups(@Path("idU") Integer idU,@Path("idV") Integer idV);
    @GET("getNumberUsers/{id}")
    Call<Boolean> getNumberUsers(@Path("id") Integer id);

}
