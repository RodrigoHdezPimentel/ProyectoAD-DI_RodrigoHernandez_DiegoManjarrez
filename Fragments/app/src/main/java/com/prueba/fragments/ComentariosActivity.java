package com.prueba.fragments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.prueba.fragments.RetrofitConnection.Interfaces.PublicacionInterface;
import com.prueba.fragments.RetrofitConnection.Models.Publicacion;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComentariosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentarios);
        Intent getId = getIntent();
        int id = getId.getIntExtra("id", 0);

        PublicacionInterface publicacionInterface = MainActivity.retrofitPublicacion.create(PublicacionInterface.class);
        Call<Publicacion> call = publicacionInterface.getPublicationById(id);
        call.enqueue(new Callback<Publicacion>() {

            @Override
            public void onResponse(Call<Publicacion> call, Response<Publicacion> response) {
                if (!response.isSuccessful()) {
                    Log.e("Response err: ", response.message());
                    return;
                }
                Publicacion newPublication = response.body();
                TextView titulo = findViewById(R.id.titulo);
                titulo.setText(newPublication.getTitulo());
                TextView contenido = findViewById(R.id.contenido);
                contenido.setText(newPublication.getContenido());
                return;
            }

            @Override
            public void onFailure(Call<Publicacion> call, Throwable t) {

            }
        });
    }
}