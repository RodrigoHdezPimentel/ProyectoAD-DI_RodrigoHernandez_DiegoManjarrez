package com.prueba.fragments.Fragments.MainFragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.prueba.fragments.ChatActivity;
import com.prueba.fragments.Class.ChatListUser;
import com.prueba.fragments.MainActivity;
import com.prueba.fragments.R;
import com.prueba.fragments.RecyclerViews.Adapters.ListaChatsRvAdapter;
import com.prueba.fragments.RetrofitConnection.Models.Grupo;
import com.prueba.fragments.RetrofitConnection.Models.GrupoUsuario;
import com.prueba.fragments.RetrofitConnection.Models.GrupoUsuarioFK;
import com.prueba.fragments.RetrofitConnection.Models.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Chats extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Chats() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Chats newInstance(String param1, String param2) {
        Chats fragment = new Chats();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    View view;
    //ArrayList<ChatListUser> ListaGrupos = new ArrayList<>();
    ProgressBar progressBar;
    FloatingActionButton newGroup;
    TextInputEditText codigo;
    TextView error;
    Grupo grupoDestino;
    GrupoUsuario grupoUsuarioChatDestino;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chats, container, false);
        progressBar = view.findViewById(R.id.progressBar);
        newGroup = view.findViewById(R.id.newGroup);
        grupoUsuarioChatDestino = null;

        newGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });
        cargarGrupos();
        return  view;
    }
    public void crearConversacion(){
        //Se crea primero el grupo y luego se asigna el user al grupo. (debido al spring xd)
        Call<Grupo> call = MainActivity.grupoInterface.create(new Grupo(null,"ic_grupo_app.jpg",generarCodigoDeGrupo()));
        call.enqueue(new Callback<Grupo>() {
            @Override
            public void onResponse(Call<Grupo> call, Response<Grupo> response) {
                if(!response.isSuccessful()){
                    return;
                }
                Toast.makeText(getContext(), response.body().getIdGrupo()+"id Grupo nuevo", Toast.LENGTH_SHORT).show();
                //Ahora Asignamos los usuarios al grupo para chatear
                asignarChat(response.body().getIdGrupo());
            }
            @Override
            public void onFailure(Call<Grupo> call, Throwable t) {

            }
        });
    }

    public void asignarChat(int idGrupo){
        //Primero nos asignamos al grupo
        Call<GrupoUsuario> call = MainActivity.grupoUsuarioInterface.create(new GrupoUsuario(null,"Nuevo grupo", null,
                new GrupoUsuarioFK(Usuario.getInstance().getId(), idGrupo)));
        call.enqueue(new Callback<GrupoUsuario>() {
            @Override
            public void onResponse(Call<GrupoUsuario> call, Response<GrupoUsuario> response1) {
                if(!response1.isSuccessful()){
                    return;
                }

                Toast.makeText(getContext(), "CREADO CON EXITO DE CHAT ", Toast.LENGTH_SHORT).show();

                //enviamos los datos al ChatActivity
                Intent toChat = new Intent(getContext(), ChatActivity.class);
                toChat.putExtra("foto",response1.body().getGrupoUsuarioFK().getGrupo().getFoto());
                toChat.putExtra("idGrupo", idGrupo);
                toChat.putExtra("idGrupoUsuario", response1.body().getIdGrupoUsuario());
                startActivity(toChat);

            }
            @Override
            public void onFailure(Call<GrupoUsuario> call, Throwable t) {

            }
        });
    }

    public void cargarGrupos(){
       Call<List<ChatListUser>> call = MainActivity.grupoUsuarioInterface.getListChatFromUser(Usuario.getInstance().getId());
       call.enqueue(new Callback<List<ChatListUser>>() {
           @Override
           public void onResponse(Call<List<ChatListUser>> call, Response<List<ChatListUser>> response) {
               if(!response.isSuccessful()){
                   return;
               }
               progressBar.setVisibility(View.GONE);
               RecyclerView MyRecyclerView = view.findViewById(R.id.ChatsListRecyclerView);
               ListaChatsRvAdapter adapter = new ListaChatsRvAdapter(getContext(),(ArrayList<ChatListUser>) response.body());
               MyRecyclerView.setAdapter(adapter);
               MyRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
           }
           @Override
           public void onFailure(Call<List<ChatListUser>> call, Throwable t) {

           }
       });
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

    public void showAlertDialog() {
        final Dialog dialog = new Dialog(getContext());

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_create_join_group);

        ImageView cancellButton = dialog.findViewById(R.id.dimissAD);
        //Crea un grupo nuevo contigo solo
        Button createGroup = dialog.findViewById(R.id.createGroupBut);
        //Te une al grupo del codigo
        Button joinGroup = dialog.findViewById(R.id.joinGroupBut);
        error = dialog.findViewById(R.id.mensajeError);
        codigo = dialog.findViewById(R.id.groupCodeInput);
        error.setVisibility(View.INVISIBLE);

        cancellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        createGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearConversacion();
                dialog.dismiss();
            }
        });
        joinGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(codigo.getText().toString().equals("")){
                    error.setVisibility(View.VISIBLE);
                    error.setText("Codigo obligatorio");
                }else{
                    error.setVisibility(View.INVISIBLE);
                    findGroup();
                }
            }
        });

        // Muestra el diálogo
        dialog.show();
    }
    //Si no ha estado antes crea su grupoUsuario
    public void joinGrupo() {
        GrupoUsuario newGrupoUsuario =
                new GrupoUsuario(null, "Nombre Grupo", null,
                    new GrupoUsuarioFK(Usuario.getInstance().getId(), grupoDestino.getIdGrupo()));
        Call<GrupoUsuario> call = MainActivity.grupoUsuarioInterface.create(newGrupoUsuario);
        call.enqueue(new Callback<GrupoUsuario>() {
            @Override
            public void onResponse(Call<GrupoUsuario> call, Response<GrupoUsuario> response) {
                if(!response.isSuccessful()){
                    return;
                }
                Toast.makeText(getContext(), "GRUPOUSUARIO", Toast.LENGTH_SHORT).show();
                grupoUsuarioChatDestino = response.body();
                Intent toChat = new Intent(getContext(), ChatActivity.class);
                toChat.putExtra("idGrupo", grupoDestino.getIdGrupo());
                toChat.putExtra("idGrupoUsuario", grupoUsuarioChatDestino.getIdGrupoUsuario());
                startActivity(toChat);
            }
            @Override
            public void onFailure(Call<GrupoUsuario> call, Throwable t) {
            }
        });
    }
    //Si ya a estado antes, cambia la fecha baja a null
    public void rejoinGrupo(){
        Call<GrupoUsuario> call = MainActivity.grupoUsuarioInterface.rejoinGroup(grupoUsuarioChatDestino.getIdGrupoUsuario());
        call.enqueue(new Callback<GrupoUsuario>() {
            @Override
            public void onResponse(Call<GrupoUsuario> call, Response<GrupoUsuario> response) {
                if(!response.isSuccessful()){
                    return;
                }
                Toast.makeText(getContext(), "GRUPOUSUARIO", Toast.LENGTH_SHORT).show();
                Intent toChat = new Intent(getContext(), ChatActivity.class);
                toChat.putExtra("idGrupo", grupoDestino.getIdGrupo());
                toChat.putExtra("idGrupoUsuario", grupoUsuarioChatDestino.getIdGrupoUsuario());
                startActivity(toChat);
            }
            @Override
            public void onFailure(Call<GrupoUsuario> call, Throwable t) {
            }
        });
    }

    //Busca si ya a estado en ese grupo
    public void searchGrupoUsuario(){
        Call<GrupoUsuario> call = MainActivity.grupoUsuarioInterface.findByIdUserIdGroup(Usuario.getInstance().getId(), grupoDestino.getIdGrupo());
        call.enqueue(new Callback<GrupoUsuario>() {
            @Override
            public void onResponse(Call<GrupoUsuario> call, Response<GrupoUsuario> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(), "Grupo no encontrado", Toast.LENGTH_SHORT).show();
                    return;
                }
                grupoUsuarioChatDestino = response.body();
                //Cambia la fechabaja por null
                rejoinGrupo();
            }
            @Override
            public void onFailure(Call<GrupoUsuario> call, Throwable t) {
                //Si no hace una conexion exitosa es pq no hay registro,
                //asi que hay que crear el grupousuario
                if(grupoUsuarioChatDestino == null){
                    joinGrupo();
                }
            }
        });

    }
    //Comprueba que el grupo existe
    public void findGroup(){
        Call<Grupo> call = MainActivity.grupoInterface.findGroup(codigo.getText().toString());
        call.enqueue(new Callback<Grupo>() {
            @Override
            public void onResponse(Call<Grupo> call, Response<Grupo> response) {
                if(!response.isSuccessful()){
                    error.setVisibility(View.VISIBLE);
                    error.setText("Grupo no encontrado");
                    return;
                }
                Toast.makeText(getContext(), "GRUPO", Toast.LENGTH_SHORT).show();
                grupoDestino = response.body();

                error.setVisibility(View.INVISIBLE);
                codigo.setText("");
                searchGrupoUsuario();
            }
            @Override
            public void onFailure(Call<Grupo> call, Throwable t) {

            }
        });
    }
}