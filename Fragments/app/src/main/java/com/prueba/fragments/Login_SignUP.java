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
import com.prueba.fragments.RetrofitConnection.Interfaces.UsuarioInterface;
import com.prueba.fragments.RetrofitConnection.Models.Publicacion;
import com.prueba.fragments.RetrofitConnection.Models.Usuario;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login_SignUP extends AppCompatActivity {

    Button buttonLogin;
    Button buttonSignUp;
    UsuarioInterface usuarioInterface;
    String[] userData = new String[2];

    TextView userName;
    TextView password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sign_up);
        userName = findViewById(R.id.inputUserName);
        password = findViewById(R.id.inputPassword);

        //conexion con retrofit
        MainActivity.retrofitUser = new Retrofit.Builder()
                .baseUrl("http://" + MainActivity.IP_DIEGO +":8086/usuario/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        buttonLogin = findViewById(R.id.buttonLogin);
        buttonSignUp = findViewById(R.id.buttonSignUp);

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
        usuarioInterface = MainActivity.retrofitUser.create(UsuarioInterface.class);
        Call<List<Usuario>> call = usuarioInterface.getAll();
        call.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                if (!response.isSuccessful()) {
                    Log.e("Response err: ", response.message());
                    return;
                }
                boolean datoEncontrado= false;
                for (Usuario u : response.body()){
                    userData[0] = u.getName();
                    userData[1] = u.getPass();
                    if(userName.getText().toString().equals(userData[0])
                            && password.getText().toString().equals(userData[1])){
                        datoEncontrado = true;
                        break;
                    }
                }
                if(datoEncontrado){
                    Intent goMain = new Intent(Login_SignUP.this,MainActivity.class);
                    startActivity(goMain);
                    finish();
                }else{
                    Toast.makeText(Login_SignUP.this, "Error. Comprueba los datos", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {

            }
        });
    }

}