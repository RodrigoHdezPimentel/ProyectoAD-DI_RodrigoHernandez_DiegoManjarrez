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

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;
import com.prueba.fragments.EditProfile;
import com.prueba.fragments.Fragments.ProfileFragment.misLikes;
import com.prueba.fragments.Fragments.ProfileFragment.misPublicaciones;
import com.prueba.fragments.R;
import com.prueba.fragments.RetrofitConnection.Models.Usuario;

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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        userName = view.findViewById(R.id.UserNameProfile);
        iconProfile = view.findViewById(R.id.iconFragmentProfile);
        descripcion = view.findViewById(R.id.descripcionProfile);
        userName.setText(Usuario.getInstance().getName());
        descripcion.setText(Usuario.getInstance().getDescripcion());

        iconAdd();
        Button editProfile = view.findViewById(R.id.updateProfile);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toEditProfile = new Intent(getActivity(), EditProfile.class);
                startActivity(toEditProfile);
            }
        });

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
        if(Usuario.getInstance().getGenero().equals("Female")){
            iconProfile.setImageResource(R.drawable.ic_mujer);
        } else if (Usuario.getInstance().getGenero().equals("Male")) {
            iconProfile.setImageResource(R.drawable.ic_hombre);
        }else {
            iconProfile.setImageResource(R.drawable.ic_app);
        }
    }
}