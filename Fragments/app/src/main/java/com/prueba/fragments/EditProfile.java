package com.prueba.fragments;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.prueba.fragments.RetrofitConnection.Interfaces.UsuarioInterface;
import com.prueba.fragments.RetrofitConnection.Models.Usuario;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfile extends AppCompatActivity {
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

        ImageView back = findViewById(R.id.backEditProfile);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toMain = new Intent(EditProfile.this, MainActivity.class);
                startActivity(toMain);
            }
        });
        ImageView confirm = findViewById(R.id.check);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.setName(userName.getText().toString());
                user.setDescripcion(descripcion.getText().toString());
                user.setPass(password.getText().toString());
                updateUser(user);
                Intent toMain = new Intent(EditProfile.this, MainActivity.class);
                startActivity(toMain);
            }
        });
        Button delete = findViewById(R.id.deleteBut);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarAlertDialog();
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
                return;
                }else{
                    Toast.makeText(EditProfile.this, "Datos actualizados", Toast.LENGTH_SHORT).show();
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
    public void deleteUser(Integer id){
        usuarioInterface = Login_SignUP.retrofitUser.create(UsuarioInterface.class);
        Call<Boolean> call = usuarioInterface.delete(Usuario.getInstance().getId());
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(EditProfile.this, "Error en la Respuesta", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(EditProfile.this, response.body().toString(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.e("Thorw err: ", t.getMessage());
            }
        });
    }
    private AlertDialog alertDialog;

    private void mostrarAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Inflar el diseño personalizado
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_delete_account, null);
        builder.setView(dialogView);

        // Obtener referencias de los botones personalizados
        Button buttonConfirmar = dialogView.findViewById(R.id.confirmDelete);
        Button buttonCancelar = dialogView.findViewById(R.id.cancelDelete);

        // Configurar controladores de clic para los botones
        buttonConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acciones al confirmar
                // Por ejemplo: actualizarPerfil();
                deleteUser(Usuario.getInstance().getId());
                Intent toLogIn = new Intent(EditProfile.this, Login_SignUP.class);
                startActivity(toLogIn);
            }
        });

        buttonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acciones al cancelar
               alertDialog.dismiss();  // Dismiss the dialog when Cancel button is clicked

            }
        });
        alertDialog = builder.create();
        alertDialog.show();
    }
}