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
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
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
    Usuario ConverUser;
    TextView name;
    ImageView iconUserChat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent getId = getIntent();
        String gender = getIntent().getStringExtra("gender");

        iconUserChat = findViewById(R.id.iconChat);
        iconAdd(gender);
        idConversacion = getId.getIntExtra("idConv",0);
        Toast.makeText(this, idConversacion+"", Toast.LENGTH_SHORT).show();

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

                    GuardarChat(new Chat(idConversacion, Usuario.getInstance().getId(),
                            texto.getText().toString(), date1.toString(), ConverUser, Usuario.getInstance()));

                }
            }
        });
        cargarChat();

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
                cargarChat();
            }
            @Override
            public void onFailure(Call<Chat> call, Throwable t) {
            }
        });

    }
    public void CargarUser(){
        UsuarioInterface usuarioInterface = Login_SignUP.retrofitUser.create(UsuarioInterface.class);
        Call<Usuario> call = usuarioInterface.getUserById(idConversacion);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(@NonNull Call<Usuario> call, @NonNull Response<Usuario> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                ConverUser = response.body();
                name.setText(ConverUser.getName().toString());
            }
            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
            }
        });
    }
    public void iconAdd(String gender){
        if (gender.equals("Female")) {
            iconUserChat.setImageResource(R.drawable.ic_mujer);
            ViewGroup.LayoutParams layoutParams = iconUserChat.getLayoutParams();
            layoutParams.height = 100; // Altura
            layoutParams.width = 100; // Anchura
            iconUserChat.setLayoutParams(layoutParams);
        } else if (gender.equals("Male")) {
            iconUserChat.setImageResource(R.drawable.ic_hombre);
        } else {
            iconUserChat.setImageResource(R.drawable.ic_app);
        }
    }
}