package com.prueba.fragments.Fragments.MainFragment;

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
import com.prueba.fragments.ChatActivity;
import com.prueba.fragments.EditProfile;
import com.prueba.fragments.Fragments.ProfileFragment.misLikes;
import com.prueba.fragments.Fragments.ProfileFragment.misPublicaciones;
import com.prueba.fragments.Login_SignUP;
import com.prueba.fragments.MainActivity;
import com.prueba.fragments.R;
import com.prueba.fragments.RetrofitConnection.Interfaces.GrupoInterface;
import com.prueba.fragments.RetrofitConnection.Interfaces.GrupoUsuarioInterface;
import com.prueba.fragments.RetrofitConnection.Models.Grupo;
import com.prueba.fragments.RetrofitConnection.Models.GrupoUsuario;
import com.prueba.fragments.RetrofitConnection.Models.GrupoUsuarioFK;
import com.prueba.fragments.RetrofitConnection.Models.Usuario;

import java.util.List;
import java.util.Random;

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
    ImageView iconEnciarMensaje;
    Boolean chatNuevo = true;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        iconEnciarMensaje = view.findViewById(R.id.icon_enviar_mensaje);
        editProfile = view.findViewById(R.id.updateProfile);
        userName = view.findViewById(R.id.UserNameProfile);
        iconProfile = view.findViewById(R.id.iconFragmentProfile);
        iconMyProfile = view.findViewById(R.id.toMyProfile);
        descripcion = view.findViewById(R.id.descripcionProfile);

        cargarPerfil();
        iconAdd();
        toMyProfile();
        enviarMensaje();



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

    public void iconAdd(){
        if(Profile.perfil.getGenero() == null){
            iconProfile.setImageResource(R.drawable.ic_app);
        }else{
            if(!Profile.perfil.getGenero()){
                iconProfile.setImageResource(R.drawable.ic_mujer);
            } else {
                iconProfile.setImageResource(R.drawable.ic_hombre);
            }
        }
    }
    public void cargarPerfil(){
        //ecibir el objeto Usuario desde otras fragments o activitis
        Bundle args = getArguments();
        if (args != null) {
            perfil = (Usuario) args.getSerializable("perfil");
            viewUser = true;
            editProfile.setVisibility(View.INVISIBLE);
        }else {
            perfil = Usuario.getInstance();
            iconMyProfile.setVisibility(View.INVISIBLE);
            iconEnciarMensaje.setVisibility(View.INVISIBLE);
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
        iconEnciarMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toChat = new Intent(getContext(), ChatActivity.class);
                //Se saca los idGrupos (Integer) en común del usuario presente y al usario que queremos enviar el mensaje
                Call <List<List<Integer>>> call = MainActivity.grupoUsuarioInterface.getCommonGroups(Usuario.getInstance().getId(), perfil.getId());
                call.enqueue(new Callback<List<List<Integer>>>() {
                    @Override
                    public void onResponse(Call<List<List<Integer>>> call, Response<List<List<Integer>>> response1) {
                        if(!response1.isSuccessful()){
                            return;
                        }

                        //Ahora nos encargamos de verificar si el numero de miembros del grupo son 2 (yo y él)
                        for (int i = 0; i < response1.body().size() ; i++ ) {
                           // Toast.makeText(getContext(), chatNuevo.toString()+"", Toast.LENGTH_SHORT).show();
                            //La primera posicion es la lista de idGrupo y idGrupoUsuario, y el otro .get()
                            //es acceder a esos valores.

                            Call <Boolean> call2 = MainActivity.grupoUsuarioInterface.getNumberUsers(response1.body().get(i).get(0));
                            int finalI = i;

                            call2.enqueue(new Callback<Boolean>() {
                                @Override
                                public void onResponse(Call<Boolean> call, Response<Boolean> response2) {
                                    if(!response2.isSuccessful()){
                                        return;
                                    }
                                    //REALIZAR LA VERIFICACION SI NUMERO DE MIEMBROS ES 2
                                    if (response2.body() || !response2.body()) {
                                        if(response2.body()){
                                            Toast.makeText(getContext(), "CARGAR EL CHAT ANTERIOR", Toast.LENGTH_SHORT).show();
                                            //enviamos los datos a ChatActivity para cargar la connversacion
                                        toChat.putExtra("idGrupo", response1.body().get(finalI).get(0));
                                        toChat.putExtra("idGrupoUsuario", response1.body().get(finalI).get(1));
                                        startActivity(toChat);
                                        }

                                        if(!response2.body()&& ((finalI+1)==response1.body().size())){
                                            Toast.makeText(getContext(), "Crear chat", Toast.LENGTH_SHORT).show();
                                            crearConversacion();
                                        }
                                    }
                                }
                                @Override
                                public void onFailure(Call<Boolean> call, Throwable t) {

                                }
                            });
                        }
                    }
                    @Override
                    public void onFailure(Call<List<List<Integer>>> call, Throwable t) {}});
            }
        });
    }

    public void crearConversacion(){
        //Se crea primero el grupo y luego se asigna el user al grupo. (debido al spring xd)
        GrupoInterface grupoInterface = MainActivity.retrofitGrupo.create(GrupoInterface.class);
        Call<Grupo> call = grupoInterface.create(new Grupo(null,"Ruta",generarCodigoDeGrupo()));
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
        Call<GrupoUsuario> call = MainActivity.grupoUsuarioInterface.create(new GrupoUsuario(null, perfil.getName(),
                new GrupoUsuarioFK(Usuario.getInstance().getId(), idGrupo,Usuario.getInstance(),grupo)));
        call.enqueue(new Callback<GrupoUsuario>() {
            @Override
            public void onResponse(Call<GrupoUsuario> call, Response<GrupoUsuario> response1) {
                if(!response1.isSuccessful()){
                    return;
                }

                //Después asignamos el grupo al otro
                Call <GrupoUsuario> call1 = MainActivity.grupoUsuarioInterface.create(new GrupoUsuario(null, Usuario.getInstance().getName(), new GrupoUsuarioFK(perfil.getId(), idGrupo,perfil,grupo)));
                call1.enqueue(new Callback<GrupoUsuario>() {
                    @Override
                    public void onResponse(Call<GrupoUsuario> call, Response<GrupoUsuario> response) {
                        if(!response.isSuccessful()){
                            return;
                        }
                        Toast.makeText(getContext(), "CREADO CON EXITO DE CHAT ", Toast.LENGTH_SHORT).show();

                        //enviamos los datos al ChatActivity
                    Intent toChat = new Intent(getContext(),ChatActivity.class);
//                        Toast.makeText(getContext(), idGrupo+"", Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getContext(), response.body().getIdGrupoUsuario()+"", Toast.LENGTH_SHORT).show();
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