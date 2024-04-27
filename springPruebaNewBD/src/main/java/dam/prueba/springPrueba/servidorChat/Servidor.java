package dam.prueba.springPrueba.servidorChat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Servidor {

    public static ArrayList<ChatUsuario> listaConexiones = new ArrayList<>();
    public static boolean endServidor;
    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(6565)) {

            while (!endServidor) {

                Socket socketCliente = serverSocket.accept();
                ChatUsuario newHilo = new ChatUsuario(socketCliente);
                for (int x = 0; x < listaConexiones.size(); x++) {

                    if (newHilo.getSocket().getInetAddress().getHostName()
                            .equals(listaConexiones.get(x).getSocket().getInetAddress().getHostName())) {

                        listaConexiones.remove(x);
                        break;
                    }
                }
                listaConexiones.add(newHilo);
                newHilo.start();

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
