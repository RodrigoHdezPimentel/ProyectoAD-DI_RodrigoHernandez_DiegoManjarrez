package com.prueba.fragments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.prueba.fragments.RecyclerViews.Adapters.ChatRvAdapter;
import com.prueba.fragments.RetrofitConnection.Interfaces.ConversacionInterface;
import com.prueba.fragments.RetrofitConnection.Interfaces.GrupoUsuarioInterface;
import com.prueba.fragments.RetrofitConnection.Models.Conversacion;
import com.prueba.fragments.RetrofitConnection.Models.GrupoUsuario;
import com.prueba.fragments.RetrofitConnection.Models.Usuario;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {
    ConversacionInterface conversacionInterface;
    Integer idGrupo;
    GrupoUsuario grupoUsuario;
    ArrayList<Conversacion> Conversation = new ArrayList<>();
    TextInputEditText texto;
    Usuario ConverUser;
    TextView name;
    ImageView iconUserChat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent getId = getIntent();
        Bundle bundle = getId.getExtras();
        String gender = getIntent().getStringExtra("gender");

        iconUserChat = findViewById(R.id.iconChat);
        iconAdd(gender);
        idGrupo = getId.getIntExtra("idGrupo",0);
        grupoUsuario = (GrupoUsuario) bundle.getSerializable("grupoUsuario");

        texto = findViewById(R.id.editText);
        ImageView send = findViewById(R.id.send);

        CargarUser();
        name = findViewById(R.id.nameTv);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!texto.getText().toString().equals("")){

                    Date date = new Date();
                    long timeInMilliSeconds = date.getTime();
                    java.sql.Date date1 = new java.sql.Date(timeInMilliSeconds);

                    GuardarConversacion(new Conversacion(grupoUsuario, date1.toString(), texto.getText().toString()));

                }
            }
        });
        cargarGrupo();

        ImageView arrow = findViewById(R.id.arrow);
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent listChat = new Intent(ChatActivity.this, MainActivity.class);
                listChat.putExtra("numFrgMain", 2);
                startActivity(listChat);
            }
        });
    }
    public void cargarGrupo(){
        conversacionInterface = Login_SignUP.retrofitConversacion.create(ConversacionInterface.class);
        Call<List<Conversacion>> call = conversacionInterface.getConversacionesByGroupId(idGrupo);
        call.enqueue(new Callback<List<Conversacion>>() {
            @Override
            public void onResponse(@NonNull Call<List<Conversacion>> call, @NonNull Response<List<Conversacion>> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                Conversation = (ArrayList<Conversacion>) response.body();

                RecyclerView MyRecyclerView = findViewById(R.id.ConversationListRecyclerView);
                MyRecyclerView.removeAllViews();

                ChatRvAdapter adapter = new ChatRvAdapter(ChatActivity.this, Conversation);
                MyRecyclerView.setAdapter(adapter);
                MyRecyclerView.setLayoutManager(new LinearLayoutManager(ChatActivity.this));
            }
            @Override
            public void onFailure(Call<List<Conversacion>> call, Throwable t) {
            }
        });
    }

    public void GuardarConversacion(Conversacion conversacion){
        conversacionInterface = Login_SignUP.retrofitConversacion.create(ConversacionInterface.class);
        Call<Conversacion> call = conversacionInterface.create(conversacion);
        call.enqueue(new Callback<Conversacion>() {
            @Override
            public void onResponse(@NonNull Call<Conversacion> call, @NonNull Response<Conversacion> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                texto.setText("");
                cargarGrupo();
            }
            @Override
            public void onFailure(Call<Conversacion> call, Throwable t) {
            }
        });
    }
    public void CargarUser(){
        GrupoUsuarioInterface grupoUsuarioInterface = Login_SignUP.retrofitGrupoUsuario.create(GrupoUsuarioInterface.class);
        Call<GrupoUsuario> call =  grupoUsuarioInterface.getById(grupoUsuario.getIdGrupoUsuario());
        call.enqueue(new Callback<GrupoUsuario>() {
            @Override
            public void onResponse(@NonNull Call<GrupoUsuario> call, @NonNull Response<GrupoUsuario> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                ConverUser = response.body().getGrupoUsuarioFK().getUsuario();
                name.setText(ConverUser.getName().toString());
            }
            @Override
            public void onFailure(Call<GrupoUsuario> call, Throwable t) {
            }
        });
    }
    public void iconAdd(String gender){
        if (gender.equals("Female")) {
            iconUserChat.setImageResource(R.drawable.ic_mujer);
//            ViewGroup.LayoutParams layoutParams = iconUserChat.getLayoutParams();
//            layoutParams.height = 100; // Altura
//            layoutParams.width = 100; // Anchura
//            iconUserChat.setLayoutParams(layoutParams);
        } else if (gender.equals("Male")) {
            iconUserChat.setImageResource(R.drawable.ic_hombre);
        } else {
            iconUserChat.setImageResource(R.drawable.ic_app);
        }
    }
}