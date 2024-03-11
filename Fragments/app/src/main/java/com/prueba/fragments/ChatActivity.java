package com.prueba.fragments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.prueba.fragments.RetrofitConnection.Interfaces.UsuarioInterface;
import com.prueba.fragments.RetrofitConnection.Models.Conversacion;
import com.prueba.fragments.RetrofitConnection.Models.Grupo;
import com.prueba.fragments.RetrofitConnection.Models.GrupoUsuario;
import com.prueba.fragments.RetrofitConnection.Models.GrupoUsuarioFK;
import com.prueba.fragments.RetrofitConnection.Models.Usuario;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {
    ConversacionInterface conversacionInterface;
    UsuarioInterface usuarioInterface;
    GrupoUsuarioInterface grupoUsuarioInterface;
    GrupoInterface grupoInterface;
    Grupo infoGrupo;
    Integer idGrupo;
    Integer idGrupoUsuario;
    Integer idOtherUser;
    Usuario otherUser;
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
        idGrupo = getId.getIntExtra("idGrupo", 0);
        idGrupoUsuario = getId.getIntExtra("idGrupoUsuario", 0);
        //A la hora de crear una conversacion con otra persona recibo su id para
        //crear los dos GrupoUsuarios de ambos cuando se envie el primer mensaje
        idOtherUser = getId.getIntExtra("idUsuario", 0);
        texto = findViewById(R.id.editText);
        send = findViewById(R.id.send);
        title = findViewById(R.id.groupName);

        iconUserChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarAlertDialog();
            }
        });

        arrow = findViewById(R.id.arrow);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!texto.getText().toString().equals("")) {
                    if (Conversation.size() != 0) {
                        GuardarConversacion();
                    } else {
                        //GenerarGrupo llama luego a generar GrupoUsuario y despues ya guarda la conversacion
                        getOtherUser();
                    }
                }
            }
        });

        cargarConversacion();
        if (Conversation.size() != 0) {
            cargarGrupo();
            cargarUsuarios();
        }
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent listChat = new Intent(ChatActivity.this, MainActivity.class);
                listChat.putExtra("numFrgMain", 2);
                startActivity(listChat);
            }
        });
    }
    public void getOtherUser() {
        usuarioInterface = Login_SignUP.retrofitUser.create(UsuarioInterface.class);
        Call<Usuario> call = usuarioInterface.getUserById(idOtherUser);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(@NonNull Call<Usuario> call, @NonNull Response<Usuario> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(ChatActivity.this, "error", Toast.LENGTH_SHORT).show();
                    return;
                }
                otherUser = response.body();
                GenerarGrupo();
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
            }
        });
    }

    public void GenerarGrupo() {
        grupoInterface = Login_SignUP.retrofitGrupo.create(GrupoInterface.class);
        Call<Grupo> call = grupoInterface.create(new Grupo(null, otherUser.getName().toString(), null, generarCodigoDeGrupo()));
        call.enqueue(new Callback<Grupo>() {
            @Override
            public void onResponse(@NonNull Call<Grupo> call, @NonNull Response<Grupo> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(ChatActivity.this, "error", Toast.LENGTH_SHORT).show();
                    return;
                }
                infoGrupo = response.body();
                title.setText(infoGrupo.getNombre().toString());
                GenerarGrupoUsuarios();
            }

            @Override
            public void onFailure(Call<Grupo> call, Throwable t) {
            }
        });
    }

    public void GenerarGrupoUsuarios() {
        //Genero el GrupoUsuario del otro usuario
        grupoUsuarioInterface = Login_SignUP.retrofitGrupoUsuario.create(GrupoUsuarioInterface.class);
        Call<GrupoUsuario> call = grupoUsuarioInterface.create(new GrupoUsuario(null, new GrupoUsuarioFK(idOtherUser, infoGrupo.getIdGrupo())));
        call.enqueue(new Callback<GrupoUsuario>() {
            @Override
            public void onResponse(@NonNull Call<GrupoUsuario> call, @NonNull Response<GrupoUsuario> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(ChatActivity.this, "error", Toast.LENGTH_SHORT).show();
                    return;
                }
                GrupoUsuario gu = response.body();
                //Genero mi propio GrupoUsuario
                grupoUsuarioInterface = Login_SignUP.retrofitGrupoUsuario.create(GrupoUsuarioInterface.class);
                Call<GrupoUsuario> call1 = grupoUsuarioInterface.create(new GrupoUsuario(null, new GrupoUsuarioFK(Usuario.getInstance().getId(), infoGrupo.getIdGrupo())));
                call1.enqueue(new Callback<GrupoUsuario>() {
                    @Override
                    public void onResponse(@NonNull Call<GrupoUsuario> call, @NonNull Response<GrupoUsuario> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(ChatActivity.this, "error", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        idGrupoUsuario = response.body().getIdGrupoUsuario();
                        GuardarConversacion();
                    }

                    @Override
                    public void onFailure(Call<GrupoUsuario> call, Throwable t) {
                    }
                });

            }

            @Override
            public void onFailure(Call<GrupoUsuario> call, Throwable t) {
            }
        });
        GuardarConversacion();
    }

    public void cargarConversacion() {
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

    public void cargarUsuarios() {

        grupoUsuarioInterface = Login_SignUP.retrofitGrupoUsuario.create(GrupoUsuarioInterface.class);
        Call<List<Usuario>> callUsers = grupoUsuarioInterface.getGroupUsers(idGrupo);
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

    public void cargarGrupo() {

        grupoInterface = Login_SignUP.retrofitGrupo.create(GrupoInterface.class);
        Call<Grupo> callUsers = grupoInterface.getById(idGrupo);
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

    public void GuardarConversacion() {

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

    public void iconAdd(Boolean gender) {
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
        nombreGrupo.setText(infoGrupo.getNombre().toString());
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

            }
        });
        alertDialog = builder.create();
        alertDialog.show();
    }

    private String generarCodigoDeGrupo() {
        String codigo = "";
        char letra;
        Random rm = new Random();
        for (int y = 0; y < 10; y++) {
            letra = (char) (rm.nextInt(122 - 48 + 1) + 48);
            if (letra == '\\' || letra == ';') {
                y--;
            } else {
                codigo += letra;
            }
        }
        return codigo;
    }
}