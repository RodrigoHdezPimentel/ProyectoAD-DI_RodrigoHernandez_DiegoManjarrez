package com.prueba.fragments.Fragments.HomeFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.prueba.fragments.Fragments.MainFragment.Home;
import com.prueba.fragments.MainActivity;
import com.prueba.fragments.R;
import com.prueba.fragments.RecyclerViews.Adapters.PublicacionRvAdapter;




public class MisTemas extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public MisTemas() {
        // Required empty public constructor
    }


    public static MisTemas newInstance(String param1, String param2) {
        MisTemas fragment = new MisTemas();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mis_temas, container, false);

       RecyclerView MyRecyclerView = view.findViewById(R.id.MisTemasRecyclerView);
        PublicacionRvAdapter adapter = new PublicacionRvAdapter(this.getContext(), MainActivity.listaPublicaciones);
        MyRecyclerView.setAdapter(adapter);
        MyRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        return view;
    }

}