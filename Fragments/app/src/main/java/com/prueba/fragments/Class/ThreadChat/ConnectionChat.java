package com.prueba.fragments.Class.ThreadChat;

import android.util.Log;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputEditText;
import com.prueba.fragments.Class.Message;
import com.prueba.fragments.MainActivity;
import com.prueba.fragments.RecyclerViews.Adapters.ChatRvAdapter;
import com.prueba.fragments.RetrofitConnection.Models.Usuario;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectionChat extends Thread{
    public static boolean endChat;
    private ImageView sendMess;
    private final TextInputEditText text;
    private final Integer idGrupoUsuario;
    private SendMessage sendMessageThread;
    private Socket socket;
    private final long IDGRUPO;
    private final ChatRvAdapter chat;


    public void run(){

        try {

            socket = new Socket(MainActivity.IP, 6565);
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            //se envia el idGrupo al servidor
            dos.writeLong(IDGRUPO);
            dos.flush();

            sendMessageThread = new SendMessage(socket,sendMess,text,idGrupoUsuario );
            sendMessageThread.start();
            //ReciveMessage reciveMessageThread = new ReciveMessage(socket,chat);


            sendMessageThread.join();
            socket.close();
            Log.d("KIIIIIIIIII", "run: ");



        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


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

    public SendMessage getSendMessageThread() {
        return sendMessageThread;
    }


}
