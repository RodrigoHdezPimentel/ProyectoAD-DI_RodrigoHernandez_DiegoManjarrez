package com.prueba.fragments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

import com.google.android.material.textfield.TextInputEditText;
import com.prueba.fragments.Class.ChatListUser;
import com.prueba.fragments.Class.Message;
import com.prueba.fragments.Class.ThreadChat.ConnectionChat;
import com.prueba.fragments.Class.ThreadChat.oldModel;
import com.prueba.fragments.RecyclerViews.Adapters.ChatRvAdapter;
import com.prueba.fragments.RecyclerViews.Adapters.ChatUsersRvAdapter;
import com.prueba.fragments.RecyclerViews.Adapters.ListaGruposShareCodeRvAdapter;
import com.prueba.fragments.RetrofitConnection.Models.Conversacion;
import com.prueba.fragments.RetrofitConnection.Models.Grupo;
import com.prueba.fragments.RetrofitConnection.Models.GrupoUsuario;
import com.prueba.fragments.RetrofitConnection.Models.Usuario;

import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {
    ConnectionChat connectionChat;
    public static ArrayList<Integer> idsGrupoUsuarioShareedCodeGroups = new ArrayList<>();
    RecyclerView MyRecyclerView;
    Integer idGrupo;
    Grupo infoGrupo;
    static Conversacion conversacionSeleccionada;
    Integer idGrupoUsuario;
    GrupoUsuario grupoUsuario;
    ArrayList<Message> Conversation = new ArrayList<>();
    ArrayList<Usuario> usuariosGrupo = new ArrayList<>();
    ArrayList<ChatListUser> listaGrupos = new ArrayList<>();
    static TextInputEditText texto;
    TextView title;
    ImageView iconChat;
    static Integer messagePosition;
    static TextView textoActualizar;
    static ImageView cross;
    static ImageView rubish;
    static ImageView confirm;
    static ImageView send;
    ImageView arrow;
    AlertDialog alertDialog;

    AlertDialog alertDialogGroups;
    ChatRvAdapter adapter;
    oldModel hiloChat;
    static ConstraintLayout editConsLay;
    static LinearLayout defaultLinLay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent getId = getIntent();
        String pathFoto = getId.getStringExtra("foto");

        iconChat = findViewById(R.id.iconChat);

        editConsLay = findViewById(R.id.linearLayoutEditMessage);
        editConsLay.setVisibility(View.GONE);
        defaultLinLay = findViewById(R.id.linearLayout);
        defaultLinLay.setVisibility(View.VISIBLE);

        MainActivity.addPicture(iconChat, ChatActivity.this, pathFoto);
        idGrupo = getId.getIntExtra("idGrupo",0);
        idGrupoUsuario = getId.getIntExtra("idGrupoUsuario",0);
        texto = findViewById(R.id.editText);
        send = findViewById(R.id.send);
        title = findViewById(R.id.groupName);
        arrow = findViewById(R.id.arrow);
        confirm = findViewById(R.id.confirmEditMessage);
        cross = findViewById(R.id.closeEditMessage);
        rubish = findViewById(R.id.deleteMessage);

        buttonListener();
        getGrupoUsuario();
        cargarConversacion();
        cargarGrupo();
        cargarUsuarios();

    }
    public void buttonListener(){
        iconChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarAlertDialog();
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Objects.requireNonNull(texto.getText()).toString().isEmpty()){
                    GuardarConversacion();}
            }
        });
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent listChat = new Intent(ChatActivity.this, MainActivity.class);
                listChat.putExtra("numFrgMain", 2);
                //para detener el hilo
                connectionChat.setEndChat(true);
                //hiloChat.setHiloEnded(true);
                startActivity(listChat);
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                defaultLinLay.setVisibility(View.VISIBLE);
                editConsLay.setVisibility(View.GONE);
                send.setVisibility(View.VISIBLE);
                //Llamada retrofit para actualizar la conversascion
                Call<Void> callUpdate = MainActivity.conversacionInterface.updateContent(conversacionSeleccionada.getIdConversacion(), texto.getText().toString());
                callUpdate.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (!response.isSuccessful()) {
                            Log.e("Response err: ", response.message());
                            Toast.makeText(ChatActivity.this, "error", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Toast.makeText(ChatActivity.this, "updated", Toast.LENGTH_SHORT).show();
                        textoActualizar.setText(texto.getText().toString());
                        texto.setText("");
                        cargarConversacion();
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                    }
                });
            }
        });
        rubish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                defaultLinLay.setVisibility(View.VISIBLE);
                editConsLay.setVisibility(View.GONE);
                send.setVisibility(View.VISIBLE);
                //llamada al retrofit para eliminar mensaje

                //MyRecyclerView.removeViewAt(messagePosition);

                Call<Void> callUpdate = MainActivity.conversacionInterface.deleteConversation(conversacionSeleccionada.getIdConversacion());
                callUpdate.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (!response.isSuccessful()) {
                            Log.e("Response err: ", response.message());
                            Toast.makeText(ChatActivity.this, "error", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Toast.makeText(ChatActivity.this, "eliminado", Toast.LENGTH_SHORT).show();
                        texto.setText("");
                        cargarConversacion();
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                    }
                });
            }
        });
        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                defaultLinLay.setVisibility(View.VISIBLE);
                editConsLay.setVisibility(View.GONE);
                send.setVisibility(View.VISIBLE);
                texto.setText("");
            }
        });

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

        Call<ArrayList<Message>> call = MainActivity.conversacionInterface.getConversacionesByGroupId(idGrupo);
        call.enqueue(new Callback<ArrayList<Message>>() {

            @Override
            public void onResponse(Call<ArrayList<Message>> call, Response<ArrayList<Message>> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                Conversation = response.body();
                MyRecyclerView = findViewById(R.id.ConversationListRecyclerView);
                MyRecyclerView.removeAllViews();
                adapter = new ChatRvAdapter(ChatActivity.this, Conversation,MyRecyclerView);

                MyRecyclerView.setAdapter(adapter);
                MyRecyclerView.setLayoutManager(new LinearLayoutManager(ChatActivity.this));

                //que se posicione en el ultimo menjsae del scrollview
                //BUSCAR ALGUNA MANERA PARA OPTIMIZAR LA VISTA DEL ULTIMO MENSAJE
                RecyclerView.LayoutManager layoutManager = MyRecyclerView.getLayoutManager();
                if (layoutManager != null && layoutManager instanceof LinearLayoutManager) {
                    ((LinearLayoutManager) layoutManager).scrollToPositionWithOffset(layoutManager.getItemCount() - 1, 0);
                }
                    //Se ecnarga de leer los mensajes que aun estaban sin leer por el user
                    UpdateIdLeido();

                    connectionChat = new ConnectionChat(idGrupo,send,texto,idGrupoUsuario,adapter);
                    connectionChat.start();

/*
                //Arranco el hilo caundo se carga la conversación
                 hiloChat = new oldModel(adapter, idGrupo,ChatActivity.this);
                hiloChat.start();
*/

            }

            @Override
            public void onFailure(Call<ArrayList<Message>> call, Throwable t) {

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

        Conversacion newConversacion = new Conversacion(null, idGrupoUsuario, getDateSpain(), texto.getText().toString(),  Usuario.getInstance().getId().toString());
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
        ImageView shareCode = dialogView.findViewById(R.id.shareCode);
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
        if(infoGrupo.getCodigo() == null){
            codigoGrupo.setVisibility(View.INVISIBLE);
            shareCode.setVisibility(View.INVISIBLE);
        }else{
            codigoGrupo.setText("Codigo de invitacion:\n" + infoGrupo.getCodigo());
            //Cuando lo clique, muestra el listado de tus grupos
            shareCode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Cargo los grupos y luego abro el alertDialog
                    getMyGroups();
                    alertDialog.dismiss();
                }
            });
        }
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
                        title.setText(nombreGrupo.getText().toString());
                        unlockNameField.setVisibility(View.VISIBLE);
                        nombreGrupo.setEnabled(false);
                        confirmName.setVisibility(View.GONE);
                    }
                    @Override
                    public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
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
    public void mostrarListaChats(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Inflar el diseño personalizado
        LayoutInflater inflater = getLayoutInflater();
        View listChatView = inflater.inflate(R.layout.dialog_group_list_send_group_code, null);
        builder.setView(listChatView);

        RecyclerView listaGruposRV = listChatView.findViewById(R.id.rvListGroups);
        listaGruposRV.removeAllViews();

        //RV de usuarios
        ListaGruposShareCodeRvAdapter adapter = new ListaGruposShareCodeRvAdapter(ChatActivity.this, listaGrupos);
        listaGruposRV.setAdapter(adapter);
        listaGruposRV.setLayoutManager(new LinearLayoutManager(ChatActivity.this));

        Button cancel = listChatView.findViewById(R.id.cancelCode);
        Button send = listChatView.findViewById(R.id.sendCode);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idsGrupoUsuarioShareedCodeGroups.removeAll(idsGrupoUsuarioShareedCodeGroups);
                alertDialogGroups.dismiss();
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Mandar los mensajes con el codigo
                enviarCodigo();
                alertDialogGroups.dismiss();
            }
        });
        alertDialogGroups = builder.create();
        alertDialogGroups.show();
    }
    public void enviarCodigo(){
        Conversacion newConversacion;

        for (Integer i : idsGrupoUsuarioShareedCodeGroups) {
            newConversacion = new Conversacion(
                    null, i, getDateSpain(),
                    "Unete a mi grupo:\n" + "http//:localhost:8086/chat/join/"+infoGrupo.getCodigo().toString(),
                    "0," + Usuario.getInstance().getId().toString());

            Call<Conversacion> call = MainActivity.conversacionInterface.save(newConversacion);
            call.enqueue(new Callback<Conversacion>() {
                @Override
                public void onResponse(@NonNull Call<Conversacion> call, @NonNull Response<Conversacion> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(ChatActivity.this, "error", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Toast.makeText(ChatActivity.this, "creado", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<Conversacion> call, Throwable t) {
                }
            });
        }
        idsGrupoUsuarioShareedCodeGroups.removeAll(idsGrupoUsuarioShareedCodeGroups);
    }
    public void getMyGroups(){
        Call<List<ChatListUser>> call = MainActivity.grupoUsuarioInterface.getListChatFromUser(Usuario.getInstance().getId());
        call.enqueue(new Callback<List<ChatListUser>>() {
            @Override
            public void onResponse(Call<List<ChatListUser>> call, Response<List<ChatListUser>> response) {
                if(!response.isSuccessful()){
                    return;
                }

                for (ChatListUser c : response.body()){
                    if(c.getChat().getGrupoUsuarioFK().getIdgrupo() != idGrupo){
                        listaGrupos.add(c);
                    }
                }
                mostrarListaChats();
            }
            @Override
            public void onFailure(Call<List<ChatListUser>> call, Throwable t) {

            }
        });
    }
    public void salirGrupo(){
        Call<Void> call = MainActivity.grupoUsuarioInterface.salitGrupo(idGrupoUsuario, getDateSpain());
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

    public static void editConversacion(Conversacion c, TextView contenido, Integer position){
        messagePosition = position;
        textoActualizar = contenido;
        conversacionSeleccionada = c;
        defaultLinLay.setVisibility(View.GONE);
        editConsLay.setVisibility(View.VISIBLE);
        send.setVisibility(View.GONE);
        Toast.makeText(send.getContext(), c.getIdConversacion().toString(), Toast.LENGTH_SHORT).show();
        texto.setText(c.getContenido());
    }
    public String getDateSpain(){
        Date date = new Date();
        //Zona
        TimeZone tz = TimeZone.getTimeZone("Europe/Madrid");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        sdf.setTimeZone(tz);

       return sdf.format(date).toString();

    }
    public void UpdateIdLeido(){
        Call<Void> call = MainActivity.conversacionInterface.readMessages(Usuario.getInstance().getId(), idGrupo);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(!response.isSuccessful()){
                    return;
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }});
    }
}