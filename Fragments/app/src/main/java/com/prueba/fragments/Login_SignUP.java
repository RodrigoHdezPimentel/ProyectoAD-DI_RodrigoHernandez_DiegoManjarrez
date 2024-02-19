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
import com.prueba.fragments.RetrofitConnection.Models.Publicacion;
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
    public static ArrayList<Tema> listaTemas = new ArrayList<>();
    public static ArrayList<Chat> listaChats = new ArrayList<>();
    public static ArrayList<Chat> chatConversation = new ArrayList<>();
    public static final String[] IP_DIEGO = {"192.168.56.1","192.168.0.178"};
    public static final String[] IP_RODRIGO = {"192.168.128.250", "192.168.0.251"};

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
                .baseUrl("http://" + IP_RODRIGO[0] +":8086/publicacion/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitTemas = new Retrofit.Builder()
                .baseUrl("http://" + IP_RODRIGO[0] +":8086/tema/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitUser = new Retrofit.Builder()
                .baseUrl("http://" + IP_RODRIGO[0] +":8086/usuario/")
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
        public void onClick(View v) {iniciarSesion();}});


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
        Call<List<Usuario>> call = usuarioInterface.getAll();
        call.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                if (!response.isSuccessful()) {
                    Log.e("Response err: ", response.message());
                    return;
                }
                String[] userData = new String[2];
                boolean datoEncontrado= false;
                for (Usuario u : response.body()){
                    userData[0] = u.getName();
                    userData[1] = u.getPass();

                    if(userName.getText().toString().equals(userData[0])
                            && password.getText().toString().equals(userData[1])){
                        datoEncontrado = true;
                        Usuario.setInstance(u);
                        break;
                    }
                }
                if(datoEncontrado){
                    Toast.makeText(Login_SignUP.this, Usuario.getInstance().getName()+"", Toast.LENGTH_SHORT).show();
                    Intent goMain = new Intent(Login_SignUP.this,MainActivity.class);
                    startActivity(goMain);
                }else{
                    Toast.makeText(Login_SignUP.this, "Error. Comprueba los datos", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {

            }
        });
    }
    public void getTemas(){
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
                for(Tema t : temporalList){
                    listaTemas.add(t);
                }
                return;
            }

            @Override
            public void onFailure(Call<List<Tema>> call, Throwable t) {

            }
        });
    }


}