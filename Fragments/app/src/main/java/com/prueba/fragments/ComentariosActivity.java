package com.prueba.fragments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.prueba.fragments.RecyclerViews.Adapters.PublicacionRvAdapter;
import com.prueba.fragments.RetrofitConnection.Interfaces.PublicacionInterface;
import com.prueba.fragments.RetrofitConnection.Interfaces.UsuarioInterface;
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
    int idP;
    int idU;
    ImageView iconUserPublish;
    ImageView iconLike;
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
    ConstraintLayout constraintLayout;
    boolean liked;
    int numLikePublish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentarios);

        Intent getId = getIntent();
        idP = getId.getIntExtra("id", 0);
        idU = getId.getIntExtra("idUser", 0);

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
        iconLike = findViewById(R.id.liekButton);
        commentBut = findViewById(R.id.commentButt);
        back = findViewById(R.id.arrow);
        home = findViewById(R.id.home);
        send = findViewById(R.id.sendComm);

        //Para dar like o quitar el like
        iconLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(liked){
                    numLikePublish --;
                    numLikes.setText(numLikePublish+"");
                    iconLike.getDrawable().setColorFilter(ContextCompat.getColor(ComentariosActivity.this, R.color.black), PorterDuff.Mode.MULTIPLY);
                    liked = false;
                    MainActivity.quitarLike(newPublication.getId());
                }else {
                    numLikePublish ++;
                    numLikes.setText(numLikePublish + "");
                    iconLike.getDrawable().setColorFilter(ContextCompat.getColor(ComentariosActivity.this, R.color.md_theme_light_primary), PorterDuff.Mode.MULTIPLY);
                    liked = true;
                    Toast.makeText(ComentariosActivity.this, "primer click", Toast.LENGTH_SHORT).show();
                    MainActivity.darLike(newPublication.getId());
                }

            }
        });

        contenidoTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentHeight = contenidoTv.getLayoutParams().height;

                if (currentHeight == ViewGroup.LayoutParams.WRAP_CONTENT) {
                    contenidoTv.getLayoutParams().height = 160;
                } else {
                    contenidoTv.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                }
                contenidoTv.requestLayout();
            }
        });
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


        cargarPublicacion(idP);
        visitarPerfil();
    }
    public void cargarPublicacion(Integer idComent){
        Call<Publicacion> call = MainActivity.publicacionInterface.getPublicationById(idComent);
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
                numLikePublish = newPublication.getNumlikes();
                numLikes.setText(numLikePublish+"");

                //cargar icon
                MainActivity.addPicture(iconUserPublish,ComentariosActivity.this, newPublication.getUsuario().getFoto());
                getComentarios(newPublication.getId());

                //cargamos el like de la publicacion
                cargarLike();
            }
            @Override
            public void onFailure(Call<Publicacion> call, Throwable t) {
            }
        });
    }

    public void getComentarios(Integer id){
        Call<List<Publicacion>> call = MainActivity.publicacionInterface.getAllComentsFromPublish(id);
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

        //añadimos el comentario con el ide de referencia del comentario o publicacion
        Call<Publicacion> call = MainActivity.publicacionInterface.save(p);
        call.enqueue(new Callback<Publicacion>() {
            @Override
            public void onResponse(Call<Publicacion> call, Response<Publicacion> response) {
                if(!response.isSuccessful()){
                  return;
                }
                // Toast.makeText(ComentariosActivity.this, "SE HA ENVIADO"+ newPublication.getId().toString(), Toast.LENGTH_SHORT).show();
                CommnetInput.setText("");

                //volver a recargar los comentarios de la publicacion en el que estamos posicionados
                cargarPublicacion(response.body().getIdpublirefer());
            }
            @Override
            public void onFailure(Call<Publicacion> call, Throwable t) {

            }
        });

    }

    //visitar el perfil del usuario cuando le das click a la foto de su perfil en la publicacion
    public void visitarPerfil(){
        iconUserPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewProfile = new Intent(ComentariosActivity.this, MainActivity.class);
                viewProfile.putExtra("numFrgMain", 3);
               //Le pasamos por argumento el objeto
                Bundle bundle = new Bundle();
                bundle.putSerializable("perfil", newPublication.getUsuario());
                viewProfile.putExtra("perfilBundle", bundle);
                startActivity(viewProfile);
            }
        });

    }
    //para colocar el color del like cuando este ya está en la tabla likes
    public void cargarLike(){
    Call <Boolean> call = MainActivity.usuarioInterface.userLikedPublish(Usuario.getInstance().getId(), newPublication.getId());
    call.enqueue(new Callback<Boolean>() {
        @Override
        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
            if(!response.isSuccessful()){
                return;
            }
            if(response.body()){
                iconLike.getDrawable().setColorFilter(ContextCompat.getColor(ComentariosActivity.this, R.color.md_theme_light_primary), PorterDuff.Mode.MULTIPLY);
                liked=true;
            }
        }

        @Override
        public void onFailure(Call<Boolean> call, Throwable t) {

        }
    });

    }
}