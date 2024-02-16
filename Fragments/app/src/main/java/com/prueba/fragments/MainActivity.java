package com.prueba.fragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.prueba.fragments.Fragments.MainFragment.Chats;
import com.prueba.fragments.Fragments.MainFragment.Home;
import com.prueba.fragments.Fragments.MainFragment.Profile;
import com.prueba.fragments.Fragments.MainFragment.Publish;
import com.prueba.fragments.RetrofitConnection.Interfaces.PublicacionInterface;
import com.prueba.fragments.RetrofitConnection.Interfaces.TemaInterface;
import com.prueba.fragments.RetrofitConnection.Models.Chat;
import com.prueba.fragments.RetrofitConnection.Models.Publicacion;
import com.prueba.fragments.RetrofitConnection.Models.Tema;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    public static Retrofit retrofitPublicacion;
    public static Retrofit retrofitTemas;
    public static Retrofit retrofitUser;
    FrameLayout frameLayout;
    TabLayout tabLayout;

    public static ArrayList<Chat> listaChats = new ArrayList<>();
    public static ArrayList<String> listaTemas = new ArrayList<>();
    public static ArrayList<Chat> chatConversation = new ArrayList<>();
    //ID DEL USAURIO QUE ENTRA EN SESION
    public static int idRegistrado = 1;
    public static final String IP_DIEGO = "192.168.56.1";
    public static final String IP_RODRIGO = "172.29.144.1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        retrofitPublicacion = new Retrofit.Builder()
                .baseUrl("http://" + IP_DIEGO +":8086/publicacion/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitTemas = new Retrofit.Builder()
                .baseUrl("http://" + IP_DIEGO +":8086/tema/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitUser = new Retrofit.Builder()
                .baseUrl("http://" + IP_DIEGO +":8086/usuario/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        frameLayout = (FrameLayout) findViewById(R.id.frameLayoutMain);
        tabLayout = (TabLayout) findViewById(R.id.MainFragmentManager);

        chatConversation.removeAll(chatConversation);
        chatConversation.add(new Chat(null,1,2,"hola","fecha"));
        chatConversation.add(new Chat(null,2,1,"hola, ¿que tal?","fecha"));
        chatConversation.add(new Chat(null,1,2,"Bien, y tu?","fecha"));
        chatConversation.add(new Chat(null,2,1,"A mi se me ha muerto el perro","fecha"));
        chatConversation.add(new Chat(null,1,2,"jajajaja","fecha"));
        chatConversation.add(new Chat(null,1,2,"perdon, chat equivocado","fecha"));
        chatConversation.add(new Chat(null,1,2,"ppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppp","fecha"));

        listaChats.removeAll(listaChats);
        listaChats.add(new Chat(null,2,1,"ppppppppppppppppp", "fecha"));
        getTemas();

        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutMain, new Home())
                .addToBackStack(null)
                .commit();

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
    public void getTemas(){
        TemaInterface temaInterface = retrofitTemas.create(TemaInterface.class);
        Call<List<Tema>> call = temaInterface.getAll();
        call.enqueue(new Callback<List<Tema>>() {

            @Override
            public void onResponse(Call<List<Tema>> call, Response<List<Tema>> response) {
                if (!response.isSuccessful()) {
                    Log.e("Response err: ", response.message());
                    return;
                }
                List<Tema> temporalList = response.body();
                for(Tema t : temporalList){
                    listaTemas.add(t.getTitulo());
                }
                return;
            }

            @Override
            public void onFailure(Call<List<Tema>> call, Throwable t) {

            }
        });
    }

}