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
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.prueba.fragments.RecyclerViews.Adapters.ChatRvAdapter;
import com.prueba.fragments.RetrofitConnection.Interfaces.ChatInterface;
import com.prueba.fragments.RetrofitConnection.Interfaces.PublicacionInterface;
import com.prueba.fragments.RetrofitConnection.Interfaces.UsuarioInterface;
import com.prueba.fragments.RetrofitConnection.Models.Chat;
import com.prueba.fragments.RetrofitConnection.Models.Publicacion;
import com.prueba.fragments.RetrofitConnection.Models.Usuario;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {
    ChatInterface chatInterface;
    Integer idConversacion;
    ArrayList<Chat> Conversation = new ArrayList<>();
    TextInputEditText texto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent getId = getIntent();
        idConversacion = getId.getIntExtra("id", 0);

        texto = findViewById(R.id.editText);
        ImageView send = findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!texto.getText().toString().equals("")){

                    Date date = new Date();
                    long timeInMilliSeconds = date.getTime();
                    java.sql.Date date1 = new java.sql.Date(timeInMilliSeconds);

                    final Usuario[] ConverUser = new Usuario[1];
                    UsuarioInterface usuarioInterface = Login_SignUP.retrofitUser.create(UsuarioInterface.class);
                    Call<Usuario> call = usuarioInterface.getUserById(idConversacion);
                    call.enqueue(new Callback<Usuario>() {
                        @Override
                        public void onResponse(@NonNull Call<Usuario> call, @NonNull Response<Usuario> response) {
                            if (!response.isSuccessful()) {
                                return;
                            }
                            ConverUser[0] = response.body();
                            GuardarChat(new Chat(idConversacion, Usuario.getInstance().getId(),
                                    texto.getText().toString(), date1.toString(), ConverUser[0], Usuario.getInstance()));

                        }
                        @Override
                        public void onFailure(Call<Usuario> call, Throwable t) {
                        }
                    });
                }
            }
        });
        cargarChat();

        ImageView arrow = findViewById(R.id.arrow);
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent listChat = new Intent(ChatActivity.this, MainActivity.class);
                startActivity(listChat);
            }
        });
    }
    public void cargarChat(){
        chatInterface = Login_SignUP.retrofitChat.create(ChatInterface.class);
        Call<List<Chat>> call = chatInterface.getUsersConversation(Usuario.getInstance().getId(), idConversacion);
        call.enqueue(new Callback<List<Chat>>() {
            @Override
            public void onResponse(@NonNull Call<List<Chat>> call, @NonNull Response<List<Chat>> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                Conversation = (ArrayList<Chat>) response.body();

                RecyclerView MyRecyclerView = findViewById(R.id.ConversationListRecyclerView);
                MyRecyclerView.removeAllViews();

                ChatRvAdapter adapter = new ChatRvAdapter(ChatActivity.this, Conversation);
                MyRecyclerView.setAdapter(adapter);
                MyRecyclerView.setLayoutManager(new LinearLayoutManager(ChatActivity.this));
            }
            @Override
            public void onFailure(Call<List<Chat>> call, Throwable t) {
            }
        });
    }

    public void GuardarChat(Chat chat){
        chatInterface = Login_SignUP.retrofitChat.create(ChatInterface.class);
        Call<Chat> call = chatInterface.create(chat);
        call.enqueue(new Callback<Chat>() {
            @Override
            public void onResponse(@NonNull Call<Chat> call, @NonNull Response<Chat> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                texto.setText("");
            }
            @Override
            public void onFailure(Call<Chat> call, Throwable t) {
            }
        });
        cargarChat();
    }
}