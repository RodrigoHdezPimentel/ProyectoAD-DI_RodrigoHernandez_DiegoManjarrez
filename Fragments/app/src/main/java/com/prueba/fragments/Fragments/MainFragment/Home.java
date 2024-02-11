package com.prueba.fragments.Fragments.MainFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;
import com.prueba.fragments.Fragments.HomeFragment.MisTemas;
import com.prueba.fragments.Fragments.HomeFragment.Tendencias;
import com.prueba.fragments.R;
import com.prueba.fragments.RetrofitConnection.Interfaces.PublicacionInterface;
import com.prueba.fragments.RetrofitConnection.Interfaces.UsuarioInterface;
import com.prueba.fragments.RetrofitConnection.Models.Publicacion;

import java.util.List;

import retrofit2.Call;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Home extends Fragment {

    TabLayout tabLayout;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Home() {

    }

    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
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
    ///////VARIABLES/////////
    public static List<Publicacion> listaPublicaciones;
    PublicacionInterface publicacionInterface;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        getAllPubliacion();
        tabLayout = root.findViewById(R.id.homeFragmentManager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                switch (tab.getPosition()) {
                    case 0:
                        selectedFragment(new MisTemas());
                        break;
                    case 1:
                        selectedFragment(new Tendencias());
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //Acá se puede recargar los "datos" cuando se vuelva a seleccionar el mismo fragment
            }
        });

        return root;
    }


    void selectedFragment(Fragment fr){
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentView, fr);
        fragmentTransaction.commit();
    }
    private void getAllPubliacion(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.56.1:8086/publicacion/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
       publicacionInterface  = retrofit.create(PublicacionInterface.class);
        Call<List<com.prueba.fragments.RetrofitConnection.Models.Publicacion>> call = publicacionInterface.getAll();
        call.enqueue(new Callback<List<Publicacion>>() {
            @Override
            public void onResponse(Call<List<Publicacion>> call, Response<List<Publicacion>> response) {
                if(!response.isSuccessful()){
                    Log.e("Response err: ", response.message());
                    return;
                }
                listaPublicaciones = response.body();
                listaPublicaciones.forEach(u -> Log.i("publicacion err: ", u.toString()));

            }

            @Override
            public void onFailure(Call<List<Publicacion>> call, Throwable t) {
                Log.e("Thorw err: ", t.getMessage());

            }
        });

    }

}