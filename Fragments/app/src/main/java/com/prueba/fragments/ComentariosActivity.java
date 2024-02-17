package com.prueba.fragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.prueba.fragments.RecyclerViews.Adapters.ChatRvAdapter;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentarios);
        Intent getId = getIntent();
        id = getId.getIntExtra("id", 0);

        back = findViewById(R.id.arrow);

        cargarPublicacion(id);

    }
    public void cargarPublicacion(Integer idComent){
        PublicacionInterface publicacionInterface = Login_SignUP.retrofitPublicacion.create(PublicacionInterface.class);
        Call<Publicacion> call = publicacionInterface.getPublicationById(idComent);
        call.enqueue(new Callback<Publicacion>() {

            @Override
            public void onResponse(Call<Publicacion> call, Response<Publicacion> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                newPublication = response.body();
                TextView titulo = findViewById(R.id.titulo);
                titulo.setText(newPublication.getTitulo());
                contenidoTv = findViewById(R.id.contenido);
                contenidoTv.setMovementMethod(new ScrollingMovementMethod());
                contenidoTv.setText(newPublication.getContenido());

                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent back = null;
                        if(newPublication.getIdpublirefer() == null){
                            back = new Intent(ComentariosActivity.this, MainActivity.class);
                        }else{
                            back = new Intent(ComentariosActivity.this, ComentariosActivity.class);
                            back.putExtra("id", newPublication.getIdpublirefer());
                        }
                        startActivity(back);
                    }
                });

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