package com.prueba.fragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.prueba.fragments.IdiomasAdapter.LanguageItemAdapter;
import com.prueba.fragments.Registro.Registro;
import com.prueba.fragments.RetrofitConnection.Interfaces.TemaInterface;
import com.prueba.fragments.RetrofitConnection.Interfaces.UsuarioInterface;
import com.prueba.fragments.RetrofitConnection.Models.Chat;
import com.prueba.fragments.RetrofitConnection.Models.Tema;
import com.prueba.fragments.RetrofitConnection.Models.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login_SignUP extends AppCompatActivity {
    public static Retrofit retrofitPublicacion;
    public static Retrofit retrofitTemas;
    public static Retrofit retrofitUser;
    public static Retrofit retrofitUserTema;
    public static Retrofit retrofitLike;
    public static Retrofit retrofitChat;
    public static ArrayList<Tema> listaTemas = new ArrayList<>();


    public static final String[] IP_DIEGO = {"192.168.56.1","192.168.0.178"};
    public static final String[] IP_RODRIGO = {"192.168.128.250", "192.168.0.251"};//clase-casa

    Button buttonLogin;
    Button buttonSignUp;
    UsuarioInterface usuarioInterface;
    TextView userName;
    TextView password;
    Spinner listLanguages;
    TextInputLayout usernameHint;
    TextInputLayout passwordHint;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sign_up);

        usernameHint = findViewById(R.id.userNameHint);
        passwordHint = findViewById(R.id.passwordHint);
        userName = findViewById(R.id.inputUserName);
        password = findViewById(R.id.inputPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonSignUp = findViewById(R.id.buttonSignUp);


        Usuario.getInstance();
        cargarIdioma();



        retrofitPublicacion = new Retrofit.Builder()
                .baseUrl("http://" + IP_DIEGO[1] +":8086/publicacion/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitTemas = new Retrofit.Builder()
                .baseUrl("http://" + IP_DIEGO[1] +":8086/tema/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitUser = new Retrofit.Builder()
                .baseUrl("http://" + IP_DIEGO[1] +":8086/usuario/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitUserTema =  new Retrofit.Builder()
                .baseUrl("http://" + IP_DIEGO[1] +":8086/usuarioTema/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitLike = new Retrofit.Builder()
                .baseUrl("http://" + IP_DIEGO[1] +":8086/like/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitChat = new Retrofit.Builder()
                .baseUrl("http://" + IP_DIEGO[1] +":8086/chat/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        getTemas();

        //Inicio de sesion
        buttonLogin.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            iniciarSesion();
        }
        });

        //Go Registro.java
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goMain = new Intent(Login_SignUP.this, Registro.class);
                startActivity(goMain);
            }
        });

    }
    private void iniciarSesion(){
        usuarioInterface = retrofitUser.create(UsuarioInterface.class);
        Call<Usuario> call = usuarioInterface.getUserRegister(userName.getText().toString(),password.getText().toString() );
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (!response.isSuccessful()) {
                    Log.e("Response err: fallo en logIn", response.message());
                    return;
                }
                Usuario userData = response.body();
                if(userData != null){
                    Usuario.setInstance(userData);

                    Intent goMain = new Intent(Login_SignUP.this,MainActivity.class);
                    startActivity(goMain);
                }else{
                    Toast.makeText(Login_SignUP.this, "Error. Comprueba los datos", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {

            }
        });
    }
    public void getTemas(){
        //Se obteine los temas de la database
        TemaInterface temaInterface = Login_SignUP.retrofitTemas.create(TemaInterface.class);
        Call<List<Tema>> call = temaInterface.getAll();
        call.enqueue(new Callback<List<Tema>>() {

            @Override
            public void onResponse(Call<List<Tema>> call, Response<List<Tema>> response) {
                if (!response.isSuccessful()) {
                    Log.e("Response err: ", response.message());
                    return;
                }
                List<Tema> temporalList = response.body();
                assert temporalList != null;
                listaTemas.addAll(temporalList);
            }

            @Override
            public void onFailure(Call<List<Tema>> call, Throwable t) {

            }
        });
    }
    //Cargo los diferentes idiomas que tenemos hasta ahora
    public void cargarIdioma(){
    listLanguages = findViewById(R.id.listaLanguages);
    LanguageItemAdapter languageAdapter = new LanguageItemAdapter(Login_SignUP.this);
    listLanguages.setAdapter(languageAdapter);
    listLanguages.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            // actualizamos el idioma del elemento seleccionado
            setAppLanguage(parent.getItemAtPosition(position).toString().toLowerCase());

        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    });

    }

    public void setAppLanguage(String language){
        //actualiamos los cambios para toda la app al idioma elegido
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        //Actualizamos la vista para que se cargue el cambio de idioma en la activity presente
        usernameHint.setHint(getString(R.string.usuario));
        passwordHint.setHint(getString(R.string.password));
        buttonLogin.setText(getString(R.string.boton_iniciar_sesion));
        buttonSignUp.setText(getString(R.string.boton_resgistrarse));

    }

}