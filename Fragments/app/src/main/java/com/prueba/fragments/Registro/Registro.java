package com.prueba.fragments.Registro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.prueba.fragments.Login_SignUP;
import com.prueba.fragments.MainActivity;
import com.prueba.fragments.R;

public class Registro extends AppCompatActivity {
Button buttonRegistrar;
ImageView screenSplash;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        buttonRegistrar = findViewById(R.id.buttonRegistrar);
        screenSplash = findViewById(R.id.backgroundScreen);

        buttonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                screenSplash.setVisibility(View.VISIBLE);
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(Registro.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                }, 500);
            }
        });


    }
}