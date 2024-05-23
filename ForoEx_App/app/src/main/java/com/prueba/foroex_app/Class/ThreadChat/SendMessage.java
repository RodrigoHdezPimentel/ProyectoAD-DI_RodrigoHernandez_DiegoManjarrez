package com.prueba.foroex_app.Class.ThreadChat;


import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.google.android.material.textfield.TextInputEditText;
import com.prueba.foroex_app.MainActivity;
import com.prueba.foroex_app.RetrofitConnection.Models.Conversacion;
import com.prueba.foroex_app.RetrofitConnection.Models.Usuario;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendMessage extends Thread{
    private ImageView sendMess;
    private  TextInputEditText text;
    private final Integer idGrupoUsuario;
    protected String[] mensaje;
    private final Socket socket;
    private boolean newMensaje;

    public void run() {

        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            sendButton();

            while (!ConnectionChat.endChat){
                    if(newMensaje){
                        oos.writeBoolean(false);
                        oos.flush();

                        Log.d("Entre", Arrays.toString(mensaje));

                        oos.writeObject(mensaje);
                        oos.flush();

                        newMensaje=false;
                    }

            }
            oos.writeBoolean(true);
            oos.flush();
            oos.close();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public void guardarConversacion(){
        Log.d("PRUEBA", "hola 1 ");
        Conversacion newConversacion = new Conversacion(null, idGrupoUsuario, getDateSpain(), Objects.requireNonNull(text.getText()).toString(),  Usuario.getInstance().getId().toString());
        Call<Conversacion> call = MainActivity.conversacionInterface.save(newConversacion);
        call.enqueue(new Callback<Conversacion>() {
            @Override
            public void onResponse(@NonNull Call<Conversacion> call, @NonNull Response<Conversacion> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                    String[] mensajeToSend = new String[7];
                    mensajeToSend[0] = response.body().getIdConversacion().toString();
                    mensajeToSend[1] = response.body().getIdGrupoUsuario().toString();
                    mensajeToSend[2] = response.body().getFecha();
                    mensajeToSend[3] = response.body().getContenido();
                    mensajeToSend[4] = response.body().getIdleido();
                    mensajeToSend[5] = Usuario.getInstance().getId().toString();
                    mensajeToSend[6] = Usuario.getInstance().getName();
                    newMensaje = true;
                    mensaje = Arrays.copyOf(mensajeToSend, mensajeToSend.length);
                    text.setText("");

//                new Message(response.body(),Usuario.getInstance().getId(),Usuario.getInstance().getName());
                // Actualizar la interfaz de usuario en el hilo principal

                //ACA SE DEBE COLOCAR EL ENVIO DEL MENSAJE AL SERVIDOR SOCKET
            }
            @Override
            public void onFailure(@NonNull Call<Conversacion> call, @NonNull Throwable t) {
            }
        });
    }

public SendMessage(Socket socket,ImageView sendMess, TextInputEditText text, Integer idGrupoUsuario){
        this.socket=socket;
    this.sendMess=sendMess;
    this.text=text;
    this.idGrupoUsuario=idGrupoUsuario;

}


    public String getDateSpain(){
        Date date = new Date();
        //Zona
        TimeZone tz = TimeZone.getTimeZone("Europe/Madrid");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        sdf.setTimeZone(tz);
        return sdf.format(date);

    }

    public void sendButton(){
        sendMess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!text.getText().toString().isEmpty()){
                    guardarConversacion();
                }
            }});
    }

    public ImageView getSendMess() {
        return sendMess;
    }



    public TextInputEditText getText() {
        return text;
    }

    public void setText(TextInputEditText text) {
        this.text = text;
    }

    public Integer getIdGrupoUsuario() {
        return idGrupoUsuario;
    }


}
