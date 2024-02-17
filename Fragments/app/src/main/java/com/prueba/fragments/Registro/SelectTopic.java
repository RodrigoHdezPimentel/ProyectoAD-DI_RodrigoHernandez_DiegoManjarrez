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
import com.prueba.fragments.RetrofitConnection.Models.Tema;

import java.util.ArrayList;

public class SelectTopic extends AppCompatActivity {
    public ChipGroup chipGroup;
    public ArrayList<Integer> temasUserId = new ArrayList<>();
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
            @SuppressLint("ResourceType")
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

    @SuppressLint("ResourceAsColor")
    public void cargarThemes(){
        for(Tema t : Login_SignUP.listaTemas){
            Chip newChip = new Chip(this);
            newChip.setText(t.getTitulo());
            ChipDrawable chipDrawable = ChipDrawable.createFromAttributes(this, null, 0, com.google.android.material.R.style.Widget_Material3_Chip_Filter);
            newChip.setId(t.getId());
            newChip.setChipDrawable(chipDrawable);

            newChip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        temasUserId.add(newChip.getId());
                    }else{
                        for (int x=0; x < temasUserId.size(); x++){
                            if(temasUserId.get(x) == newChip.getId()){
                                temasUserId.remove(x);
                                break;
                            }
                        }
                    }
                }
            });

            chipGroup.addView(newChip);

        }

    }
}