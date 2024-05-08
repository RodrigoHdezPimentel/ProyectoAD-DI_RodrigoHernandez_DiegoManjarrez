package com.prueba.fragments.Class.ThreadChat;


import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.google.android.material.textfield.TextInputEditText;
import com.prueba.fragments.Class.Message;
import com.prueba.fragments.MainActivity;
import com.prueba.fragments.RetrofitConnection.Models.Conversacion;
import com.prueba.fragments.RetrofitConnection.Models.Usuario;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendMessage extends Thread{
    //valor por default por si no hay respuesta en la query de ultimo mensaje
    private final ImageView sendMess;
    private  TextInputEditText text;
    private final Integer idGrupoUsuario;
    protected DataOutputStream dos;
    protected Message mensaje;
    protected ObjectOutputStream oos;

    private ConnectionChat connectionChat;

    public void run(){

        sendMessage();

        try {
            dos = new DataOutputStream(connectionChat.getSocket().getOutputStream());
            oos = new ObjectOutputStream(connectionChat.getSocket().getOutputStream());
            //se envia el idGrupo al servidor
            dos.writeLong(1L);

           while (!connectionChat.isEndChat()){}
            //CUANDO SE EVNIA UN MENSAJE VENDRA ACOMPAÑADO DEL BOOLEANO FALSE

            //CUANDO SALGAMOS DEl CHAT SE ENVIARA EL BOOLEAO TRUE PA
            //terminar el bucle (servidor hilo)
            dos.writeBoolean(true);

            oos.close();
            dos.close();
            connectionChat.getSocket().close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public void GuardarConversacion(){
        Conversacion newConversacion = new Conversacion(null, idGrupoUsuario, getDateSpain(), Objects.requireNonNull(text.getText()).toString(),  Usuario.getInstance().getId().toString());
        Call<Conversacion> call = MainActivity.conversacionInterface.save(newConversacion);
        call.enqueue(new Callback<Conversacion>() {
            @Override
            public void onResponse(@NonNull Call<Conversacion> call, @NonNull Response<Conversacion> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                mensaje = new Message(response.body(),Usuario.getInstance().getId(),Usuario.getInstance().getName());
                try {
                    dos.writeBoolean(connectionChat.isEndChat());
                    oos.writeObject(mensaje);
                    oos.flush();

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


public SendMessage(ConnectionChat connectionChat, ImageView sendMess, TextInputEditText text, Integer idGrupoUsuario){
       this.connectionChat=connectionChat;
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

    public ConnectionChat getConnectionChat() {
        return connectionChat;
    }

    public void setConnectionChat(ConnectionChat connectionChat) {
        this.connectionChat = connectionChat;
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

    public DataOutputStream getDos() {
        return dos;
    }

    public void setDos(DataOutputStream dos) {
        this.dos = dos;
    }

    public Message getMensaje() {
        return mensaje;
    }

    public void setMensaje(Message mensaje) {
        this.mensaje = mensaje;
    }

    public ObjectOutputStream getOos() {
        return oos;
    }

    public void setOos(ObjectOutputStream oos) {
        this.oos = oos;
    }

}
