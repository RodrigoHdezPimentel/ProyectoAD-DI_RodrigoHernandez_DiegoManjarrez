package com.prueba.foroex_app.Class.ThreadChat;


import android.content.Context;

import com.prueba.foroex_app.Class.Message;
import com.prueba.foroex_app.MainActivity;
import com.prueba.foroex_app.RecyclerViews.Adapters.ChatRvAdapter;
import com.prueba.foroex_app.RetrofitConnection.Interfaces.ConversacionInterface;
import com.prueba.foroex_app.RetrofitConnection.Models.Conversacion;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class oldModel extends Thread{
    //valor por default por si no hay respuesta en la query de ultimo mensaje
    private Message ultimoMensaje = new Message(new Conversacion(0), 0, "");
    private ChatRvAdapter chat;
    private boolean hiloEnded;
    private Integer idGrupo;
    Context context;
    private ConversacionInterface conversacionInterface;


    public void run(){
        //primero obtengo los datos del ultimo mensaje cuando se carga la conversación
        Call<Message> call = MainActivity.conversacionInterface.getLastMessage(idGrupo);
        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if(!response.isSuccessful()){
                    return;
                }
                ultimoMensaje = response.body();
                newMensaje();
            }
            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                newMensaje();
            }});
    }
    public void newMensaje() {
        Call<Message> call2 = MainActivity.conversacionInterface.getLastMessage(idGrupo);
        call2.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                //Comprobar que el idConversacion no sea igual al idConversacion del mensaje anterior
                if (!ultimoMensaje.getConversacion().getIdConversacion().equals(response.body().getConversacion().getIdConversacion())) {
                    ultimoMensaje = response.body();
//                    Toast.makeText(context, ultimoMensaje.getConversacion().getIdConversacion()+" ULT", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(context, response.body().getConversacion().getIdConversacion()+" NEW", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(context, "he entrado", Toast.LENGTH_SHORT).show();
                    chat.mensajeNuevo(ultimoMensaje);
                }
                //Tuve que hacerlo de esta forma por que si coloco esto en un bucle (petición)
                //habría entradas asincronas y entonces habrá en cola 3 peticiones
                //en vez de 1 peticion y el simulador se vuelve lento y habria fallos en el adapter
                if(!hiloEnded){
                    newMensaje();
                }//El Hilo terminará cuando ya no vuelva a entrar en el if
            }
            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                //Para asegurarnos que aunque no haya ningun mensaje en el chat vuelva hacer
                //la comprobacion hasta que llegue un mensaje nuevo
                if(!hiloEnded){
                    newMensaje();
                }
            }});

    }
    public oldModel(ChatRvAdapter chat, Integer idGrupo, Context context){
        this.chat = chat;
        this.idGrupo = idGrupo;
        this.context = context;
    }



    public ChatRvAdapter getChat() {
        return chat;
    }

    public void setChat(ChatRvAdapter chat) {
        this.chat = chat;
    }

    public boolean isHiloEnded() {
        return hiloEnded;
    }

    public void setHiloEnded(boolean hiloEnded) {
        this.hiloEnded = hiloEnded;
    }

    public Integer getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(Integer idGrupo) {
        this.idGrupo = idGrupo;
    }


}
