package com.prueba.fragments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.prueba.fragments.Registro.SelectTopic;
import com.prueba.fragments.RetrofitConnection.Interfaces.UsuarioInterface;
import com.prueba.fragments.RetrofitConnection.Models.Usuario;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfile extends AppCompatActivity {
    Button buttonRegistrar;
    Button buttonConfirmar;
    Button buttonEliminar;

    TextInputEditText userName;
    TextInputEditText password;
    TextInputEditText descripcion;

    UsuarioInterface usuarioInterface;

    Usuario user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        userName = findViewById(R.id.inputUserNameUpdate);
        password = findViewById(R.id.inputPasswordUpdate);
        descripcion = findViewById(R.id.inputDescripcionUpdate);

        cargarUserResgitrado();

        //Dentro de un buton de confirmar
//        user.setName(userName.getText().toString());
//        user.setDescripcion(descripcion.getText().toString());
//        user.setPass(password.getText().toString());
//        updateUser(user);
        //----------------------------------

//        userName.setText(user.getName());
//        password.setText(user.getPass());
//        descripcion.setText(user.getDescripcion());
        ImageView back = findViewById(R.id.backEditProfile);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toMain = new Intent(EditProfile.this, MainActivity.class);
                startActivity(toMain);
            }
        });

    }

    public void updateUser(Usuario user){
        usuarioInterface = Login_SignUP.retrofitUser.create(UsuarioInterface.class);
        Call<Usuario> call = usuarioInterface.update(user);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(EditProfile.this, "Error en la Respuesta", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.e("Thorw err: ", t.getMessage());
            }
        });

    }
    public void cargarUserResgitrado(){
        usuarioInterface = Login_SignUP.retrofitUser.create(UsuarioInterface.class);
        Call<Usuario> call = usuarioInterface.getUserById(Usuario.getInstance().getId());
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(EditProfile.this, "Error en la Respuesta", Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(EditProfile.this, response.body().getName(), Toast.LENGTH_SHORT).show();
                user = response.body();
                userName.setText(user.getName());
                password.setText(user.getPass());
                descripcion.setText(user.getDescripcion());
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.e("Thorw err: ", t.getMessage());
            }
        });
    }
}