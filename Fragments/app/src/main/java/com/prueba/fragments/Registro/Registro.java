package com.prueba.fragments.Registro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.prueba.fragments.Login_SignUP;
import com.prueba.fragments.R;

import java.util.HashMap;
import java.util.Map;


public class Registro extends AppCompatActivity {
Button buttonRegistrar;
Button buttonCancelar;

TextInputEditText userName;
TextInputEditText  password;
TextInputEditText  yearsOdl;
TextInputEditText  email;
AutoCompleteTextView  gender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        buttonRegistrar = findViewById(R.id.buttonRegistrarRegister);
        buttonCancelar = findViewById(R.id.buttonCancellRegister);

        userName = findViewById(R.id.inputUserNameRegister);
        password = findViewById(R.id.inputPasswordRegister);
        yearsOdl = findViewById(R.id.inputYearsOldRegister);
        email = findViewById(R.id.inputEmailRegister);

        //Cargar la el Array de Gender y colocarlo en el xml
        gender = findViewById(R.id.listaGender);
        String[] items = getResources().getStringArray(R.array.Gender);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, items);
        gender.setAdapter(adapter);


        buttonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toThemes = new Intent(Registro.this, SelectTopic.class);
                Map<String, String> user = new HashMap<>();
                toThemes.putExtra("userName", userName.getText().toString());
                toThemes.putExtra("password", password.getText().toString());
                toThemes.putExtra("gender", gender.getText().toString());
                toThemes.putExtra("email", email.getText().toString());
                //Toast.makeText(Registro.this, email.getText().toString(), Toast.LENGTH_SHORT).show();
                toThemes.putExtra("yearsOld", yearsOdl.getText().toString());
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



}