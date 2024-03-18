package com.prueba.fragments.Fragments.MainFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.prueba.fragments.ChatActivity;
import com.prueba.fragments.Class.ChatLastMessage;
import com.prueba.fragments.Login_SignUP;
import com.prueba.fragments.MainActivity;
import com.prueba.fragments.R;
import com.prueba.fragments.RecyclerViews.Adapters.ListaChatsRvAdapter;
import com.prueba.fragments.RetrofitConnection.Interfaces.GrupoInterface;
import com.prueba.fragments.RetrofitConnection.Interfaces.GrupoUsuarioInterface;
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
    //ArrayList<ChatLastMessage> ListaGrupos = new ArrayList<>();
    ProgressBar progressBar;
    FloatingActionButton newGroup;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chats, container, false);
        progressBar = view.findViewById(R.id.progressBar);
        newGroup = view.findViewById(R.id.newGroup);

        newGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearConversacion();
            }
        });
        cargarGrupos();
        return  view;
    }
    public void crearConversacion(){
        //Se crea primero el grupo y luego se asigna el user al grupo. (debido al spring xd)
        Call<Grupo> call = MainActivity.grupoInterface.create(new Grupo(null,"Ruta",generarCodigoDeGrupo()));
        call.enqueue(new Callback<Grupo>() {
            @Override
            public void onResponse(Call<Grupo> call, Response<Grupo> response) {
                if(!response.isSuccessful()){
                    return;
                }
                Toast.makeText(getContext(), response.body().getIdGrupo()+"id Grupo nuevo", Toast.LENGTH_SHORT).show();
                //Ahora Asignamos los usuarios al grupo para chatear
                asignarChat(response.body().getIdGrupo(),response.body());
            }
            @Override
            public void onFailure(Call<Grupo> call, Throwable t) {

            }
        });
    }
    public void asignarChat(int idGrupo, Grupo grupo){
        //Primero nos asignamos al grupo
        Call<GrupoUsuario> call = MainActivity.grupoUsuarioInterface.create(new GrupoUsuario(null,"Nuevo grupo",
                new GrupoUsuarioFK(Usuario.getInstance().getId(), idGrupo,Usuario.getInstance(),grupo)));
        call.enqueue(new Callback<GrupoUsuario>() {
            @Override
            public void onResponse(Call<GrupoUsuario> call, Response<GrupoUsuario> response1) {
                if(!response1.isSuccessful()){
                    return;
                }

                Toast.makeText(getContext(), "CREADO CON EXITO DE CHAT ", Toast.LENGTH_SHORT).show();

                //enviamos los datos al ChatActivity
                Intent toChat = new Intent(getContext(), ChatActivity.class);
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
        Call<List<ChatLastMessage>> call = MainActivity.grupoUsuarioInterface.getListChatFromUser(Usuario.getInstance().getId());
       call.enqueue(new Callback<List<ChatLastMessage>>() {
           @Override
           public void onResponse(Call<List<ChatLastMessage>> call, Response<List<ChatLastMessage>> response) {
               if(!response.isSuccessful()){
                   return;
               }
               //ListaGrupos = (ArrayList<ChatLastMessage>) response.body();
               progressBar.setVisibility(View.GONE);

               // Inflate the layout for this fragment
               RecyclerView MyRecyclerView = view.findViewById(R.id.ChatsListRecyclerView);

               ListaChatsRvAdapter adapter = new ListaChatsRvAdapter(getContext(), (ArrayList<ChatLastMessage>) response.body());
               MyRecyclerView.setAdapter(adapter);
               MyRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

           }

           @Override
           public void onFailure(Call<List<ChatLastMessage>> call, Throwable t) {

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
}