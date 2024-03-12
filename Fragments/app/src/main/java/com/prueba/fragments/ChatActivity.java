package com.prueba.fragments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.prueba.fragments.RecyclerViews.Adapters.ChatRvAdapter;
import com.prueba.fragments.RecyclerViews.Adapters.ChatUsersRvAdapter;
import com.prueba.fragments.RetrofitConnection.Interfaces.ConversacionInterface;
import com.prueba.fragments.RetrofitConnection.Interfaces.GrupoInterface;
import com.prueba.fragments.RetrofitConnection.Interfaces.GrupoUsuarioInterface;
import com.prueba.fragments.RetrofitConnection.Models.Conversacion;
import com.prueba.fragments.RetrofitConnection.Models.Grupo;
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
    GrupoInterface grupoInterface;
    //Grupo infoGrupo;
    //Integer idGrupo;
    Integer idGrupoUsuario;
    GrupoUsuario grupoUsuario;
    ArrayList<Conversacion> Conversation = new ArrayList<>();

    ArrayList<Usuario> usuariosGrupo = new ArrayList<>();
    TextInputEditText texto;
    TextView title;
    ImageView iconUserChat;
    ImageView send;
    ImageView arrow;
    AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent getId = getIntent();
        Boolean gender = getId.getBooleanExtra("gender", false);

        iconUserChat = findViewById(R.id.iconChat);
        iconAdd(gender);
        //idGrupo = getId.getIntExtra("idGrupo",0);
        idGrupoUsuario = getId.getIntExtra("idGrupoUsuario",0);
        texto = findViewById(R.id.editText);
        send = findViewById(R.id.send);
        title = findViewById(R.id.groupName);
        arrow = findViewById(R.id.arrow);
        iconUserChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarAlertDialog();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!texto.getText().toString().equals("")){

                    GuardarConversacion();

                }
            }
        });

        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent listChat = new Intent(ChatActivity.this, MainActivity.class);
                listChat.putExtra("numFrgMain", 2);
                startActivity(listChat);
            }
        });

        getGrupoUsuario();
        cargarConversacion();
        cargarUsuarios();
    }
    public void getGrupoUsuario(){
        grupoUsuarioInterface = Login_SignUP.retrofitGrupoUsuario.create(GrupoUsuarioInterface.class);
        Call<GrupoUsuario> callUsers = grupoUsuarioInterface.getById(idGrupoUsuario);
        callUsers.enqueue(new Callback<GrupoUsuario>() {
            @Override
            public void onResponse(@NonNull Call<GrupoUsuario> call, @NonNull Response<GrupoUsuario> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                grupoUsuario = response.body();
            }
            @Override
            public void onFailure(Call<GrupoUsuario> call, Throwable t) {
            }

        });
    }
    public void cargarConversacion(){
        conversacionInterface = Login_SignUP.retrofitConversacion.create(ConversacionInterface.class);
        Call<List<Conversacion>> call = conversacionInterface.getConversacionesByGroupId(grupoUsuario.getIdgrupo());
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


                //que se posicione en el ultimo menjsae del scrollview
                //BUSCAR ALGUNA MANERA PARA OPTIMIZAR LA VISTA DEL ULTIMO MENSAJE
                RecyclerView.LayoutManager layoutManager = MyRecyclerView.getLayoutManager();
                if (layoutManager != null && layoutManager instanceof LinearLayoutManager) {
                    int ultimoElemento = layoutManager.getItemCount() - 1;
                    ((LinearLayoutManager) layoutManager).scrollToPositionWithOffset(ultimoElemento, 0);
                }

            }
            @Override
            public void onFailure(Call<List<Conversacion>> call, Throwable t) {
            }
        });
    }
    public void cargarUsuarios(){
        grupoUsuarioInterface = Login_SignUP.retrofitGrupoUsuario.create(GrupoUsuarioInterface.class);
        Call<List<Usuario>> callUsers = grupoUsuarioInterface.getGroupUsers(grupoUsuario.getIdgrupo());
        callUsers.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(@NonNull Call<List<Usuario>> call, @NonNull Response<List<Usuario>> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                usuariosGrupo = (ArrayList<Usuario>) response.body();
            }
            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
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
                cargarConversacion();
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

    @SuppressLint("SetTextI18n")
    private void mostrarAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Inflar el diseño personalizado
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_group_details, null);
        builder.setView(dialogView);

        // Obtener referencias de los botones personalizados
        ImageView buttonCerrar = dialogView.findViewById(R.id.cerrarAD);
        Button buttonSalirGrupo = dialogView.findViewById(R.id.salirGrupo);
        TextView nombreGrupo = dialogView.findViewById(R.id.nombreGrupo);
        TextView codigoGrupo = dialogView.findViewById(R.id.codigoGrupo);

        RecyclerView listaUsuarios = dialogView.findViewById(R.id.usuariosRecyclerView);
        listaUsuarios.removeAllViews();

        //RV de usuarios
        ChatUsersRvAdapter adapter = new ChatUsersRvAdapter(ChatActivity.this, usuariosGrupo);
        listaUsuarios.setAdapter(adapter);
        listaUsuarios.setLayoutManager(new LinearLayoutManager(ChatActivity.this));

        //Datos del grupo
        nombreGrupo.setText(grupoUsuario.getGrupo().getNombre().toString());
        codigoGrupo.setText("Codigo de invitacion:\n" + grupoUsuario.getGrupo().getCodigo().toString());
        // Configurar controladores de clic para los botones
        buttonCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();  // Dismiss the dialog when Cancel button is clicked
            }
        });

        buttonSalirGrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Eliminar registro para salir

            }
        });
        alertDialog = builder.create();
        alertDialog.show();
    }
}