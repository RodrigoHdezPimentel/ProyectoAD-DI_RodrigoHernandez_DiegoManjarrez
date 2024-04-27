package dam.prueba.springPrueba.servidorChat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ChatUsuario extends Thread {
    private Socket socket;
    public ChatUsuario(Socket socketr) {
        this.socket = socket;
    }

    public void run() {
        try {

            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            String read;

            do {
                read =dis.readUTF();
                System.out.println("mensaje recibido: "+ read);
                break;
            }while(true);

            for(int i = 0; i < Servidor.listaConexiones.size(); i++) {

                if(Servidor.listaConexiones.get(i).getSocket().getInetAddress().getHostName().equals(
                        socket.getInetAddress().getHostName())) {
                    Servidor.listaConexiones.get(i).enviarMensaje("haz ganado");;
                }
                Servidor.listaConexiones.get(i).enviarMensaje("haz perdido");;
            }
            dos.close();
            dis.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }
    public void enviarMensaje(String texto) {
        try {
            DataOutputStream dos;
            dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF(texto);
            dos.close();
        } catch (Exception e) {

        }

    }

}
