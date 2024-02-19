package com.prueba.fragments.Fragments.MainFragment;

import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.prueba.fragments.Login_SignUP;
import com.prueba.fragments.R;
import com.prueba.fragments.RetrofitConnection.Models.Tema;

import java.nio.file.Files;
import java.util.ArrayList;

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
        View view = inflater.inflate(R.layout.fragment_publish, container, false);

        TextInputLayout contenido = view.findViewById(R.id.PublishContenido);
        contenido.setOnFocusChangeListener(new View.OnFocusChangeListener() {
       @Override
       public void onFocusChange(View view, boolean b) {
           if (contenido.hasFocus()){
               contenido.setGravity(Gravity.LEFT);
               contenido.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
           } else {
               contenido.setGravity(Gravity.CENTER);
               contenido.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
           }
       }
   });


        //ScrollView in loop
        HorizontalScrollView scrollView = (HorizontalScrollView) view.findViewById(R.id.ScrollTemas);
        LinearLayout liLayTemas = view.findViewById(R.id.linlayTemas);
        liLayTemas.removeAllViews();

        ArrayList<TextView> listaTvTemas = new ArrayList<>();

        for(Tema t : Login_SignUP.listaTemas){
            TextView tema = new TextView(view.getContext());
            tema.setTypeface(ResourcesCompat.getFont(view.getContext(), R.font.caviardreams));
            tema.setText(t.getTitulo().toString());
            tema.setTextSize(24f);
            tema.setWidth(500);
            tema.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tema.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(TextView tv : listaTvTemas){
                        tv.setTypeface(null, Typeface.NORMAL);
                    }
                    tema.setTypeface(null, Typeface.BOLD);
                }
            });
            liLayTemas.addView(tema);
            listaTvTemas.add(tema);
        }

        return view;
    }
}