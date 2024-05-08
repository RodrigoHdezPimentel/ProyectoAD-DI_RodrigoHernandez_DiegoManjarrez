package com.prueba.fragments.Class.ThreadChat;

import com.prueba.fragments.ChatActivity;
import com.prueba.fragments.Class.Message;
import com.prueba.fragments.RecyclerViews.Adapters.ChatRvAdapter;

import java.io.IOException;
import java.io.ObjectInputStream;

public class ReciveMessage extends Thread{
    protected ObjectInputStream ois;
    private final ConnectionChat connectionChat;
    private final ChatRvAdapter chat;


    public  void run(){

        try {
            while (!connectionChat.isEndChat() && Thread.currentThread().isInterrupted()){
                chat.mensajeNuevo((Message) ois.readObject());
            }

        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    public  ReciveMessage (ConnectionChat connectionChat,ChatRvAdapter chat ){
        this.connectionChat=connectionChat;
        this.chat=chat;
    }
}
