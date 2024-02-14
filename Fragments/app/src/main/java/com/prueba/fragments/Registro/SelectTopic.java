package com.prueba.fragments.Registro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.prueba.fragments.Login_SignUP;
import com.prueba.fragments.MainActivity;
import com.prueba.fragments.R;

public class SelectTopic extends AppCompatActivity {
    public ChipGroup chipGroup;
    Button buttonConfirmar;
    Button buttonCancelar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_topic);
        chipGroup = findViewById(R.id.chipGroupThemes);
        ConstraintLayout splashCreen = findViewById(R.id.SclashScreen);
        ConstraintLayout selectTopic = findViewById(R.id.ContenidoParaOcultar);
        buttonConfirmar = findViewById(R.id.buttonConfirmarTopic);
        buttonCancelar = findViewById(R.id.buttonCancellTopic);
        splashCreen.setVisibility(View.INVISIBLE);
        selectTopic.setVisibility(View.VISIBLE);
        cargarThemes();
        buttonConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                splashCreen.setVisibility(View.VISIBLE);
                selectTopic.setVisibility(View.INVISIBLE);

                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(SelectTopic.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                }, 500);
            }
        });

        buttonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goRegistro = new Intent(SelectTopic.this, Registro.class);
                startActivity(goRegistro);
            }
        });
    }

    public void cargarThemes(){
        String[] themesName = getResources().getStringArray(R.array.listaThemes);
        for(String s : themesName){
            Chip newChip = new Chip(this);
            newChip.setText(s);

            chipGroup.addView(newChip);

        }

    }
}