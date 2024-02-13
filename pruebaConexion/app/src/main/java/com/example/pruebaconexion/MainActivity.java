package com.example.pruebaconexion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Optional;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    List<Usuario> usuarios;
    CRUD_UsuarioInterface crudUsuarioInterface;
    ScrollView scrollView;
    LinearLayout l;
    ProgressBar progressBar;
     List<Publicacion> listaPublicaciones;
    PublicacionInterface publicacionInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBar);

        //getAllUser();
        //getAllPubliacion();

        getUSerOne(2);
        scrollView = findViewById(R.id.vistaName);
        l = findViewById(R.id.listaName);
    }
    private void getAllPubliacion(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.56.1:8086/publicacion/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        publicacionInterface = retrofit.create(PublicacionInterface.class);
        Call<List<Publicacion>> call = publicacionInterface.getAll();
        call.enqueue(new Callback<List<Publicacion>>() {
            @Override
            public void onResponse(Call<List<Publicacion>> call, Response<List<Publicacion>> response) {
                if(!response.isSuccessful()){
                    Log.e("Response err: ", response.message());
                    return;
                }
                listaPublicaciones = response.body();
                listaPublicaciones.forEach(u -> Log.i("Usaurio err: ", u.toString()));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        for (int i = 0; i < listaPublicaciones.size(); i++){
                            TextView usernew = new TextView(MainActivity.this);
                            usernew.setText(listaPublicaciones.get(i).getId().toString());
                            l.addView(usernew);

                        }
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Publicacion>> call, Throwable t) {
                Log.e("Failure", "Error en la solicitud HTTP: " + t.getMessage());
                t.printStackTrace();
            }

        });
    }
    private void getAllUser(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.56.1:8086/Usuario/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        crudUsuarioInterface = retrofit.create(CRUD_UsuarioInterface.class);
        Call<List<Usuario>> call = crudUsuarioInterface.getAll();
        call.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                if(!response.isSuccessful()){
                    Log.e("Response err: ", response.message());
                    return;
                }
                usuarios = response.body();
                usuarios.forEach(u -> Log.i("Usaurio err: ", u.toString()));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        for (int i = 0; i < usuarios.size(); i++){
                            TextView usernew = new TextView(MainActivity.this);
                            usernew.setText(usuarios.get(i).getName());
                            l.addView(usernew);

                        }
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                Log.e("Thorw err: ", t.getMessage());

            }
        });
    }
    private void getUSerOne(Integer id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.56.1:8086/Usuario/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        crudUsuarioInterface = retrofit.create(CRUD_UsuarioInterface.class);
        Call <Optional<Usuario>> call = crudUsuarioInterface.getUserOne(id);
        call.enqueue(new Callback<Optional<Usuario>>() {

            @Override
            public void onResponse(Call<Optional<Usuario>> call, Response<Optional<Usuario>> response) {
                if(!response.isSuccessful()) {
                    Log.e("Response err: ", response.message());
                    return;
                }
                progressBar.setVisibility(View.GONE);
                Log.d("Response", response.toString());
                Optional<Usuario> user = response.body();

                if (user.isPresent()) {
                    Usuario usuario = user.get();

                } else {
                    Toast.makeText(MainActivity.this, ""+response.body(), Toast.LENGTH_SHORT).show();

                }
//                Toast.makeText(MainActivity.this, user.get().getName(), Toast.LENGTH_SHORT).show();
//                if(user.isPresent()) {
//                    progressBar.setVisibility(View.GONE);
//                    // Verificamos si el objeto Optional<Usuario> no es nulo
//                    TextView t2 = new TextView(MainActivity.this);
//                    t2.setText(user.get().getName());
//                    l.addView(t2);
//                } else {
//                    Log.e("User not found: ", "User with id " + id + " not found");
//                }
            }

            @Override
            public void onFailure(Call<Optional<Usuario>> call, Throwable t) {
                Log.e("Thorw err: ", t.getMessage());
            }
        });
    }
}