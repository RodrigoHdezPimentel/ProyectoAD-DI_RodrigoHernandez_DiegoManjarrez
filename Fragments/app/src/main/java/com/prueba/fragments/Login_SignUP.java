package com.prueba.fragments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Login_SignUP extends AppCompatActivity {
Button buttonLogin;
Button buttonSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sign_up);
    buttonLogin = findViewById(R.id.buttonLogin);
    buttonLogin.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent goMain = new Intent(Login_SignUP.this,MainActivity.class);
            startActivity(goMain);
            //el finish se utiliza para eliminar la activvity para que no ocupe almacenamiento
            finish();
        }
    });
}
}