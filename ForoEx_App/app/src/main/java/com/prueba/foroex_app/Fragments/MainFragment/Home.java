package com.prueba.foroex_app.Fragments.MainFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;
import com.prueba.foroex_app.Fragments.HomeFragment.MisTemas;
import com.prueba.foroex_app.Fragments.HomeFragment.Tendencias;
import com.prueba.foroex_app.R;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View root = inflater.inflate(R.layout.fragment_home, container, false);

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

}