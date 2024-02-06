package com.prueba.fragments.Registro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.prueba.fragments.MainActivity;
import com.prueba.fragments.R;

public class SelectTopic extends AppCompatActivity {
    public ChipGroup chipGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_topic);
         chipGroup = findViewById(R.id.chipGroupThemes);

        cargarThemes();
        /*buttonRegistrar.setOnClickListener(new View.OnClickListener() {
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
        });*/
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