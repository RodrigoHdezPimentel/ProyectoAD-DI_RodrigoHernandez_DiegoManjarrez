package com.prueba.fragments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.prueba.fragments.Registro.Registro;

public class Login_SignUP extends AppCompatActivity {
Button buttonLogin;
Button buttonSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sign_up);
    buttonLogin = findViewById(R.id.buttonLogin);
    buttonSignUp = findViewById(R.id.buttonSignUp);
        //el finish() se utiliza para eliminar la activvity para que no ocupe almacenamiento

        buttonLogin.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            //colocar metodo de comprobaciçon en la base de datos si el username y el paswword coincide
            Intent goMain = new Intent(Login_SignUP.this,MainActivity.class);
            startActivity(goMain);
            finish();

        }
    });

    buttonSignUp.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            Intent goMain = new Intent(Login_SignUP.this, Registro.class);
            startActivity(goMain);
            finish();

        }
    });

    }

}