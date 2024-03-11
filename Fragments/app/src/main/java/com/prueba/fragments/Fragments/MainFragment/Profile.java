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
import com.prueba.fragments.RetrofitConnection.Interfaces.ConversacionInterface;
import com.prueba.fragments.RetrofitConnection.Interfaces.GrupoUsuarioInterface;
import com.prueba.fragments.RetrofitConnection.Models.Conversacion;
import com.prueba.fragments.RetrofitConnection.Models.GrupoUsuario;
import com.prueba.fragments.RetrofitConnection.Models.Usuario;

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
    ImageView iconEnciarMensaje;
    GrupoUsuarioInterface grupoUsuarioInterface;
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
        if(!Profile.perfil.getGenero()){
            iconProfile.setImageResource(R.drawable.ic_mujer);
        } else if (Profile.perfil.getGenero()) {
            iconProfile.setImageResource(R.drawable.ic_hombre);
        }else {
            iconProfile.setImageResource(R.drawable.ic_app);
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
                grupoUsuarioInterface = Login_SignUP.retrofitGrupoUsuario.create(GrupoUsuarioInterface.class);
                //Se saca los idGrupos (Integer) en común del usuario presente y al usario que queremos enviar el mensaje

                Call <List<List<Integer>>> call = grupoUsuarioInterface.getCommonGroups(Usuario.getInstance().getId(), perfil.getId());
                call.enqueue(new Callback<List<List<Integer>>>() {
                    @Override
                    public void onResponse(Call<List<List<Integer>>> call, Response<List<List<Integer>>> response) {
                        if(!response.isSuccessful()){
                            return;
                        }

                        //Ahora nos encargamos de verificar si el numero de miembros del grupo son 2 (yo y él)
                        for (int i = 0; i < response.body().size(); i++ ) {
                            //La primera posicion es la lista de idGrupo y idGrupoUsuario, y el otro .get()
                            //es acceder a esos valores.
                            if (verificacion(response.body().get(i).get(0))) {

                                //enviamos los datos a ChatActivity para cargar la connversacion
                                toChat.putExtra("idGrupo", response.body().get(i).get(0));
                                toChat.putExtra("idGrupoUsuario", response.body().get(i).get(1));
                                startActivity(toChat);
                                break;
                            }
                        }
                        //Creamos el grupo con esa persona en caso de no haber un "chat"
                        crearConversacion();


                    }
                    @Override
                    public void onFailure(Call<List<List<Integer>>> call, Throwable t) {}});


            }
        });
    }
    public Boolean verificacion(Integer idGrupo){
        final Boolean[] respuesta = {false};

        Call <Boolean> call2 = grupoUsuarioInterface.getNumberUsers(idGrupo);
        call2.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(!response.isSuccessful()){
                    return;
                }
                respuesta[0] = response.body();
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });
        return respuesta[0];
    }
    public void crearConversacion(){
        ConversacionInterface conversacionInterface = Login_SignUP.retrofitConversacion.create(ConversacionInterface.class);
       // Call <Conversacion> call = conversacionInterface.save()
    }
}