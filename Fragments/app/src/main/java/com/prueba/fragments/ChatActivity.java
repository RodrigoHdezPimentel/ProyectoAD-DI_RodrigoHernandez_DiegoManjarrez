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
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.prueba.fragments.RecyclerViews.Adapters.ChatRvAdapter;
import com.prueba.fragments.RetrofitConnection.Interfaces.ConversacionInterface;
import com.prueba.fragments.RetrofitConnection.Interfaces.GrupoUsuarioInterface;
import com.prueba.fragments.RetrofitConnection.Models.Conversacion;
import com.prueba.fragments.RetrofitConnection.Models.GrupoUsuario;
import com.prueba.fragments.RetrofitConnection.Models.Usuario;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {
    ConversacionInterface conversacionInterface;
    GrupoUsuarioInterface grupoUsuarioInterface;
    Integer idGrupo;
    Integer idGrupoUsuario;
    ArrayList<Conversacion> Conversation = new ArrayList<>();
    TextInputEditText texto;
    TextView title;
    ImageView iconUserChat;
    ImageView send;
    ImageView arrow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent getId = getIntent();
        Boolean gender = getId.getBooleanExtra("gender", false);

        iconUserChat = findViewById(R.id.iconChat);
        iconAdd(gender);
        idGrupo = getId.getIntExtra("idGrupo",0);
        idGrupoUsuario = getId.getIntExtra("idGrupoUsuario",0);
        texto = findViewById(R.id.editText);
        send = findViewById(R.id.send);
        title = findViewById(R.id.groupName);
        arrow = findViewById(R.id.arrow);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!texto.getText().toString().equals("")){

                    GuardarConversacion();

                }
            }
        });
        cargarGrupo();


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
    public void GuardarConversacion(){

        Date date = new Date();
        long timeInMilliSeconds = date.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String formattedDate = sdf.format(new Date(timeInMilliSeconds));


        Conversacion newConversacion = new Conversacion(null, idGrupoUsuario, formattedDate.toString(), texto.getText().toString());

        conversacionInterface = Login_SignUP.retrofitConversacion.create(ConversacionInterface.class);
        Call<Conversacion> call = conversacionInterface.save(newConversacion);
        call.enqueue(new Callback<Conversacion>() {
            @Override
            public void onResponse(@NonNull Call<Conversacion> call, @NonNull Response<Conversacion> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(ChatActivity.this, "error", Toast.LENGTH_SHORT).show();
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
    public void iconAdd(Boolean gender){
        if (!gender) {
            iconUserChat.setImageResource(R.drawable.ic_mujer);
//            ViewGroup.LayoutParams layoutParams = iconUserChat.getLayoutParams();
//            layoutParams.height = 100; // Altura
//            layoutParams.width = 100; // Anchura
//            iconUserChat.setLayoutParams(layoutParams);
        } else if (gender) {
            iconUserChat.setImageResource(R.drawable.ic_hombre);
        } else {
            iconUserChat.setImageResource(R.drawable.ic_app);
        }
    }
}