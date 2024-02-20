package com.prueba.fragments.Fragments.MainFragment;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.prueba.fragments.Login_SignUP;
import com.prueba.fragments.MainActivity;
import com.prueba.fragments.R;
import com.prueba.fragments.RetrofitConnection.Interfaces.PublicacionInterface;
import com.prueba.fragments.RetrofitConnection.Interfaces.UsuarioInterface;
import com.prueba.fragments.RetrofitConnection.Models.Publicacion;
import com.prueba.fragments.RetrofitConnection.Models.Tema;
import com.prueba.fragments.RetrofitConnection.Models.Usuario;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    Tema temaSeleccionado = null;
    TextInputEditText titulo;
    TextInputEditText contenido;
    Button publish;
    Button cancel;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_publish, container, false);

        titulo = view.findViewById(R.id.tietTitulo);
        contenido = view.findViewById(R.id.tietContenido);

        publish = view.findViewById(R.id.buttonPublicar);
        cancel = view.findViewById(R.id.buttonCanelar);

        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                publicar();
            }
        });

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
                    temaSeleccionado = t;
                }
            });
            liLayTemas.addView(tema);
            listaTvTemas.add(tema);
        }

        return view;
    }
    public void publicar(){
        Toast.makeText(view.getContext(), "llama funcion", Toast.LENGTH_SHORT).show();
        if(temaSeleccionado != null &&  !contenido.getText().toString().equals("") && !titulo.getText().toString().equals("")){
            Toast.makeText(view.getContext(), "pasa filtro", Toast.LENGTH_SHORT).show();

            Date date = new Date();
            long timeInMilliSeconds = date.getTime();
            java.sql.Date date1 = new java.sql.Date(timeInMilliSeconds);

            Toast.makeText(view.getContext(), temaSeleccionado.toString(), Toast.LENGTH_LONG).show();

            Publicacion newPublicacion = new Publicacion(
                    Usuario.getInstance().getId(), temaSeleccionado.getId(), null, date1.toString(),
                    0, contenido.getText().toString(), titulo.getText().toString(), temaSeleccionado, Usuario.getInstance(), new Publicacion[0]);

            PublicacionInterface publicacionInterface = Login_SignUP.retrofitPublicacion.create(PublicacionInterface.class);
            Call<Publicacion> call = publicacionInterface.save(newPublicacion);
            call.enqueue(new Callback<Publicacion>() {
                @Override
                public void onResponse(Call<Publicacion> call, Response<Publicacion> response) {
                    if (!response.isSuccessful()) {
                        Log.e("Response err: ", response.message());
                        return;
                    }
                    Toast.makeText(view.getContext(), response.body().toString(), Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onFailure(Call<Publicacion> call, Throwable t) {

                }
            });
        }

    }
}