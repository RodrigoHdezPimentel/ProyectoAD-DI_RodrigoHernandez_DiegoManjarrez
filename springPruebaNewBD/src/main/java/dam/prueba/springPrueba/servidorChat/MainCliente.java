package dam.prueba.springPrueba.servidorChat;

import dam.prueba.springPrueba.Class.LoadConversation;
import dam.prueba.springPrueba.controllers.ConversacionController;
import dam.prueba.springPrueba.controllers.UsuarioController;
import dam.prueba.springPrueba.models.Conversacion;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class MainCliente {

    public static void main(String[] args) {

        try {
            //HAGA va colocada el retofit de guardar la conversaicon cuando se envia el mensaje por el chat
            //ConversacionController conversacionController = new ConversacionController();
            //Conversacion conversacion = new Conversacion();

            Socket socket = new Socket("10.94.30.45", 6565);

            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            //se envia el idGrupo al servidor
            dos.writeLong(1L);

            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            Scanner s = new Scanner(System.in);
            LoadConversation respuesta;
            LoadConversation mensaje;



                System.out.println("Introduzca el mensaje que vas a enviar al chat:");
                mensaje = new LoadConversation(new Conversacion(null,1,"",s.nextLine(),"1"),1,"a");

                oos.writeObject(mensaje);
                oos.flush();

                respuesta = (LoadConversation) ois.readObject();
                System.out.println("Mensajerecibido del servidor");
                System.out.println(respuesta.getNombreUsuario());
                System.out.println("Este es el contenido del mensaje: "+respuesta.getConversacion().getContenido());


            // Cierre de todas las conexiones o streams de datos
            //terminar el bucle
            dos.writeBoolean(true);
            s.close();
            oos.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

}
