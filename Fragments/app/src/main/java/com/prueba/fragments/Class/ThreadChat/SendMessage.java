package com.prueba.fragments.Class.ThreadChat;


import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.textfield.TextInputEditText;
import com.prueba.fragments.ChatActivity;
import com.prueba.fragments.Class.Message;
import com.prueba.fragments.MainActivity;
import com.prueba.fragments.RetrofitConnection.Models.Conversacion;
import com.prueba.fragments.RetrofitConnection.Models.Like;
import com.prueba.fragments.RetrofitConnection.Models.Usuario;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendMessage extends Thread{
    private final ImageView sendMess;
    private  TextInputEditText text;
    private final Integer idGrupoUsuario;
    protected String[] mensaje = new String[6];
    protected ObjectOutputStream oos;
    private final Socket socket;
    private boolean newMensaje;

    @Override
    public void run() {

       // sendMessage();

                try {
                    oos = new ObjectOutputStream(socket.getOutputStream());

                    //CUANDO SE ENVÍA UN MENSAJE VENDRÁ ACOMPAÑADO DEL BOOLEANO FALSE
                    //CUANDO SALGAMOS DEL CHAT SE ENVIARÁ EL BOOLEANO TRUE PARA
                    //terminar el bucle (servidor hilo)

                    mensaje[0] = "1";
                    mensaje[1] = "1";
                    mensaje[2] = "1";
                    mensaje[3] = "1";
                    mensaje[4] = Usuario.getInstance().getId().toString();
                    mensaje[5] = Usuario.getInstance().getName();

                    // new Message(new Conversacion(null,1,"","aasasasas","1"),1,"a");
                    oos.writeBoolean(false);
                    oos.flush();

                    oos.writeObject(mensaje);
                    oos.flush();

                    oos.writeBoolean(true);
                    oos.flush();

                    socket.close();
                    oos.close();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
    public void GuardarConversacion(){
        Log.d("PRUEBA", "hola 1 ");
        Conversacion newConversacion = new Conversacion(null, idGrupoUsuario, getDateSpain(), Objects.requireNonNull(text.getText()).toString(),  Usuario.getInstance().getId().toString());
        Call<Conversacion> call = MainActivity.conversacionInterface.save(newConversacion);
        call.enqueue(new Callback<Conversacion>() {
            @Override
            public void onResponse(@NonNull Call<Conversacion> call, @NonNull Response<Conversacion> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                new Message(response.body(),Usuario.getInstance().getId(),Usuario.getInstance().getName());
                try {

                    Log.d("PRUEBA", "hola 3 ");

                    mensaje[0] = response.body().getIdGrupoUsuario().toString();
                    mensaje[1] = response.body().getFecha();
                    mensaje[2] = response.body().getIdleido();
                    mensaje[3] = response.body().getContenido();
                    mensaje[4] = Usuario.getInstance().getId().toString();
                    mensaje[5] = Usuario.getInstance().getName();

                  // new Message(new Conversacion(null,1,"","aasasasas","1"),1,"a");
                    oos.writeBoolean(false);
                    oos.flush();

                    oos.writeObject(mensaje);
                    oos.flush();
                    newMensaje = true;

                    Log.d("PRUEBA", "hola 4 ");

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                //ACA SE DEBE COLOCAR EL ENVIO DEL MENSAJE AL SERVIDOR SOCKET
                text.setText("");
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

    public void sendMessage(){
        sendMess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {GuardarConversacion();}});
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


    public ObjectOutputStream getOos() {
        return oos;
    }

    public void setOos(ObjectOutputStream oos) {
        this.oos = oos;
    }

}
