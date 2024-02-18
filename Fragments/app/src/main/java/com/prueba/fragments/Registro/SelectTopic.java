package com.prueba.fragments.Registro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.prueba.fragments.Login_SignUP;
import com.prueba.fragments.MainActivity;
import com.prueba.fragments.R;
import com.prueba.fragments.RetrofitConnection.Interfaces.UsuarioInterface;
import com.prueba.fragments.RetrofitConnection.Models.Tema;
import com.prueba.fragments.RetrofitConnection.Models.Usuario;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SelectTopic extends AppCompatActivity {
    public ChipGroup chipGroup;
    public ArrayList<Integer> temasUserId = new ArrayList<>();
    Button buttonConfirmar;
    Button buttonCancelar;
    ArrayList<Chip> listChip = new ArrayList<>();


    String userName;
    String password;
    String email;
    String gender;
    int yearsOld;
    UsuarioInterface usuarioInterface;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_topic);
        cargarDatosRegister();

        Toast.makeText(this, userName, Toast.LENGTH_SHORT).show();

        chipGroup = findViewById(R.id.chipGroupThemes);
        ConstraintLayout splashCreen = findViewById(R.id.SclashScreen);
        ConstraintLayout selectTopic = findViewById(R.id.ContenidoParaOcultar);
        buttonConfirmar = findViewById(R.id.buttonConfirmarTopic);
        buttonCancelar = findViewById(R.id.buttonCancellTopic);
        splashCreen.setVisibility(View.INVISIBLE);
        selectTopic.setVisibility(View.VISIBLE);

        cargarThemes();
        buttonConfirmar.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {

                //Comprovacion de chip seleccionados
                for (Chip c : listChip){
                    if (c.isChecked()){
                        temasUserId.add(c.getId());
                    }
                }
                createUser();
//                splashCreen.setVisibility(View.VISIBLE);
//                selectTopic.setVisibility(View.INVISIBLE);

//                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        Intent intent = new Intent(SelectTopic.this, MainActivity.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                        startActivity(intent);
//                    }
//                }, 800);
                Toast.makeText(SelectTopic.this, temasUserId.size()+"", Toast.LENGTH_SHORT).show();
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

    @SuppressLint("ResourceAsColor")
    public void cargarThemes(){
        for(Tema t : Login_SignUP.listaTemas){
            Chip newChip = new Chip(this);
            newChip.setText(t.getTitulo());
            ChipDrawable chipDrawable = ChipDrawable.createFromAttributes(this, null, 0, com.google.android.material.R.style.Widget_Material3_Chip_Filter);
            newChip.setId(t.getId());
            newChip.setChipDrawable(chipDrawable);
            newChip.isChecked();
            listChip.add(newChip);
            chipGroup.addView(newChip);
        }

    }
    public void cargarDatosRegister(){
        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");
        password = intent.getStringExtra("password");
        yearsOld = Integer.parseInt(intent.getStringExtra("yearsOld"));
        email = intent.getStringExtra("email");
        gender = intent.getStringExtra("gender");
    }
    public void createUser(){
        Usuario user = new Usuario(null, yearsOld, userName, gender, email,password );
         usuarioInterface = Login_SignUP.retrofitUser.create(UsuarioInterface.class);
        Call<Usuario> call = usuarioInterface.create(user);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(SelectTopic.this, "Error en la Respuesta", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.e("Thorw err: ", t.getMessage());
            }
        });

    }
}