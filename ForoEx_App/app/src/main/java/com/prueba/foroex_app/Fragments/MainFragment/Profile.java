package com.prueba.foroex_app.Fragments.MainFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;
import com.prueba.foroex_app.ChatActivity;
import com.prueba.foroex_app.EditProfile;
import com.prueba.foroex_app.Fragments.ProfileFragment.misLikes;
import com.prueba.foroex_app.Fragments.ProfileFragment.misPublicaciones;
import com.prueba.foroex_app.MainActivity;
import com.prueba.foroex_app.R;
import com.prueba.foroex_app.RetrofitConnection.Models.Grupo;
import com.prueba.foroex_app.RetrofitConnection.Models.GrupoUsuario;
import com.prueba.foroex_app.RetrofitConnection.Models.GrupoUsuarioFK;
import com.prueba.foroex_app.RetrofitConnection.Models.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile extends Fragment {
    FrameLayout frameLayout;
    TabLayout tabLayout;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public Profile() {
    }

    public static Profile newInstance(String param1, String param2) {
        Profile fragment = new Profile();
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
    TextView userName;
    TextView descripcion;
    ImageView iconProfile;
    ImageView iconMyProfile;
    boolean viewUser;
    Button editProfile;
    //por default va a ser el usuario resgitrado
    public static Usuario perfil;
    ImageView iconEnviarMensaje;
    Boolean chatNuevo = true;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        iconEnviarMensaje = view.findViewById(R.id.icon_enviar_mensaje);
        editProfile = view.findViewById(R.id.updateProfile);
        userName = view.findViewById(R.id.UserNameProfile);
        iconProfile = view.findViewById(R.id.iconFragmentProfile);
        iconMyProfile = view.findViewById(R.id.toMyProfile);
        descripcion = view.findViewById(R.id.descripcionProfile);

        cargarPerfil();
        enviarMensaje();

        //Coloco las fotos de perfil
        MainActivity.addPicture(iconMyProfile, getContext(),Usuario.getInstance().getFoto());
        MainActivity.addPicture(iconProfile, getContext(),Profile.perfil.getFoto());

        userName.setText(perfil.getName());
        descripcion.setText(perfil.getDescripcion());

        if(!viewUser){
            editProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent toEditProfile = new Intent(getActivity(), EditProfile.class);
                    startActivity(toEditProfile);
                }
            });
        }else {

            toMyProfile();
        }


        frameLayout = (FrameLayout) view.findViewById(R.id.frameLayoutProfile);
        tabLayout = (TabLayout) view.findViewById(R.id.ProfileFragmentManager);

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutProfile, new misPublicaciones())
                .addToBackStack(null)
                .commit();

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment = null;
                switch (tab.getPosition()){
                    case 0:
                        fragment = new misPublicaciones();
                        break;
                    case 1:
                        fragment = new misLikes();
                        break;
                }

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutProfile, fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return view;
    }


    public void cargarPerfil(){

        //Recibir el objeto Usuario desde otras fragments o activitis
        Bundle args = getArguments();
        if (args != null) {
            perfil = (Usuario) args.getSerializable("perfil");

            if(perfil.getId().equals(Usuario.getInstance().getId())){
                iconMyProfile.setVisibility(View.INVISIBLE);
                iconEnviarMensaje.setVisibility(View.INVISIBLE);

            }else {
                viewUser = true;
                editProfile.setVisibility(View.INVISIBLE);

            }
        }else {
            perfil = Usuario.getInstance();
            iconMyProfile.setVisibility(View.INVISIBLE);
            iconEnviarMensaje.setVisibility(View.INVISIBLE);
        }
    }
    //Metodo para ir nuestro perfil
    public void toMyProfile(){
        iconMyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toMain = new Intent(getContext(), MainActivity.class);
                toMain.putExtra("numFrgMain", 3);
                startActivity(toMain);
            }
        });
    }


    public void enviarMensaje(){
        iconEnviarMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<List<List<Integer>>> call = MainActivity.grupoUsuarioInterface.getLoadChat(Usuario.getInstance().getId(), perfil.getId());
                call.enqueue(new Callback<List<List<Integer>>>() {
                    @Override
                    public void onResponse(Call<List<List<Integer>>> call, Response<List<List<Integer>>> response) {
                        if(!response.isSuccessful()){
                            return;
                        }
                        if(response.body().isEmpty()){
                            crearConversacion();
                        }else {
                            Intent toChat = new Intent(getContext(), ChatActivity.class);
                            toChat.putExtra("foto",perfil.getFoto());
                            toChat.putExtra("idGrupo", response.body().get(0).get(0));
                            toChat.putExtra("idGrupoUsuario",response.body().get(0).get(1));
                            startActivity(toChat);
                        }

                    }

                    @Override
                    public void onFailure(Call<List<List<Integer>>> call, Throwable t) {

                    }
                });
            }
        });
    }

    public void crearConversacion(){
        //Se crea primero el grupo y luego se asigna el user al grupo. (debido al spring xd)
        Call<Grupo> call =  MainActivity.grupoInterface.create(new Grupo(null,"ic_grupo_app.png",null));
        call.enqueue(new Callback<Grupo>() {
            @Override
            public void onResponse(Call<Grupo> call, Response<Grupo> response) {
                if(!response.isSuccessful()){
                    return;
                }
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
        Call<GrupoUsuario> call = MainActivity.grupoUsuarioInterface.create(new GrupoUsuario(null, perfil.getName(), null,
                new GrupoUsuarioFK(Usuario.getInstance().getId(), idGrupo,Usuario.getInstance(),grupo)));
        call.enqueue(new Callback<GrupoUsuario>() {
            @Override
            public void onResponse(Call<GrupoUsuario> call, Response<GrupoUsuario> response1) {
                if(!response1.isSuccessful()){
                    return;
                }

                //Después asignamos el grupo al otro
                Call <GrupoUsuario> call1 = MainActivity.grupoUsuarioInterface.create(new GrupoUsuario(null, Usuario.getInstance().getName(), null, new GrupoUsuarioFK(perfil.getId(), idGrupo,perfil,grupo)));
                call1.enqueue(new Callback<GrupoUsuario>() {
                    @Override
                    public void onResponse(Call<GrupoUsuario> call, Response<GrupoUsuario> response) {
                        if(!response.isSuccessful()){
                            return;
                        }
                        Toast.makeText(getContext(), "CREADO CON EXITO DE CHAT ", Toast.LENGTH_SHORT).show();

                        //enviamos los datos al ChatActivity
                    Intent toChat = new Intent(getContext(),ChatActivity.class);
                    toChat.putExtra("foto",perfil.getFoto());
                    toChat.putExtra("idGrupo", idGrupo);
                    toChat.putExtra("idGrupoUsuario", response1.body().getIdGrupoUsuario());
                    startActivity(toChat);
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
    }

}