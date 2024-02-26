package com.prueba.fragments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.prueba.fragments.RecyclerViews.Adapters.PublicacionRvAdapter;
import com.prueba.fragments.RetrofitConnection.Interfaces.PublicacionInterface;
import com.prueba.fragments.RetrofitConnection.Models.Like;
import com.prueba.fragments.RetrofitConnection.Models.Publicacion;
import com.prueba.fragments.RetrofitConnection.Models.Usuario;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComentariosActivity extends AppCompatActivity {
ArrayList<Publicacion> listaComentarios = new ArrayList<>();
ImageView back;
    int id;
    ImageView iconUserPublish;
    Publicacion newPublication;
    TextView contenidoTv;
    TextView numComentarios;
    TextView numLikes;
    TextView titulo;
    TextView userName;
    ImageView home;
    ImageView send;
    TextInputEditText CommnetInput;
    FloatingActionButton commentBut;
    LinearLayout commentInputField;
    RecyclerView recyclerView;
    PublicacionInterface publicacionInterface;
    ConstraintLayout constraintLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_comentarios);
        Intent getId = getIntent();
        id = getId.getIntExtra("id", 0);
        userName = findViewById(R.id.UserName);
        numLikes = findViewById(R.id.numLikes);
        numComentarios = findViewById(R.id.numComentarios);
        contenidoTv = findViewById(R.id.contenido);
        titulo = findViewById(R.id.titulo);
        commentInputField = findViewById(R.id.LLcommentBar);
        recyclerView = findViewById(R.id.commentRecycler);
        constraintLayout = findViewById(R.id.constraintLayout);
        CommnetInput = findViewById(R.id.CommnetInput);
        iconUserPublish = findViewById(R.id.iconUserPublish);
        commentBut = findViewById(R.id.commentButt);
        back = findViewById(R.id.arrow);
        home = findViewById(R.id.home);
        send = findViewById(R.id.sendComm);

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
        commentBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commentBut.setVisibility(View.GONE);
                commentInputField.setVisibility(View.VISIBLE);
            }
        });
        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commentBut.setVisibility(View.VISIBLE);
                commentInputField.setVisibility(View.GONE);
            }
        });
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commentBut.setVisibility(View.VISIBLE);
                commentInputField.setVisibility(View.GONE);
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commentBut.setVisibility(View.VISIBLE);
                commentInputField.setVisibility(View.GONE);
                enviarComentario();
            }
        });

        cargarPublicacion(id);
    }
    public void cargarPublicacion(Integer idComent){
        publicacionInterface = Login_SignUP.retrofitPublicacion.create(PublicacionInterface.class);
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
                //cargar icon
                iconAdd(newPublication.getUsuario().getGenero());
                getComentarios(newPublication.getId());
            }
            @Override
            public void onFailure(Call<Publicacion> call, Throwable t) {
            }
        });
    }

    public void getComentarios(Integer id){
        publicacionInterface = Login_SignUP.retrofitPublicacion.create(PublicacionInterface.class);
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

    public void enviarComentario(){
        Date date = new Date();
        long timeInMilliSeconds = date.getTime();
        java.sql.Date date1 = new java.sql.Date(timeInMilliSeconds);
        Publicacion p = new Publicacion(null, Usuario.getInstance().getId(),
                newPublication.getIdtema(), newPublication.getId(),date1.toString(),0,
                CommnetInput.getText().toString(), "", newPublication.getTema(), Usuario.getInstance(), new Publicacion[0]);

        Toast.makeText(this, newPublication.getId()+" asda", Toast.LENGTH_SHORT).show();
        //añadimos el comentario con el ide de referencia del comentario o publicacion
        publicacionInterface = Login_SignUP.retrofitPublicacion.create(PublicacionInterface.class);
        Call<Publicacion> call = publicacionInterface.save(p);
        call.enqueue(new Callback<Publicacion>() {
            @Override
            public void onResponse(Call<Publicacion> call, Response<Publicacion> response) {
                if(!response.isSuccessful()){
                  return;
                }
                // Toast.makeText(ComentariosActivity.this, "SE HA ENVIADO"+ newPublication.getId().toString(), Toast.LENGTH_SHORT).show();
                Toast.makeText(ComentariosActivity.this, response.body().getIdpublirefer()+" referenciaaaa", Toast.LENGTH_SHORT).show();
                CommnetInput.setText("");
                //volver a recargar los comentarios de la publicacion en el que estamos posicionados
                getComentarios(response.body().getIdpublirefer());
            }
            @Override
            public void onFailure(Call<Publicacion> call, Throwable t) {

            }
        });

    }
    public void iconAdd(String gender){
        if(gender.equals("Femal")){
            iconUserPublish.setImageResource(R.drawable.ic_mujer);

        } else if (gender.equals("Male")) {
            iconUserPublish.setImageResource(R.drawable.ic_hombre);

        }else {
            iconUserPublish.setImageResource(R.drawable.ic_app);
        }
    }
}