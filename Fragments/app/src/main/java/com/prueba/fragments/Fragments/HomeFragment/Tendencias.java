package com.prueba.fragments.Fragments.HomeFragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.prueba.fragments.EditProfile;
import com.prueba.fragments.Login_SignUP;
import com.prueba.fragments.MainActivity;
import com.prueba.fragments.R;
import com.prueba.fragments.RecyclerViews.Adapters.PublicacionRvAdapter;
import com.prueba.fragments.RetrofitConnection.Models.Publicacion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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
    ProgressBar progressBar;
    List<Publicacion> listaPublicaciones;
    FloatingActionButton searchFilter;
    View view;
    TextInputEditText inputTitulo;
    TextInputEditText inputContenido;
    TextInputEditText inputAutor;
    TextInputEditText inputTema;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tendencias, container, false);

        progressBar = view.findViewById(R.id.progressBar);
        searchFilter = view.findViewById(R.id.showFilter);

        searchFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarFiltro();
            }
        });
        getPublishTrending();
        return view;
    }
    private AlertDialog alertDialog;
    private void mostrarFiltro(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        // Inflar el diseño personalizado
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_search_filter, null);
        builder.setView(dialogView);

        ImageView cerrar = dialogView.findViewById(R.id.cerrar);
        Button applyFilter = dialogView.findViewById(R.id.applyFilters);

        inputTitulo = dialogView.findViewById(R.id.inputTitulo);
        inputContenido = dialogView.findViewById(R.id.inputContenido);
        inputAutor = dialogView.findViewById(R.id.inputAutor);
        inputTema = dialogView.findViewById(R.id.inputTema);

        inputTitulo.setText(titulo);
        inputContenido.setText(contenido);
        inputAutor.setText(user);
        inputTema.setText(tema);

        applyFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
                alertDialog.dismiss();
            }
        });


        // Configurar controladores de clic para los botones
        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog = builder.create();
        alertDialog.show();
    }
    String titulo = "";
    String contenido = "";
    String user = "";
    String tema = "";
    @SuppressLint("ResourceAsColor")
    private void search(){

        if(inputTitulo.getText().toString().equals("")){
            titulo = " ";
        }
        else{
            titulo = inputTitulo.getText().toString();
        }
        if(inputContenido.getText().toString().equals("")){
            contenido = " ";
        }
        else{
            contenido = inputContenido.getText().toString();
        }
        if(inputAutor.getText().toString().equals("")){
            user = " ";
        }
        else{
            user = inputAutor.getText().toString();
        }
        if(inputTema.getText().toString().equals("")){
            tema = " ";
        }
        else{
            tema = inputTema.getText().toString();
        }

        Call<List<Publicacion>> call = MainActivity.publicacionInterface.getFiltroPublication(titulo, contenido, user, tema);
        call.enqueue(new Callback<List<Publicacion>>() {
            @Override
            public void onResponse(Call<List<Publicacion>> call, Response<List<Publicacion>> response) {
                if (!response.isSuccessful()) {
                    //Log.e("Response err: ", response.message());
                    return;
                }
                listaPublicaciones = response.body();

                RecyclerView MyRecyclerView = view.findViewById(R.id.tendenciasRecyclerView);
                MyRecyclerView.removeAllViews();
                PublicacionRvAdapter adapter = new PublicacionRvAdapter(getContext(), listaPublicaciones);
                MyRecyclerView.setAdapter(adapter);
                MyRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }
                @Override
                public void onFailure (Call < List < Publicacion >> call, Throwable t){

                }
            });
    }

    private void getPublishTrending() {
        Call<List<Publicacion>> call = MainActivity.publicacionInterface.getPublishTrending();
        call.enqueue(new Callback<List<Publicacion>>() {
            @Override
            public void onResponse(Call<List<Publicacion>> call, Response<List<Publicacion>> response) {
                if (!response.isSuccessful()) {
                    //Log.e("Response err: ", response.message());
                    return;
                }

                listaPublicaciones = response.body();
                progressBar.setVisibility(View.GONE);

                RecyclerView MyRecyclerView = view.findViewById(R.id.tendenciasRecyclerView);
                PublicacionRvAdapter adapter = new PublicacionRvAdapter(getContext(), listaPublicaciones);
                MyRecyclerView.setAdapter(adapter);
                MyRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }
            @Override
            public void onFailure(Call<List<Publicacion>> call, Throwable t) {

            }
        });
    }
}