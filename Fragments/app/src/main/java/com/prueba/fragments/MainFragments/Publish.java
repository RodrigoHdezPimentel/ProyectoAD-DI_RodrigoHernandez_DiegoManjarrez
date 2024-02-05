package com.prueba.fragments.MainFragments;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prueba.fragments.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Publish#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Publish extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Publish() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Publish.
     */
    // TODO: Rename and change types and number of parameters
    public static Publish newInstance(String param1, String param2) {
        Publish fragment = new Publish();
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
        View root = inflater.inflate(R.layout.fragment_publish, container, false);
        ConstraintLayout constraintLayout = root.findViewById(R.id.constraintContenido);

        // Crear un drawable de forma programática para el borde
        GradientDrawable borderDrawable = new GradientDrawable();
        int color = getResources().getColor(R.color.black);
        borderDrawable.setStroke(2, color); // Ancho del borde y color

        // Aplicar el borde al ConstraintLayout
        constraintLayout.setBackground(borderDrawable);
        return  root;
    }
}