package com.prueba.foroex_app.Class.ThreadChat;

import android.util.Log;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputEditText;
import com.prueba.foroex_app.MainActivity;
import com.prueba.foroex_app.RecyclerViews.Adapters.ChatRvAdapter;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ConnectionChat extends Thread{
    public static boolean endChat;
    private final ImageView sendMess;
    private final TextInputEditText text;
    private final Integer idGrupoUsuario;
    private final long IDGRUPO;
    private final ChatRvAdapter chat;


    public void run(){

        try {

            Socket socket = new Socket(MainActivity.IP, 6565);
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            //se envia el idGrupo al servidor
            dos.writeLong(IDGRUPO);
            dos.flush();

            SendMessage sendMessageThread = new SendMessage(socket,sendMess,text,idGrupoUsuario );
            ReciveMessage reciveMessageThread = new ReciveMessage(socket,chat);

            sendMessageThread.start();
            reciveMessageThread.start();

            sendMessageThread.join();
            reciveMessageThread.join();
            socket.close();


        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        Log.d("HILO", "run: MUERTO");

    }
    public ConnectionChat (long IDGRUPO,ImageView sendMess, TextInputEditText text, Integer idGrupoUsuario, ChatRvAdapter chat)  {
        this.IDGRUPO=IDGRUPO;
        this.sendMess=sendMess;
        this.text=text;
        this.idGrupoUsuario=idGrupoUsuario;
        this.chat=chat;
        setEndChat(false);
    }
    public boolean isEndChat() {
        return endChat;
    }

    public void setEndChat(boolean endChat) {
        this.endChat = endChat;
    }

}
