package com.prueba.fragments;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.prueba.fragments.Class.AutoLogin;
import com.prueba.fragments.Fragments.MainFragment.Profile;

import com.prueba.fragments.RetrofitConnection.Interfaces.FileInterface;
import com.prueba.fragments.RetrofitConnection.Models.Tema;
import com.prueba.fragments.RetrofitConnection.Models.Usuario;
import com.prueba.fragments.RetrofitConnection.Models.UsuarioTema;
import com.prueba.fragments.RetrofitConnection.Models.UsuarioTemaFK;



import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class EditProfile extends AppCompatActivity {
    TextInputEditText userName, password, descripcion;
    Button botonConfirmarCambios, botonCancelarCambios, botonUpdateTema,
            botoDeleteCuenta, buttonLoginOut;
    FileInterface fileInterface;
    ConstraintLayout con;
    ProgressBar progressBar;
    //los ids de los temas que ya tiene el usaurio regsitrado en la tabla
    List<UsuarioTema> UsuarioTemasIds;
    ImageView fotoPerfil, updateFoto, back;
    String originalUserName, originalPassword, originalDescripcion;
    TextWatcher textWatcher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_profile);
        userName = findViewById(R.id.inputUserNameUpdate);
        password = findViewById(R.id.inputPasswordUpdate);
        descripcion = findViewById(R.id.inputDescripcionUpdate);
        fotoPerfil = findViewById(R.id.fotoEditProfle);
        updateFoto = findViewById(R.id.updatePicture);
        back = findViewById(R.id.backEditProfile);
        botoDeleteCuenta = findViewById(R.id.deleteBut);
        botonUpdateTema = findViewById(R.id.editTemasProfile);
        botonConfirmarCambios = findViewById(R.id.buttonConfirmarUpdates);
        botonCancelarCambios = findViewById(R.id.buttonCancellUpdates);
        buttonLoginOut = findViewById(R.id.logOutBut);

        //se rellena los datos en los texView
        userName.setText(Usuario.getInstance().getName());
        password.setText(Usuario.getInstance().getPass());
        descripcion.setText(Usuario.getInstance().getDescripcion());

        //guardamos los valores inicialess
        originalUserName = Usuario.getInstance().getName();
        originalPassword = Usuario.getInstance().getPass();
        originalDescripcion = Usuario.getInstance().getDescripcion();

        MainActivity.addPicture(fotoPerfil, EditProfile.this,Profile.perfil.getFoto());
        cambiarFoto();
        FileRetrofit();
        buttonListener();
        deteccionCambios();
        nuevosValores();


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
    public String getDateSpain(){
        Date date = new Date();
        //Zona
        TimeZone tz = TimeZone.getTimeZone("Europe/Madrid");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        sdf.setTimeZone(tz);

        return sdf.format(date).toString();

    }
    public void buttonListener(){
        botonUpdateTema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                con = findViewById(R.id.fondoAppEditProfile);
                con.setAlpha(0.5f);
                showDialogThemes();}
        });

        botoDeleteCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarAlertDialog();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toMain = new Intent(EditProfile.this, MainActivity.class);
                toMain.putExtra("numFrgMain", 3);
                startActivity(toMain);
            }
        });

        botonConfirmarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deshabilitarBotonesCambios();
                Usuario.getInstance().setName(userName.getText().toString());
                Usuario.getInstance().setDescripcion(descripcion.getText().toString());
                Usuario.getInstance().setPass(password.getText().toString());
                AutoLogin.setPrefUserPass(EditProfile.this, password.getText().toString());
                AutoLogin.setUserName(EditProfile.this, userName.getText().toString());
                updateUser();
                nuevosValores();


            }
        });
        botonCancelarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deshabilitarBotonesCambios();
                restaurarValoresOriginales();
            }
        });
        buttonLoginOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                Usuario.setInstance(null);
                AutoLogin.setUserName(EditProfile.this, null);
                AutoLogin.setPrefUserPass(EditProfile.this, null);
                Intent toListChat = new Intent(EditProfile.this, Login_SignUP.class);
                startActivity(toListChat);
            }
        });
    }
    public void deteccionCambios(){
       textWatcher = new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence s, int start, int count, int after) {

          }

          @Override
          public void onTextChanged(CharSequence s, int start, int before, int count) {
              habilitarBotonesCambios();

          }

          @Override
          public void afterTextChanged(Editable s) {

          }
      };
        userName.addTextChangedListener(textWatcher);
        password.addTextChangedListener(textWatcher);
        descripcion.addTextChangedListener(textWatcher);
    }
public void habilitarBotonesCambios(){
    botonCancelarCambios.setVisibility(View.VISIBLE);
    botonConfirmarCambios.setVisibility(View.VISIBLE);
    botonUpdateTema.setVisibility(View.INVISIBLE);
    botoDeleteCuenta.setVisibility(View.INVISIBLE);
}
    public void deshabilitarBotonesCambios(){
        botonCancelarCambios.setVisibility(View.INVISIBLE);
        botonConfirmarCambios.setVisibility(View.INVISIBLE);
        botonUpdateTema.setVisibility(View.VISIBLE);
        botoDeleteCuenta.setVisibility(View.VISIBLE);
    }
    public void restaurarValoresOriginales() {
        // Deshabilitar temporalmente el TextWatcher
        userName.removeTextChangedListener(textWatcher);
        password.removeTextChangedListener(textWatcher);
        descripcion.removeTextChangedListener(textWatcher);

        // Restaurar los valores originales
        userName.setText(originalUserName);
        password.setText(originalPassword);
        descripcion.setText(originalDescripcion);

        // Habilitar nuevamente el TextWatcher
        userName.addTextChangedListener(textWatcher);
        password.addTextChangedListener(textWatcher);
        descripcion.addTextChangedListener(textWatcher);
    }
    public void nuevosValores(){
        //guardamos los valores inicialess
        originalUserName = Usuario.getInstance().getName();
        originalPassword = Usuario.getInstance().getPass();
        originalDescripcion = Usuario.getInstance().getDescripcion();
    }
    public void reiniciarDeteccionCambios(){
        userName.removeTextChangedListener(textWatcher);
        password.removeTextChangedListener(textWatcher);
        descripcion.removeTextChangedListener(textWatcher);
       deteccionCambios();

    }
    // Método para deshabilitar el TextWatcher
    public void deleteUser(){
        Call<Void> call = MainActivity.usuarioInterface.delete(Usuario.getInstance().getId(), getDateSpain());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(EditProfile.this, "error", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(EditProfile.this, "Cuenta eliminada", Toast.LENGTH_SHORT).show();
                Usuario.setInstance(null);
                AutoLogin.setUserName(EditProfile.this, null);
                AutoLogin.setPrefUserPass(EditProfile.this, null);
                Intent toListChat = new Intent(EditProfile.this, Login_SignUP.class);
                startActivity(toListChat);
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
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
        File file = new File(getCacheDir(), "temp_image.jpg");
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
    public void FileRetrofit(){
        Retrofit retrofitFile;
        retrofitFile = new Retrofit.Builder()
                .baseUrl("http://" + MainActivity.IP + ":8086/file/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        fileInterface = retrofitFile.create(FileInterface.class);
    }

    // Actualiza la foto del usuario en el servidor
    // Actualiza la foto del usuario en el servidor
    public void actualizarFotoUser(File file) {
        // Crear un RequestBody de archivo
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        // MultipartBody.Part está utilizado para enviar el archivo real con su nombre
        MultipartBody.Part image = MultipartBody.Part.createFormData("image", file.getName(), requestFile);

        // Realizar la llamada a Retrofit
        Call<String> call = fileInterface.saveImageApp(image);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    atualizarFotoUseDB(response.body());
                    String beforeUpdate = Usuario.getInstance().getFoto();
                    deleteFotoServidor(beforeUpdate);
                    Usuario.getInstance().setFoto(response.body());
                }else {
                    Toast.makeText(EditProfile.this, "Error en la ruta", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("ERROR", "onFailure: "+t.getMessage());
            }
        });

    }
    public void deleteFotoServidor(String nameFile){
        Call<String> call = fileInterface.deleteImage(nameFile);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    Log.d("PHOTO", "onResponse: "+response.body());
                }else {
                    Toast.makeText(EditProfile.this, "Fallos en la ruta", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {}
        });
    }
    public void atualizarFotoUseDB(String namePhoto){

       Call<Void> call = MainActivity.usuarioInterface.updateFotoUser(Usuario.getInstance().getId(),namePhoto );
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Toast.makeText(EditProfile.this, "foto actualizada", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

}