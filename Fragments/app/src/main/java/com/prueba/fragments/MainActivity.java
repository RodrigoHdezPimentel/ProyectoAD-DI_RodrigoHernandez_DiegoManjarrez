package com.prueba.fragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.prueba.fragments.Class.AutoLogin;
import com.prueba.fragments.Fragments.MainFragment.Chats;
import com.prueba.fragments.Fragments.MainFragment.Home;
import com.prueba.fragments.Fragments.MainFragment.Profile;
import com.prueba.fragments.Fragments.MainFragment.Publish;
import com.prueba.fragments.RetrofitConnection.Interfaces.ConversacionInterface;
import com.prueba.fragments.RetrofitConnection.Interfaces.FileInterface;
import com.prueba.fragments.RetrofitConnection.Interfaces.FileInterface;
import com.prueba.fragments.RetrofitConnection.Interfaces.GrupoInterface;
import com.prueba.fragments.RetrofitConnection.Interfaces.GrupoUsuarioInterface;
import com.prueba.fragments.RetrofitConnection.Interfaces.LikeInterface;
import com.prueba.fragments.RetrofitConnection.Interfaces.PublicacionInterface;
import com.prueba.fragments.RetrofitConnection.Interfaces.TemaInterface;
import com.prueba.fragments.RetrofitConnection.Interfaces.UsuarioInterface;
import com.prueba.fragments.RetrofitConnection.Interfaces.UsuarioTemaInterface;
import com.prueba.fragments.RetrofitConnection.Models.Like;
import com.prueba.fragments.RetrofitConnection.Models.Usuario;

import java.net.InetAddress;
import java.net.UnknownHostException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    public static Retrofit retrofitPublicacion;
    public static Retrofit retrofitTemas;
    public static Retrofit retrofitUser;
    public static Retrofit retrofitUserTema;
    public static Retrofit retrofitLike;
    public static Retrofit retrofitConversacion;
    public static Retrofit retrofitGrupo;
    public static Retrofit retrofitGrupoUsuario;
    public static Retrofit retrofitFile;
    public static FileInterface fileInterface;
    public static ConversacionInterface conversacionInterface;
    public static GrupoInterface grupoInterface;
    public static GrupoUsuarioInterface grupoUsuarioInterface;
    public static LikeInterface likeInterface;
    public static PublicacionInterface publicacionInterface;
    public static TemaInterface temaInterface;
    public static UsuarioInterface usuarioInterface;
    public static UsuarioTemaInterface usuarioTemaInterface;


    static final String[] IP_DIEGO = {"192.168.56.1","192.168.0.33","10.94.30.45"};
    static final String[] IP_RODRIGO = {"192.168.128.250", "192.168.0.251", "192.168.243.6"};//clase-casa-movil

    public static String IP = IP_DIEGO[0];
    FrameLayout frameLayout;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        retrofitFile = new Retrofit.Builder()
                .baseUrl("http://" + IP +":8086/file/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitPublicacion = new Retrofit.Builder()
                .baseUrl("http://" + IP +":8086/publicacion/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitTemas = new Retrofit.Builder()
                .baseUrl("http://" + IP +":8086/tema/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitUser = new Retrofit.Builder()
                .baseUrl("http://" + IP +":8086/usuario/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitUserTema =  new Retrofit.Builder()
                .baseUrl("http://" + IP +":8086/usuarioTema/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitLike = new Retrofit.Builder()
                .baseUrl("http://" + IP +":8086/like/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitConversacion = new Retrofit.Builder()
                .baseUrl("http://" + IP +":8086/conversacion/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitGrupo = new Retrofit.Builder()
                .baseUrl("http://" + IP +":8086/grupo/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitGrupoUsuario = new Retrofit.Builder()
                .baseUrl("http://" + IP +":8086/grupoUsuario/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        fileInterface = retrofitFile.create(FileInterface.class);
        conversacionInterface = retrofitConversacion.create(ConversacionInterface.class);
        grupoUsuarioInterface = retrofitGrupoUsuario.create(GrupoUsuarioInterface.class);
        grupoInterface = retrofitGrupo.create(GrupoInterface.class);
        likeInterface = retrofitLike.create(LikeInterface.class);
        publicacionInterface = retrofitPublicacion.create(PublicacionInterface.class);
        temaInterface = retrofitTemas.create(TemaInterface.class);
        usuarioInterface = retrofitUser.create(UsuarioInterface.class);
        usuarioTemaInterface = retrofitUserTema.create(UsuarioTemaInterface.class);

        if(Usuario.getInstance().getId() == null){
            if (AutoLogin.getUserName(MainActivity.this).isEmpty()) {
                //Si no hay registro previo, va a login
                Toast.makeText(this, "hola", Toast.LENGTH_SHORT).show();
                Intent toLogin = new Intent(this, Login_SignUP.class);
                startActivity(toLogin);
            } else {
                // Stay at the current activity.
                iniciarSesion();
            }
        }else {
                cargarActivity();
        }

    }

    private void iniciarSesion(){
        Call<Usuario> call = usuarioInterface.getUserRegister(AutoLogin.getUserName(MainActivity.this),AutoLogin.getPassord(MainActivity.this) );
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (!response.isSuccessful()) {
                    Log.e("Response err: fallo en logIn", response.message());
                    return;
                }
                Usuario userData = response.body();
                if (userData != null) {
                    Usuario.setInstance(userData);
                    Usuario.getInstance().setAutoLogin(true);
                    cargarActivity();

                } else {
                    Toast.makeText(MainActivity.this, "Error. Comprueba los datos", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {

            }
        });
    }

    public void cargarActivity() {
        frameLayout = (FrameLayout) findViewById(R.id.frameLayoutMain);
        tabLayout = (TabLayout) findViewById(R.id.MainFragmentManager);
        Intent getFragment = getIntent();

        int fragmentNum = getFragment.getIntExtra("numFrgMain", 0);
        Fragment fragmentMain = null;
        switch (fragmentNum){
            case 0:
                fragmentMain = new Home();
                break;
            case 1:
                fragmentMain = new Publish();
                break;
            case 2:
                fragmentMain = new Chats();
                break;
            case 3:
                fragmentMain = new Profile();
                //Obtenemos el objeto que se envió para ver el perfil.
                fragmentMain.setArguments(getIntent().getBundleExtra("perfilBundle"));
                break;
        }
        getSupportFragmentManager().beginTransaction().add(R.id.frameLayoutMain, fragmentMain)
                .addToBackStack(null)
                .commit();
        TabLayout.Tab tab = tabLayout.getTabAt(fragmentNum);
        tab.select();

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment = null;
                switch (tab.getPosition()){
                    case 0:
                        fragment = new Home();
                        break;
                    case 1:
                        fragment = new Publish();
                        break;
                    case 2:
                        fragment = new Chats();
                        break;
                    case 3:
                        fragment = new Profile();

                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutMain, fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null)
                        .commit();
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    //Volver esto estatico para llamar al metodo en las demás clases
    public static void darLike(Integer idPublicacion){
        Like like = new Like(null, idPublicacion, Usuario.getInstance().getId());
        Call<Like> call = likeInterface.create(like);
        call.enqueue(new Callback<Like>() {
            @Override
            public void onResponse(Call<Like> call, Response<Like> response) {
                if (!response.isSuccessful()) {
                    Log.e("Response err: ", response.message());
                    return;
                }

            }

            @Override
            public void onFailure(Call<Like> call, Throwable t) {

            }
        });
    }
    public static void quitarLike(int idPublicacion){
        Call <Boolean> call = likeInterface.removeLikeUser(idPublicacion, Usuario.getInstance().getId());
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (!response.isSuccessful()) {
                    Log.e("Response err: ", response.message());
                    return;
                }
            }
            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
            }
        });
    }

    public static void addPicture(ImageView imageView, Context contex, String pathFoto){
        Log.d("nombre", "http://localhost:8086/file/image/"+ pathFoto);
            Glide.with(contex).load("http://"+ MainActivity.IP+":8086/file/image/"+pathFoto).fitCenter().error(R.drawable.ic_mujer).into(imageView);
    }
    public String getIPDispositivo() throws UnknownHostException {
        InetAddress localHost = InetAddress.getLocalHost();
        return localHost.getHostAddress().toString();
    }
}