package com.prueba.foroex_app.Fragments.MainFragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.prueba.foroex_app.MainActivity;
import com.prueba.foroex_app.R;
import com.prueba.foroex_app.RetrofitConnection.Models.Publicacion;
import com.prueba.foroex_app.RetrofitConnection.Models.Tema;
import com.prueba.foroex_app.RetrofitConnection.Models.Usuario;

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
    ArrayList<TextView> listaTvTemas = new ArrayList<>();
    Tema temaSeleccionado = null;
    TextInputEditText titulo;
    TextInputEditText contenido;
    Button publish;
    Button cancel;
    View view;
    ArrayList<Tema> listaTemas = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_publish, container, false);

        titulo = view.findViewById(R.id.tietTitulo);
        contenido = view.findViewById(R.id.tietContenido);

        publish = view.findViewById(R.id.buttonPublicar);
        cancel = view.findViewById(R.id.buttonCanelar);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contenido.setText("");
                titulo.setText("");

            }
        });
        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(titulo.getText().toString().trim().isEmpty() || contenido.getText().toString().trim().isEmpty()){
                    Toast.makeText(getContext(), "Por favor rellena los campos", Toast.LENGTH_SHORT).show();
                }else{
                    publicar();
                }
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
        getTemas();
        return view;
    }
    public void getTemas(){
        //Se obteine los temas de la database
        Call<List<Tema>> call = MainActivity.temaInterface.getAll();
        call.enqueue(new Callback<List<Tema>>() {

            @Override
            public void onResponse(Call<List<Tema>> call, Response<List<Tema>> response) {
                if (!response.isSuccessful()) {
                    Log.e("Response err: ", response.message());
                    return;
                }
                List<Tema> temporalList = response.body();
                assert temporalList != null;
                listaTemas.addAll(temporalList);
                setThemesLinearLayout();
            }

            @Override
            public void onFailure(Call<List<Tema>> call, Throwable t) {

            }
        });
    }

    public void setThemesLinearLayout(){
        //ScrollView in loop
        LinearLayout liLayTemas = view.findViewById(R.id.linlayTemas);
        liLayTemas.removeAllViews();

        for(Tema t : listaTemas){
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
    }
    public void publicar(){
        if(temaSeleccionado != null &&  !contenido.getText().toString().equals("") && !titulo.getText().toString().equals("")){

            Date date = new Date();
            long timeInMilliSeconds = date.getTime();
            java.sql.Date date1 = new java.sql.Date(timeInMilliSeconds);

            Publicacion newPublicacion = new Publicacion(null,
                    Usuario.getInstance().getId(), temaSeleccionado.getId(), null, date1.toString(),
                    0, contenido.getText().toString(), titulo.getText().toString(),
                    temaSeleccionado, Usuario.getInstance(), new Publicacion[0]);

            Call<Publicacion> call = MainActivity.publicacionInterface.save(newPublicacion);
            call.enqueue(new Callback<Publicacion>() {
                @Override
                public void onResponse(Call<Publicacion> call, Response<Publicacion> response) {
                    if (!response.isSuccessful()) {
                        Log.e("Response err: ", response.message());
                        return;
                    }
                    //Toast.makeText(view.getContext(), response.body().toString(), Toast.LENGTH_SHORT).show();
                    titulo.setText("");
                    contenido.setText("");
                    for(TextView tv : listaTvTemas){
                        tv.setTypeface(null, Typeface.NORMAL);
                    }
                    Toast.makeText(getContext(), "Publicacion realizada con exito", Toast.LENGTH_SHORT).show();

                }
                @Override
                public void onFailure(Call<Publicacion> call, Throwable t) {

                }
            });
        }

    }

}