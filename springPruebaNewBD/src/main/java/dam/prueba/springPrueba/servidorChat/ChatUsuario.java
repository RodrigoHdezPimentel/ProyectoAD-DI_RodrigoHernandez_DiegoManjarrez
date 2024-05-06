package dam.prueba.springPrueba.servidorChat;

import dam.prueba.springPrueba.Class.LoadConversation;

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
            LoadConversation mensaje;

            do {
                mensaje = (LoadConversation) ois.readObject();
                System.out.println("mensaje recibido de : " + mensaje.getNombreUsuario());
                enviarMensaje(mensaje);

            } while (!dis.readBoolean());
            //BUSCAR UNA MANERA MAS COMODA DE TERMINAR EL BUCLE

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

    public void enviarMensaje(LoadConversation mensaje) {
        try {
            ArrayList<ChatUsuario> listaUsuarios = new ArrayList<>(Servidor.chatConexiones.get(idGrupo));
            for (ChatUsuario usuario: listaUsuarios){
                oos.writeObject(mensaje);
                oos.flush(); // Asegurar que los datos se envíen
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}