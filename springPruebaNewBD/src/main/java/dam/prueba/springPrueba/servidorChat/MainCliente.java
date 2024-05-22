package dam.prueba.springPrueba.servidorChat;

import dam.prueba.springPrueba.Class.Message;
import dam.prueba.springPrueba.models.Conversacion;
import dam.prueba.springPrueba.models.Like;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class MainCliente {

    public static void main(String[] args) {

        try {

            Socket socket = new Socket("10.94.30.45", 6565);

            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            //se envia el idGrupo al servidor
            dos.writeLong(1L);

            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            Scanner s = new Scanner(System.in);
            Message respuesta;
            Like mensaje2;
            Message mensaje;

            //esto será colOcado en el lugar donde se envia mensajes
                System.out.println("Introduzca el mensaje que vas a enviar al chat:");
                mensaje = new Message(new Conversacion(null,1,"",s.nextLine(),"1"),1,"a");
                mensaje2 = new Like(1,1,1);
                //CUANDO SE EVNIA UN MENSAJE VENDRA ACOMPAÑADO DEL BOOLEANO FALSE
                oos.writeBoolean(false);
                oos.writeObject(mensaje2);
                oos.flush();
                oos.writeBoolean(true);
                oos.flush();
            //--------------------------------------------------------------------

            //Esto será colocado en el hilo que se encargará de recibir los mensajes nuevos en el chat.
                respuesta = (Message) ois.readObject();
                if (respuesta == null){
                    System.out.println("null");
                }else {
                    System.out.print("Mensaje recibido del servidor con el nombre: ");
                    System.out.println(respuesta.getNombreUsuario());
                    System.out.println("Este es el contenido del mensaje: "+respuesta.getConversacion().getContenido());
                }
                     //------------------------------------------------------------------------


            // Cierre de todas las conexiones o streams de datos

            //CUANDO SALGAMOS DEl CHAT SE ENVIARA EL BOOLEAO TRUE PA
            //terminar el bucle
            oos.writeBoolean(true);
                oos.flush();
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
