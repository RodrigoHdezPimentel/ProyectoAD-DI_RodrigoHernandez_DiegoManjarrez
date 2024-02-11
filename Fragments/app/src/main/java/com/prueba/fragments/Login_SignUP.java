package com.prueba.fragments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.prueba.fragments.RecyclerViews.Models.Chat;
import com.prueba.fragments.RecyclerViews.Models.Publicacion;
import com.prueba.fragments.Registro.Registro;

import java.util.ArrayList;

public class Login_SignUP extends AppCompatActivity {
    public static ArrayList<Publicacion> listaPublicaciones = new ArrayList<>();
    public static ArrayList<Chat> listaChats = new ArrayList<>();
    public static ArrayList<Chat> chatConversation = new ArrayList<>();
    Button buttonLogin;
    Button buttonSignUp;
    public static int idRegistrado = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sign_up);
        //Relleno temporal para las publicaciones
        listaPublicaciones.add(
                new Publicacion(1,1,64,32,"contenido 1"));
        listaPublicaciones.add(
                new Publicacion(9,14,12,2647,"contenido 2"));
        listaPublicaciones.add(
                new Publicacion(5,16,43,5638,"contenido 3"));
        listaPublicaciones.add(
                new Publicacion(12,21,11,86,"contenido 4"));


        chatConversation.add(new Chat(1,2,"hola","fecha"));
        chatConversation.add(new Chat(2,1,"hola, ¿que tal?","fecha"));
        chatConversation.add(new Chat(1,2,"Bien, y tu?","fecha"));
        chatConversation.add(new Chat(2,1,"A mi se me ha muerto el perro","fecha"));
        chatConversation.add(new Chat(1,2,"jajajaja","fecha"));
        chatConversation.add(new Chat(1,2,"perdon, chat equivocado","fecha"));
        chatConversation.add(new Chat(1,2,"ppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppp","fecha"));

        listaChats.add(new Chat(2,1,"ppppppppppppppppp", "fecha"));

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