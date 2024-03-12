package com.prueba.fragments.Fragments.ProfileFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.prueba.fragments.Fragments.MainFragment.Profile;
import com.prueba.fragments.Login_SignUP;
import com.prueba.fragments.R;
import com.prueba.fragments.RecyclerViews.Adapters.PublicacionRvAdapter;
import com.prueba.fragments.RetrofitConnection.Interfaces.UsuarioInterface;
import com.prueba.fragments.RetrofitConnection.Models.Publicacion;
import com.prueba.fragments.RetrofitConnection.Models.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class misPublicaciones extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public misPublicaciones() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static misPublicaciones newInstance(String param1, String param2) {
        misPublicaciones fragment = new misPublicaciones();
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

    List<Publicacion> listaPublicaciones;
    UsuarioInterface usuarioInterface;
    View view;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_mis_publicaciones, container, false);
        //La barra de progreso
        progressBar = view.findViewById(R.id.progressBar);

        getAllPubliacionFromUser();
        return view;
    }

    private void getAllPubliacionFromUser() {

        usuarioInterface = Login_SignUP.retrofitUser.create(UsuarioInterface.class);
        Call<List<Publicacion>> call = usuarioInterface.getPublicationsFromUser(Profile.perfil.getId());
        call.enqueue(new Callback<List<Publicacion>>() {
            @Override
            public void onResponse(Call<List<Publicacion>> call, Response<List<Publicacion>> response) {
                if (!response.isSuccessful()) {
                    Log.e("Response err: ", response.message());
                    return;
                }

                listaPublicaciones = response.body();
                progressBar.setVisibility(View.GONE);

                RecyclerView MyRecyclerView = view.findViewById(R.id.misPublicacionesRecyclerView);
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