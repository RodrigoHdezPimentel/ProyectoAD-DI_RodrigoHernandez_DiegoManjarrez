package dam.prueba.spring_boot_foroex.servidorChat;


import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Servidor {

    public static ConcurrentHashMap<Long, ArrayList<ChatUsuario>> chatConexiones = new ConcurrentHashMap<>();
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
                for (Map.Entry<Long, ArrayList<ChatUsuario>> entry : chatConexiones.entrySet()) {
                    Long id = entry.getKey();
                    System.out.println("chat: "+id);
                        System.out.println("usuarios: "+entry.getValue().size());

                }


            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public synchronized static void agregarUsuarioChat(ChatUsuario chatUsuario){
        chatConexiones.computeIfAbsent(chatUsuario.getIdGrupo(), k -> new ArrayList<>()).add(chatUsuario);

    }


    public synchronized static void eliminarUsuarioChat(ChatUsuario chatUsuario){
       if(chatConexiones.containsKey(chatUsuario.getIdGrupo())){
            chatConexiones.get(chatUsuario.getIdGrupo()).remove(chatUsuario);

            if(chatConexiones.get(chatUsuario.getIdGrupo()).isEmpty()){
                chatConexiones.remove(chatUsuario.getIdGrupo());

            }
       }

    }


}
