package com.prueba.fragments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.prueba.fragments.Registro.Registro;
import com.prueba.fragments.RetrofitConnection.Interfaces.TemaInterface;
import com.prueba.fragments.RetrofitConnection.Interfaces.UsuarioInterface;
import com.prueba.fragments.RetrofitConnection.Models.Chat;
import com.prueba.fragments.RetrofitConnection.Models.Tema;
import com.prueba.fragments.RetrofitConnection.Models.Usuario;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login_SignUP extends AppCompatActivity {
    public static Retrofit retrofitPublicacion;
    public static Retrofit retrofitTemas;
    public static Retrofit retrofitUser;
    public static Retrofit retrofitUserTema;
    public static Retrofit retrofitLike;
    public static Retrofit retrofitChat;
    public static ArrayList<Tema> listaTemas = new ArrayList<>();

    public static final String[] IP_DIEGO = {"192.168.56.1","192.168.0.178"};
    public static final String[] IP_RODRIGO = {"192.168.128.250", "192.168.0.251"};//clase-casa

    Button buttonLogin;
    Button buttonSignUp;
    UsuarioInterface usuarioInterface;
    TextView userName;
    TextView password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sign_up);
        Usuario.getInstance();


        retrofitPublicacion = new Retrofit.Builder()
                .baseUrl("http://" + IP_DIEGO[0] +":8086/publicacion/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitTemas = new Retrofit.Builder()
                .baseUrl("http://" + IP_DIEGO[0] +":8086/tema/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitUser = new Retrofit.Builder()
                .baseUrl("http://" + IP_DIEGO[0] +":8086/usuario/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitUserTema =  new Retrofit.Builder()
                .baseUrl("http://" + Login_SignUP.IP_DIEGO[0] +":8086/usuarioTema/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitLike = new Retrofit.Builder()
                .baseUrl("http://" + Login_SignUP.IP_DIEGO[0] +":8086/like/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitChat = new Retrofit.Builder()
                .baseUrl("http://" + Login_SignUP.IP_DIEGO[0] +":8086/chat/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        userName = findViewById(R.id.inputUserName);
        password = findViewById(R.id.inputPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonSignUp = findViewById(R.id.buttonSignUp);

        getTemas();

        //Inicio de sesion
        buttonLogin.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            iniciarSesion();
        }
        });

        //Go Registro.java
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goMain = new Intent(Login_SignUP.this, Registro.class);
                startActivity(goMain);
            }
        });

    }
    private void iniciarSesion(){
        usuarioInterface = retrofitUser.create(UsuarioInterface.class);
        Call<Usuario> call = usuarioInterface.getUserRegister(userName.getText().toString(),password.getText().toString() );
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (!response.isSuccessful()) {
                    Log.e("Response err: fallo en logIn", response.message());
                    return;
                }
                Usuario userData = response.body();
                if(userData != null){
                    Usuario.setInstance(userData);

                    Intent goMain = new Intent(Login_SignUP.this,MainActivity.class);
                    startActivity(goMain);
                }else{
                    Toast.makeText(Login_SignUP.this, "Error. Comprueba los datos", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {

            }
        });
    }
    public void getTemas(){
        //Se obteine los temas de la database
        TemaInterface temaInterface = Login_SignUP.retrofitTemas.create(TemaInterface.class);
        Call<List<Tema>> call = temaInterface.getAll();
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
            }

            @Override
            public void onFailure(Call<List<Tema>> call, Throwable t) {

            }
        });
    }


}