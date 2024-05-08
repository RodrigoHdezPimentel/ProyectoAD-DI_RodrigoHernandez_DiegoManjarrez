package com.prueba.fragments.Class.ThreadChat;

import com.prueba.fragments.MainActivity;
import com.prueba.fragments.RetrofitConnection.Models.Usuario;

import java.io.IOException;
import java.net.Socket;

public class ConnectionChat {
    private boolean endChat;
    private final int puerto = 6565;
    public ConnectionChat () {

    }

    public Socket getSocket() throws IOException {
        Socket socket;
        return socket = new Socket(MainActivity.IP, puerto);
    }

    public boolean isEndChat() {
        return endChat;
    }

    public void setEndChat(boolean endChat) {
        this.endChat = endChat;
    }
}
