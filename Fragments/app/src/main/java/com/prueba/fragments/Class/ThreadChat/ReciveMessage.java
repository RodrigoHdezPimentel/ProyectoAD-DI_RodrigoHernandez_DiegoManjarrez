package com.prueba.fragments.Class.ThreadChat;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.prueba.fragments.ChatActivity;
import com.prueba.fragments.Class.Message;
import com.prueba.fragments.RecyclerViews.Adapters.ChatRvAdapter;
import com.prueba.fragments.RetrofitConnection.Models.Conversacion;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Arrays;

public class ReciveMessage extends Thread{
    private final Socket socket;
    private final ChatRvAdapter chat;
    private String[] respuesta;
    private final Handler handler;

    public  void run(){

        try {
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    while (!ConnectionChat.endChat) {
                        try {
                        if ((respuesta = (String[]) ois.readObject()) != null) {
                            Log.d("LLEGO", "hola");

                            handler.post(() -> chat.mensajeNuevo(new Message(new Conversacion(
                                    Integer.valueOf(respuesta[0]), Integer.valueOf(respuesta[1]),
                                    respuesta[2], respuesta[3], respuesta[4]),
                                    Integer.valueOf(respuesta[5]), respuesta[6])));

                                   }
                        }catch (SocketException e) {
                            break;
                    }
                //ME CANSË DE INTENTARLO DURANTE 5 HORAS
            }


            Log.d("SALIDO", "siuuuuuuuuuuuu");
            ois.close();
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    public ReciveMessage(Socket socket,ChatRvAdapter chat ){
        this.socket=socket;
        this.chat=chat;
        this.handler = new Handler(Looper.getMainLooper());

    }
}
