package com.prueba.fragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.google.android.material.tabs.TabLayout;
import com.prueba.fragments.Fragments.MainFragment.Chats;
import com.prueba.fragments.Fragments.MainFragment.Home;
import com.prueba.fragments.Fragments.MainFragment.Profile;
import com.prueba.fragments.Fragments.MainFragment.Publish;
import com.prueba.fragments.RecyclerViews.Models.Chat;
import com.prueba.fragments.RecyclerViews.Models.Publicacion;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    FrameLayout frameLayout;
    TabLayout tabLayout;

    public static ArrayList<Chat> listaChats = new ArrayList<>();
    public static ArrayList<Chat> chatConversation = new ArrayList<>();
    public static int idRegistrado = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frameLayout = (FrameLayout) findViewById(R.id.frameLayoutMain);
        tabLayout = (TabLayout) findViewById(R.id.MainFragmentManager);

        chatConversation.add(new Chat(1,2,"hola","fecha"));
        chatConversation.add(new Chat(2,1,"hola, ¿que tal?","fecha"));
        chatConversation.add(new Chat(1,2,"Bien, y tu?","fecha"));
        chatConversation.add(new Chat(2,1,"A mi se me ha muerto el perro","fecha"));
        chatConversation.add(new Chat(1,2,"jajajaja","fecha"));
        chatConversation.add(new Chat(1,2,"perdon, chat equivocado","fecha"));
        chatConversation.add(new Chat(1,2,"ppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppp","fecha"));

        listaChats.add(new Chat(2,1,"ppppppppppppppppp", "fecha"));

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

}