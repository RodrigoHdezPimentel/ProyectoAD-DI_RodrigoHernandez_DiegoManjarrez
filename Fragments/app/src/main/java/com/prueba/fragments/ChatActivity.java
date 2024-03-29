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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.prueba.fragments.Class.LoadConversation;
import com.prueba.fragments.Class.ThreadChat;
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
    Integer idGrupo;
    Grupo infoGrupo;
    Integer idGrupoUsuario;
    GrupoUsuario grupoUsuario;
    ArrayList<LoadConversation> Conversation = new ArrayList<>();
    ArrayList<Usuario> usuariosGrupo = new ArrayList<>();
    TextInputEditText texto;
    TextView title;
    ImageView iconUserChat;
    ImageView send;
    ImageView arrow;
    AlertDialog alertDialog;
    ChatRvAdapter adapter;
    ThreadChat hiloChat;
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
                //para detener el hilo
                hiloChat.setHiloEnded(true);
                startActivity(listChat);
            }
        });

        getGrupoUsuario();
        cargarConversacion();
        cargarGrupo();
        cargarUsuarios();

    }
    public void getGrupoUsuario() {
        Call<GrupoUsuario> call = MainActivity.grupoUsuarioInterface.getById(idGrupoUsuario);
        call.enqueue(new Callback<GrupoUsuario>() {
            @Override
            public void onResponse(@NonNull Call<GrupoUsuario> call, @NonNull Response<GrupoUsuario> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                grupoUsuario = response.body();
                title.setText(grupoUsuario.getNombre().toString());
            }
            @Override
            public void onFailure(Call<GrupoUsuario> call, Throwable t) {
            }
        });
    }

    public void cargarConversacion(){

        Call<ArrayList<LoadConversation>> call = MainActivity.conversacionInterface.getConversacionesByGroupId(idGrupo);
        call.enqueue(new Callback<ArrayList<LoadConversation>>() {

            @Override
            public void onResponse(Call<ArrayList<LoadConversation>> call, Response<ArrayList<LoadConversation>> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                Conversation = response.body();
                RecyclerView MyRecyclerView = findViewById(R.id.ConversationListRecyclerView);
                MyRecyclerView.removeAllViews();
                adapter = new ChatRvAdapter(ChatActivity.this, Conversation,MyRecyclerView);

                MyRecyclerView.setAdapter(adapter);
                MyRecyclerView.setLayoutManager(new LinearLayoutManager(ChatActivity.this));

                //que se posicione en el ultimo menjsae del scrollview
                //BUSCAR ALGUNA MANERA PARA OPTIMIZAR LA VISTA DEL ULTIMO MENSAJE
                RecyclerView.LayoutManager layoutManager = MyRecyclerView.getLayoutManager();
                if (layoutManager != null && layoutManager instanceof LinearLayoutManager) {
                    int ultimoElemento = layoutManager.getItemCount() - 1;
                    ((LinearLayoutManager) layoutManager).scrollToPositionWithOffset(ultimoElemento, 0);
                }
                //Arranco el hilo caundo se carga la conversación
                hiloChat = new ThreadChat(adapter, idGrupo,ChatActivity.this);
                hiloChat.start();
            }

            @Override
            public void onFailure(Call<ArrayList<LoadConversation>> call, Throwable t) {

            }
        });


    }

    public void cargarUsuarios(){
        Call<List<Usuario>> callUsers = MainActivity.grupoUsuarioInterface.getGroupUsers(idGrupo);
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

    public void cargarGrupo(){
        Call<Grupo> callUsers = MainActivity.grupoInterface.getById(idGrupo);
        callUsers.enqueue(new Callback<Grupo>() {
            @Override
            public void onResponse(@NonNull Call<Grupo> call, @NonNull Response<Grupo> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                infoGrupo = response.body();
            }
            @Override
            public void onFailure(Call<Grupo> call, Throwable t) {
            }
        });
    }

    public void GuardarConversacion(){

        Date date = new Date();
        long timeInMilliSeconds = date.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String formattedDate = sdf.format(new Date(timeInMilliSeconds));

        Conversacion newConversacion = new Conversacion(null, idGrupoUsuario, formattedDate.toString(), texto.getText().toString(), "0," + Usuario.getInstance().getId().toString());
        Call<Conversacion> call = MainActivity.conversacionInterface.save(newConversacion);
        call.enqueue(new Callback<Conversacion>() {
            @Override
            public void onResponse(@NonNull Call<Conversacion> call, @NonNull Response<Conversacion> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(ChatActivity.this, "error", Toast.LENGTH_SHORT).show();
                    return;
                }
                texto.setText("");
            }
            @Override
            public void onFailure(Call<Conversacion> call, Throwable t) {
            }
        });
    }

    public void iconAdd(Boolean gender){
        if(gender == null){
            iconUserChat.setImageResource(R.drawable.ic_app);
        } else {
            if (!gender) {
                iconUserChat.setImageResource(R.drawable.ic_mujer);
            } else {
                iconUserChat.setImageResource(R.drawable.ic_hombre);
            }
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
        ImageView confirmName = dialogView.findViewById(R.id.checkName);
        ImageView unlockNameField = dialogView.findViewById(R.id.modifyName);
        Button buttonSalirGrupo = dialogView.findViewById(R.id.salirGrupo);
        EditText nombreGrupo = dialogView.findViewById(R.id.nombreGrupo);
        TextView codigoGrupo = dialogView.findViewById(R.id.codigoGrupo);

        unlockNameField.setVisibility(View.VISIBLE);
        nombreGrupo.setEnabled(false);
        confirmName.setVisibility(View.GONE);

        RecyclerView listaUsuarios = dialogView.findViewById(R.id.usuariosRecyclerView);
        listaUsuarios.removeAllViews();

        //RV de usuarios
        ChatUsersRvAdapter adapter = new ChatUsersRvAdapter(ChatActivity.this, usuariosGrupo);
        listaUsuarios.setAdapter(adapter);
        listaUsuarios.setLayoutManager(new LinearLayoutManager(ChatActivity.this));

        //Datos del grupo
        nombreGrupo.setText(grupoUsuario.getNombre());
        codigoGrupo.setText("Codigo de invitacion:\n" + infoGrupo.getCodigo().toString());
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
                // Acciones al cancelar
                salirGrupo();
            }
        });

        confirmName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Void> call = MainActivity.grupoUsuarioInterface.updateGroupName(nombreGrupo.getText().toString(),idGrupoUsuario);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(ChatActivity.this, "error", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        unlockNameField.setVisibility(View.VISIBLE);
                        nombreGrupo.setEnabled(false);
                        confirmName.setVisibility(View.GONE);
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                    }
                });
            }
        });

        unlockNameField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombreGrupo.setEnabled(true);
                unlockNameField.setVisibility(View.GONE);
                confirmName.setVisibility(View.VISIBLE);
            }
        });

        alertDialog = builder.create();
        alertDialog.show();
    }

    public void salirGrupo(){
        Date date = new Date();
        long timeInMilliSeconds = date.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String formattedDate = sdf.format(new Date(timeInMilliSeconds));

        Call<Void> call = MainActivity.grupoUsuarioInterface.salitGrupo(idGrupoUsuario, formattedDate);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(ChatActivity.this, "error", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(ChatActivity.this, "Saliste del grupo", Toast.LENGTH_SHORT).show();
                Intent toListChat = new Intent(ChatActivity.this, MainActivity.class);
                startActivity(toListChat);
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });
    }
}