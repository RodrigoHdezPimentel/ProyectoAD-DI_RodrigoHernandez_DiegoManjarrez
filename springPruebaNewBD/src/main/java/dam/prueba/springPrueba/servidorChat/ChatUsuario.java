package dam.prueba.springPrueba.servidorChat;

import dam.prueba.springPrueba.Class.Message;

import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

@Getter
@Setter
public class ChatUsuario extends Thread {

    private Socket socket;
    private long idGrupo;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;


    public ChatUsuario(Socket socket, long idGrupo) {
        this.socket = socket;
        this.idGrupo = idGrupo;
        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void run() {
        try {

            DataInputStream dis = new DataInputStream(socket.getInputStream());
            Message mensaje;

            while(!dis.readBoolean()){
                mensaje = (Message) ois.readObject();
                System.out.println("mensaje recibido de : " + mensaje.getNombreUsuario());

                //Se envía los mensajes a todos los conectados qe estén presentes en el chat y en ese gurpo(chat)
                ArrayList<ChatUsuario> listaUsuarios = new ArrayList<>(Servidor.chatConexiones.get(idGrupo));
                for (ChatUsuario usuario: listaUsuarios){
                    System.out.println("mensaje para el grupo:"+usuario.idGrupo);
                    usuario.enviarMensaje(mensaje);
                }
            }

            System.out.println("Cerrado");
            Servidor.eliminarUsuarioChat(this);
            oos.close();
            ois.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void enviarMensaje(Message mensaje) {
        try {
            oos.writeObject(mensaje);
            oos.flush(); // Asegurar que los datos se envíen
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}