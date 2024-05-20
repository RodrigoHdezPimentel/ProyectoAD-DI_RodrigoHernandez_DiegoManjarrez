package com.prueba.fragments.RetrofitConnection.Interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.File;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface FileInterface {

        @Multipart
        @POST("/saveImageApp")
        Call<String> saveImageApp(@Part MultipartBody.Part image);

        @GET("/prueba")
        Call<String> prueba();
}
