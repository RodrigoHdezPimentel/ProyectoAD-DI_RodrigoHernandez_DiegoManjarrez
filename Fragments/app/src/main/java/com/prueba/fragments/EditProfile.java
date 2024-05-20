package com.prueba.fragments;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.prueba.fragments.Fragments.MainFragment.Profile;

import com.prueba.fragments.RetrofitConnection.Models.Tema;
import com.prueba.fragments.RetrofitConnection.Models.Usuario;
import com.prueba.fragments.RetrofitConnection.Models.UsuarioTema;
import com.prueba.fragments.RetrofitConnection.Models.UsuarioTemaFK;

import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfile extends AppCompatActivity {
    TextInputEditText userName;
    TextInputEditText password;
    TextInputEditText descripcion;

   //para el fondo de la activity
    ConstraintLayout con;
    ProgressBar progressBar;
    //los ids de los temas que ya tiene el usaurio regsitrado en la tabla
    List<UsuarioTema> UsuarioTemasIds;
    ImageView fotoPerfil;
    ImageView updateFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_profile);
        userName = findViewById(R.id.inputUserNameUpdate);
        password = findViewById(R.id.inputPasswordUpdate);
        descripcion = findViewById(R.id.inputDescripcionUpdate);
        fotoPerfil = findViewById(R.id.fotoEditProfle);
        updateFoto = findViewById(R.id.updatePicture);
        //se rellena los datos en los texView
        userName.setText(Usuario.getInstance().getName());
        password.setText(Usuario.getInstance().getPass());
        descripcion.setText(Usuario.getInstance().getDescripcion());

        MainActivity.addPicture(fotoPerfil, EditProfile.this,Profile.perfil.getFoto());
        cambiarFoto();


        ImageView back = findViewById(R.id.backEditProfile);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toMain = new Intent(EditProfile.this, MainActivity.class);
                toMain.putExtra("numFrgMain", 3);
                startActivity(toMain);
            }
        });
        ImageView confirm = findViewById(R.id.check);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Usuario.getInstance().setName(userName.getText().toString());
                Usuario.getInstance().setDescripcion(descripcion.getText().toString());
                Usuario.getInstance().setPass(password.getText().toString());
                updateUser();
                Intent toMain = new Intent(EditProfile.this, MainActivity.class);
                toMain.putExtra("numFrgMain", 3);
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

        Button updateTema = findViewById(R.id.editTemasProfile);
        updateTema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                con = findViewById(R.id.fondoAppEditProfile);
                con.setAlpha(0.5f);
                showDialogThemes();}
        });
    }

    public void updateUser(){
        Call<Usuario> call = MainActivity.usuarioInterface.update(Usuario.getInstance());
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

    public void deleteUser(){
        Call<Boolean> call = MainActivity.usuarioInterface.delete(Usuario.getInstance().getId());
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
        View dialogView = inflater.inflate(R.layout.dialog_delete_account, null);
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
                deleteUser();
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
    public void showDialogThemes(){
            final Dialog dialog = new Dialog(this);

            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_edit_theme);
            ChipGroup chipGroup = dialog.findViewById(R.id.chipGroupEditProfile);

            progressBar = dialog.findViewById(R.id.progressBar);
            //cargamos los chips del usuarioRegistrado
            getTemas(chipGroup);

            Button cancellButton = dialog.findViewById(R.id.cancellButtonEditTheme);
            Button confirmarButton = dialog.findViewById(R.id.confirmarButtoneEditTheme);
            cancellButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    con.setAlpha(1f);
                    dialog.dismiss();
                }
            });
            confirmarButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    con.setAlpha(1f);
                    editarTemas(chipGroup);
                    dialog.dismiss();
                    Toast.makeText(EditProfile.this, "Temas Actualizados", Toast.LENGTH_SHORT).show();
                }});

            // Muestra el diálogo
            dialog.show();

    }

    public void cargarChips(ChipGroup chipGroup, ArrayList<Tema> temas){

        Call<List<UsuarioTema>> call = MainActivity.usuarioTemaInterface.getAllTemaFromId(Usuario.getInstance().getId());
        call.enqueue(new Callback<List<UsuarioTema>>() {
            @Override
            public void onResponse(Call<List<UsuarioTema>> call, Response<List<UsuarioTema>> response) {
                if(response.isSuccessful()){
                    //comparacion de iDTema del user para colocar los ischecked de los chips

                    for (int i = 0; i < temas.size(); i++){
                        //Personalizacion de los chips
                        Chip newChip = new Chip(EditProfile.this);
                        ChipDrawable chipDrawable = ChipDrawable.createFromAttributes(EditProfile.this, null, 0, com.google.android.material.R.style.Widget_Material3_Chip_Filter);
                        newChip.setText(temas.get(i).getTitulo());
                        newChip.setText(temas.get(i).getTitulo());
                        newChip.setId(temas.get(i).getId());
                        newChip.setChipDrawable(chipDrawable);

                        UsuarioTemasIds = response.body();
                        for(int u = 0; u < UsuarioTemasIds.size(); u++){
                            //colocar el ischecked
                            if(UsuarioTemasIds.get(u).getId().getIdTema() == temas.get(i).getId()){
                                newChip.setChecked(true);
                            }
                        }
                        chipGroup.addView(newChip);
                    }
                    progressBar.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<List<UsuarioTema>> call, Throwable t) {

            }
        });

    }
    public void getTemas(ChipGroup chipGroup){
        //Se obteine los temas de la database
        Call<List<Tema>> call = MainActivity.temaInterface.getAll();
        call.enqueue(new Callback<List<Tema>>() {

            @Override
            public void onResponse(Call<List<Tema>> call, Response<List<Tema>> response) {
                if (!response.isSuccessful()) {
                    Log.e("Response err: ", response.message());
                    return;
                }
                   cargarChips(chipGroup, (ArrayList<Tema>) response.body());
            }

            @Override
            public void onFailure(Call<List<Tema>> call, Throwable t) {

            }
        });
    }

    public void editarTemas(ChipGroup chipGroup){
        //Para que me busque los que no coinciden
        boolean idTemaChipEncontrado;

        //metodo para agregar los idTemas de los temas para agregar a la tabla
        for (int i = 0; i < chipGroup.getChildCount(); i++) {
            idTemaChipEncontrado=false;
            View child = chipGroup.getChildAt(i);
            if(((Chip) child).isChecked()) {

                //se hace la comparacion de los chips que están seleccionados con
                //los idTemas que ya está dentro de la tabla del user
                for (int ut = 0; ut < UsuarioTemasIds.size(); ut++) {
                    if (UsuarioTemasIds.get(ut).getId().getIdTema() == child.getId()) {
                        idTemaChipEncontrado = true;
                        break;
                    }
                }
                //acá se agrega
                if (!idTemaChipEncontrado){
                    crearUserTema(child.getId());
                }
            }

        }

        //metodo para sacar los idTemas para eliminar de la tabla
        for (int i = 0; i < UsuarioTemasIds.size(); i++) {
            idTemaChipEncontrado=false;

                for (int ut = 0; ut < chipGroup.getChildCount(); ut++) {
                    View child = chipGroup.getChildAt(ut);
                    if(((Chip) child).isChecked()){

                        if (UsuarioTemasIds.get(i).getId().getIdTema() == child.getId()) {
                            idTemaChipEncontrado = true;
                            break;
                        }
                    }

                }
                if (!idTemaChipEncontrado){
                    deleteUserTema(UsuarioTemasIds.get(i).getId().getIdTema());
                }
        }

    }
    public void crearUserTema(int idTema){
        UsuarioTema userTema = new UsuarioTema(new UsuarioTemaFK(Usuario.getInstance().getId(),idTema));
        Call <UsuarioTema> call = MainActivity.usuarioTemaInterface.create(userTema);
        call.enqueue(new Callback<UsuarioTema>() {
            @Override
            public void onResponse(Call<UsuarioTema> call, Response<UsuarioTema> response) {
                if(!response.isSuccessful()){
                    return;
                }
            }

            @Override
            public void onFailure(Call<UsuarioTema> call, Throwable t) {

            }
        });
    }
    public void deleteUserTema(int idTema){
        Call <Boolean> call = MainActivity.usuarioTemaInterface.removeTemaUser(idTema, Usuario.getInstance().getId());
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (!response.isSuccessful()){
                    return;
                }
            }
            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });

    }

    // PARA ESCOGER UNAFOTO DESDE LA GALERIA DEL MOVIL
    public void cambiarFoto() {
        ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
                registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                    if (uri != null) {
                        Log.d("PhotoPicker", "Selected URI: " + uri);
                        fotoPerfil.setImageURI(uri);

                        try {
                            actualizarFotoUser(getFileFromUri(uri));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                    } else {
                        Log.d("PhotoPicker", "No media selected");
                    }
                });
        updateFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickMedia.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                        .build());
            }
        });
    }

    // Convierte la imagen seleccionada en un archivo
    private File getFileFromUri(Uri uri) throws IOException {
        File file = new File(getCacheDir(), "temp_image");
        try (InputStream inputStream = getContentResolver().openInputStream(uri);
             OutputStream outputStream = new FileOutputStream(file)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
        }
        return file;
    }

    // Actualiza la foto del usuario en el servidor
    // Actualiza la foto del usuario en el servidor
    public void actualizarFotoUser(File file) {
        // Crear un RequestBody de archivo
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        // MultipartBody.Part está utilizado para enviar el archivo real con su nombre
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), requestFile);

        // Realizar la llamada a Retrofit
        Call<String> call = MainActivity.fileInterface.saveImageApp(body);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Log.d("Upload", "Success: " + response.body());
                    Toast.makeText(EditProfile.this, response.body() + " VAMOOOS", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("Upload", "Failed: " + response.message());
                    Toast.makeText(EditProfile.this, " NOOOOOOOO", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("Upload error:", t.getMessage());
            }
        });
    }

}