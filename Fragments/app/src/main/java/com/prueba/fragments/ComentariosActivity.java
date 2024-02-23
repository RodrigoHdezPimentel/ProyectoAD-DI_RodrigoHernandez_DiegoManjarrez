package com.prueba.fragments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.prueba.fragments.RecyclerViews.Adapters.PublicacionRvAdapter;
import com.prueba.fragments.RetrofitConnection.Interfaces.PublicacionInterface;
import com.prueba.fragments.RetrofitConnection.Models.Publicacion;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComentariosActivity extends AppCompatActivity {
ArrayList<Publicacion> listaComentarios = new ArrayList<>();
ImageView back;
    int id;
    Integer idRef;
    Publicacion newPublication;
    TextView contenidoTv;
    TextView numComentarios;
    TextView numLikes;
    TextView titulo;
    ImageView home;
    TextView userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentarios);
        Intent getId = getIntent();
        id = getId.getIntExtra("id", 0);
        userName = findViewById(R.id.UserName);
        numLikes = findViewById(R.id.numLikes);
        back = findViewById(R.id.arrow);
        numComentarios = findViewById(R.id.numComentarios);
        contenidoTv = findViewById(R.id.contenido);
        titulo = findViewById(R.id.titulo);
        home = findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toHome = new Intent(ComentariosActivity.this, MainActivity.class);
                startActivity(toHome);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back;
                if(newPublication.getIdpublirefer() == null){
                    back = new Intent(ComentariosActivity.this, MainActivity.class);
                }else{
                    back = new Intent(ComentariosActivity.this, ComentariosActivity.class);
                    back.putExtra("id", newPublication.getIdpublirefer());
                }
                startActivity(back);
            }
        });

        cargarPublicacion(id);

    }
    public void cargarPublicacion(Integer idComent){
        PublicacionInterface publicacionInterface = Login_SignUP.retrofitPublicacion.create(PublicacionInterface.class);
        Call<Publicacion> call = publicacionInterface.getPublicationById(idComent);
        call.enqueue(new Callback<Publicacion>() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<Publicacion> call, @NonNull Response<Publicacion> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                newPublication = response.body();
                userName.setText(newPublication.getUsuario().getName());
                titulo.setText(newPublication.getTitulo());
                contenidoTv.setMovementMethod(new ScrollingMovementMethod());
                contenidoTv.setText(newPublication.getContenido());
                numComentarios.setText(newPublication.getComentarios().length+"");
                numLikes.setText(newPublication.getNumlikes()+"");

                getComentarios(newPublication.getId());
            }
            @Override
            public void onFailure(Call<Publicacion> call, Throwable t) {
            }
        });
    }

    public void getComentarios(Integer id){
        PublicacionInterface publicacionInterface = Login_SignUP.retrofitPublicacion.create(PublicacionInterface.class);
        Call<List<Publicacion>> call = publicacionInterface.getAllComentsFromPublish(id);
        call.enqueue(new Callback<List<Publicacion>>() {

            @Override
            public void onResponse(Call<List<Publicacion>> call, Response<List<Publicacion>> response) {
                if (!response.isSuccessful()) {
                    Log.e("Response err: ", response.message());
                    return;
                }
                listaComentarios = (ArrayList<Publicacion>) response.body();

                RecyclerView Contenidos = findViewById(R.id.commentRecycler);
                PublicacionRvAdapter adapter = new PublicacionRvAdapter(ComentariosActivity.this, listaComentarios);
                Contenidos.setAdapter(adapter);
                Contenidos.setLayoutManager(new LinearLayoutManager(ComentariosActivity.this));

                LinearLayoutManager layoutManager = (LinearLayoutManager) Contenidos.getLayoutManager();

                Contenidos.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);

                        // Check if the RecyclerView has reached the top
                        if (layoutManager.findFirstVisibleItemPosition() == 0) {
                            // The RecyclerView has reached the top
                            contenidoTv.setVisibility(View.VISIBLE);
                        }else{
                            contenidoTv.setVisibility(View.GONE);
                        }
                    }
                });
            }
            @Override
            public void onFailure(Call<List<Publicacion>> call, Throwable t) {
                Toast.makeText(ComentariosActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}