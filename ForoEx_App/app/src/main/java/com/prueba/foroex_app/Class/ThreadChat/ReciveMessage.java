package com.prueba.foroex_app.Class.ThreadChat;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.prueba.foroex_app.Class.Message;
import com.prueba.foroex_app.RecyclerViews.Adapters.ChatRvAdapter;
import com.prueba.foroex_app.RetrofitConnection.Models.Conversacion;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;

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
