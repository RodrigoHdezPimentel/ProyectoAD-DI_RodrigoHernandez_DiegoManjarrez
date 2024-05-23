package com.prueba.foroex_app.Registro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.prueba.foroex_app.Login_SignUP;
import com.prueba.foroex_app.MainActivity;
import com.prueba.foroex_app.R;
import com.prueba.foroex_app.RetrofitConnection.Models.Tema;
import com.prueba.foroex_app.RetrofitConnection.Models.Usuario;
import com.prueba.foroex_app.RetrofitConnection.Models.UsuarioTema;
import com.prueba.foroex_app.RetrofitConnection.Models.UsuarioTemaFK;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectTopic extends AppCompatActivity {
    public ChipGroup chipGroup;
    //public ArrayList<Integer> temaId = new ArrayList<>();
    Button buttonConfirmar;
    Button buttonCancelar;
    ArrayList<Chip> listChip = new ArrayList<>();

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
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {

                createUser();

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
                }, 800);

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
            listChip.add(newChip);
            chipGroup.addView(newChip);
        }

    }

    public void createUser(){
        Call<Usuario> call = MainActivity.usuarioInterface.create(Usuario.getInstance());
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(SelectTopic.this, "Error en la Respuesta", Toast.LENGTH_SHORT).show();
                    return;
                }
                UsuarioTemaFK idTemaUser = new UsuarioTemaFK();
                UsuarioTema userTema = new UsuarioTema(idTemaUser);
                //Actualizar el ID de la instancia singleton
                Usuario.getInstance().setId(response.body().getId());

                //Comprobacion de chips que están seleccionado y pasarlos para crear el userTema
                for (int i = 0; i < listChip.size(); i++) {

                    if(listChip.get(i).isChecked()){
                        userTema.getId().setIdTema(listChip.get(i).getId());
                        userTema.getId().setIdUsuario(Usuario.getInstance().getId());
                        createUserTema(userTema);
                    }
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.e("Thorw err: ", t.getMessage());
            }
        });

    }

    public void createUserTema(UsuarioTema userTema){
        Call<UsuarioTema> call = MainActivity.usuarioTemaInterface.create(userTema);


            call.enqueue(new Callback<UsuarioTema>() {
                @Override
                public void onResponse(Call<UsuarioTema> call, Response<UsuarioTema> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(SelectTopic.this, "Error en la Respuesta", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Toast.makeText(SelectTopic.this, "Se ha añadido userTema exitosamente", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onFailure(Call<UsuarioTema> call, Throwable t) {

                }
            });



    }
}