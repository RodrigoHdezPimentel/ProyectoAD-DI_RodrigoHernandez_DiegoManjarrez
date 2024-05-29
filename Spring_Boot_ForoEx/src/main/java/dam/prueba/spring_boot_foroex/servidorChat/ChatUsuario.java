package dam.prueba.spring_boot_foroex.servidorChat;

import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

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
            throw new RuntimeException(e);
        }

    }

    public void run() {
        try {

            String [] mensaje;

            while(!ois.readBoolean()){
                mensaje =  (String []) ois.readObject();
                System.out.println("mensaje recibido de : " + Arrays.toString(mensaje));
                enviarMensaje(mensaje);

                //Se envía los mensajes a todos los conectados qe estén presentes en el chat y en ese gurpo(chat)
            }

            System.out.println("Cerrado");
             oos.writeObject(null);
            oos.flush();
            Servidor.eliminarUsuarioChat(this);
            oos.close();
            ois.close();
            socket.close();
        }  catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void enviarMensaje(String [] mensaje ) {
        ArrayList<ChatUsuario> listaUsuarios = new ArrayList<>(Servidor.chatConexiones.get(idGrupo));
        try {
            for (ChatUsuario usuario: listaUsuarios){
                System.out.println("mensaje para el grupo:"+usuario.idGrupo);
                System.out.println("Con socket: "+ socket.getInetAddress().getHostName());
                oos.writeObject(mensaje);
                oos.flush(); // Asegurar que los datos se envíen
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}