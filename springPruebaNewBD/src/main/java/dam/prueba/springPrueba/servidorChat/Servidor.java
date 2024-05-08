package dam.prueba.springPrueba.servidorChat;

import dam.prueba.springPrueba.models.Usuario;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Servidor {

    public static HashMap<Long, ArrayList<ChatUsuario>> chatConexiones = new HashMap<Long, ArrayList<ChatUsuario>>() ;
    public static boolean endServidor;
    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(6565)) {
            DataInputStream dis;
            while (!endServidor) {

                Socket socketCliente = serverSocket.accept();
                //Acá recibe el idgrupo para colocarselo al hilo
                dis = new DataInputStream(socketCliente.getInputStream());
                long idGrupo = dis.readLong();

                ChatUsuario newHilo = new ChatUsuario(socketCliente,idGrupo);

                agregarUsuarioChat(newHilo);
//                System.out.println("mensaje recibido de : " + mensaje.getNombreUsuario());

                newHilo.start();
                System.out.println("Hola, mundo!");


            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void agregarUsuarioChat(ChatUsuario chatUsuario){
        if(!chatConexiones.containsKey(chatUsuario.getIdGrupo())){
            chatConexiones.put(chatUsuario.getIdGrupo(), new ArrayList<>());
            chatConexiones.get(chatUsuario.getIdGrupo()).add(chatUsuario);

        }else {
            chatConexiones.get(chatUsuario.getIdGrupo()).add(chatUsuario);
        }
    }


    public static void eliminarUsuarioChat(ChatUsuario chatUsuario){
       if(chatConexiones.containsKey(chatUsuario.getIdGrupo())){
            chatConexiones.get(chatUsuario.getIdGrupo()).remove(chatUsuario);

            if(!chatConexiones.containsKey(chatUsuario.getIdGrupo())){
                chatConexiones.remove(chatUsuario.getIdGrupo());
            }
       }

    }


}
