package com.prueba.fragments.Fragments.HomeFragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.prueba.fragments.Fragments.MainFragment.Home;
import com.prueba.fragments.Login_SignUP;
import com.prueba.fragments.MainActivity;
import com.prueba.fragments.R;
import com.prueba.fragments.RecyclerViews.Adapters.PublicacionRvAdapter;
import com.prueba.fragments.RecyclerViews.Models.Publicacion;

import org.jboss.jandex.Main;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Tendencias#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tendencias extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Tendencias() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tendencias.
     */
    // TODO: Rename and change types and number of parameters
    public static Tendencias newInstance(String param1, String param2) {
        Tendencias fragment = new Tendencias();
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
    //Cambiar esto por los datos retornados de la BD

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tendencias, container, false);

        RecyclerView MyRecyclerView = view.findViewById(R.id.tendenciasRecyclerView);

        PublicacionRvAdapter adapter = new PublicacionRvAdapter(this.getContext(), Home.listaPublicaciones);
        MyRecyclerView.setAdapter(adapter);
        MyRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        return view;
    }
}