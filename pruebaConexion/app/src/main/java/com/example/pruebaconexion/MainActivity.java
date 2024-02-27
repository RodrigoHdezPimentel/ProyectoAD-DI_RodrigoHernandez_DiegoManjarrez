package com.example.pruebaconexion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

    Button insertar;
    Button update;

    Button delete;

    TextView nombre;
    TextView nacimiento;
    TextView genero;
    TextView password;
    TextView descripcion;
    TextView mail;
    String ip_diego = "192.168.56.1";
    String ip_rodri = "172.29.144.1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBar);
        insertar = findViewById(R.id.insertar);
        nombre = findViewById(R.id.nombre);
        nacimiento = findViewById(R.id.nacimiento);
        password = findViewById(R.id.contrasena);
        genero = findViewById(R.id.genero);
        descripcion = findViewById(R.id.descripcion);
        mail = findViewById(R.id.mail);
        update = findViewById(R.id.update);
        delete = findViewById(R.id.delete);
        insertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Usuario user = new Usuario(Integer.parseInt(nacimiento.getText().toString()),
                        nombre.getText().toString(),
                        genero.getText().toString(), descripcion.getText().toString(),
                        mail.getText().toString(), password.getText().toString());
                //Integer year, String name, String genero, String descripcion, String mail, String pass
               // Toast.makeText(MainActivity.this, user.getName().toString(), Toast.LENGTH_SHORT).show();
               createUser(user);
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Usuario user = new Usuario(Integer.parseInt(nacimiento.getText().toString()),
                        nombre.getText().toString(),
                        genero.getText().toString(), descripcion.getText().toString(),
                        mail.getText().toString(), password.getText().toString());
                user.setId(3);
                updateUser(user);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = 5;

                deleteUser(id);
            }
        });

        //getAllUser();
        //getAllPubliacion();

       // getUSerOne(2);
        //createUser();
        scrollView = findViewById(R.id.vistaName);
        l = findViewById(R.id.listaName);
    }
    private void getAllPubliacion(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://"+ip_rodri+":8086/publicacion/")
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
                .baseUrl("http://"+ip_diego+":8086/Usuario/")
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
        Call <Usuario> call = crudUsuarioInterface.getUserOne(id);
        call.enqueue(new Callback<Usuario>() {

            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if(!response.isSuccessful()) {
                    Log.e("Response err: ", response.message());
                    return;
                }
                progressBar.setVisibility(View.GONE);
                Log.d("Response", response.toString());
                Usuario user;
                TextView t2 = new TextView(MainActivity.this);

                if((user = response.body()) != null){
                    t2.setText(user.getName());

                }else{
                    t2.setText("User not found");
                }
                l.addView(t2);

            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.e("Thorw err: ", t.getMessage());
            }
        });
    }
    private void createUser(Usuario user){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://"+ip_diego+":8086/Usuario/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        crudUsuarioInterface = retrofit.create(CRUD_UsuarioInterface.class);
        Call<Usuario> call = crudUsuarioInterface.create(user);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Error de respuesta", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(MainActivity.this, "asdasdasd", Toast.LENGTH_SHORT).show();
                Usuario newUser = response.body();
                Toast.makeText(MainActivity.this, newUser.getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.e("Thorw err: ", t.getMessage());
            }
        });

    }
    private  void updateUser(Usuario user){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://"+ip_diego+":8086/Usuario/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        crudUsuarioInterface = retrofit.create(CRUD_UsuarioInterface.class);
        Call<Usuario> call = crudUsuarioInterface.update(user);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Error de respuesta", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(MainActivity.this, "DATOS CAMBOIADOS", Toast.LENGTH_SHORT).show();
                Usuario newUser = response.body();
                Toast.makeText(MainActivity.this, newUser.getId().toString(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.e("Thorw err: ", t.getMessage());

            }
        });

    }
    private void deleteUser(int id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://" + ip_diego + ":8086/Usuario/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        crudUsuarioInterface = retrofit.create(CRUD_UsuarioInterface.class);
        Call <Boolean> call = crudUsuarioInterface.delete(id);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Error de respuesta", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(response.body()){
                    Toast.makeText(MainActivity.this, "ELIMINADO", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(MainActivity.this, "Fallo en la eliminacion", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });
    }
}