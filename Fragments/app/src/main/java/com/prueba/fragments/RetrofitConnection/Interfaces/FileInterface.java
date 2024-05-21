package com.prueba.fragments.RetrofitConnection.Interfaces;


import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface FileInterface {
        @Multipart
        @POST("saveImageApp")
        Call<String> saveImageApp(@Part MultipartBody.Part image);

        @DELETE("deleteImage/{namePhoto}")
        Call<String> deleteImage (@Path("namePhoto") String str);

}
