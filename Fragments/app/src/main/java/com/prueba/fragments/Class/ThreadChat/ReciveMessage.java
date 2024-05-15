package com.prueba.fragments.Class.ThreadChat;

import android.content.Context;
import android.widget.Toast;

import com.prueba.fragments.ChatActivity;
import com.prueba.fragments.Class.Message;
import com.prueba.fragments.RecyclerViews.Adapters.ChatRvAdapter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ReciveMessage extends Thread{

    private final Socket socket;
    private final ChatRvAdapter chat;


    public  void run(){

        try {
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            while (!ConnectionChat.endChat){
                if((Message) ois.readObject() != null){
                    chat.mensajeNuevo((Message) ois.readObject());
                }
            }
            ois.close();
            socket.close();
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    public ReciveMessage(Socket socket,ChatRvAdapter chat ){
        this.socket=socket;
        this.chat=chat;
    }
}
