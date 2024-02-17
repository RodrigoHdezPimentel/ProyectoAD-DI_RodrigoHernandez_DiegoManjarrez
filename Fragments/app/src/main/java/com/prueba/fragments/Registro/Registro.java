package com.prueba.fragments.Registro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.prueba.fragments.Login_SignUP;
import com.prueba.fragments.MainActivity;
import com.prueba.fragments.R;

import java.util.ArrayList;
import java.util.List;

public class Registro extends AppCompatActivity {
Button buttonRegistrar;
Button buttonCancelar;

Spinner spinnerGender;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        buttonRegistrar = findViewById(R.id.buttonRegistrarRegister);
        buttonCancelar = findViewById(R.id.buttonCancellRegister);
        //spinnerGender = findViewById(R.id.spinner_gender);

        // Crear una lista de opciones
//        List<String> genderOptions = new ArrayList<>();
//        genderOptions.add("Hombre");
//        genderOptions.add("Mujer");
//
//        // Crear un adaptador para el Spinner
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, genderOptions);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        // Asignar el adaptador al Spinner
//        spinnerGender.setAdapter(adapter);


        buttonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toThemes = new Intent(Registro.this, SelectTopic.class);
                startActivity(toThemes);

            }
        });
        buttonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toLoginSignUp = new Intent(Registro.this, Login_SignUP.class);
                startActivity(toLoginSignUp);
            }
        });

    }
    public void cargarGender(){
        String[] themesName = getResources().getStringArray(R.array.Gender);
        for(String s : themesName){
            Chip newChip = new Chip(this);
            newChip.setText(s);

           // chipGroup.addView(newChip);

        }

    }
}