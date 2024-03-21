package com.prueba.fragments.RecyclerViews.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.prueba.fragments.ChatActivity;
import com.prueba.fragments.Class.ChatLastMessage;
import com.prueba.fragments.Login_SignUP;
import com.prueba.fragments.MainActivity;
import com.prueba.fragments.R;
import com.prueba.fragments.RetrofitConnection.Interfaces.ConversacionInterface;
import com.prueba.fragments.RetrofitConnection.Interfaces.GrupoUsuarioInterface;
import com.prueba.fragments.RetrofitConnection.Models.Conversacion;
import com.prueba.fragments.RetrofitConnection.Models.GrupoUsuario;
import com.prueba.fragments.RetrofitConnection.Models.Usuario;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaChatsRvAdapter extends RecyclerView.Adapter<ListaChatsRvAdapter.MyViewHolder> {
    Context context;
    ArrayList<ChatLastMessage> groupModels;
    Intent toChat = null;

    public ListaChatsRvAdapter(Context context, ArrayList<ChatLastMessage> chatModels) {
        this.context = context;
        this.groupModels = chatModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cv_row_chatlist, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint({"SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        toChat = new Intent(context, ChatActivity.class);
    try {
        holder.personaChat.setText(groupModels.get(position).getChat().getNombre());

        iconAdd(groupModels.get(position).getChat().getGrupoUsuarioFK().getUsuario().getGenero(), holder);
        //Para Controlar los Objetos Conversacion vacios que no tengan mensajes
        if (groupModels.get(position).getMensaje() != null) {
            holder.fechaUltimoMensaje.setText(groupModels.get(position).getMensaje().getFecha().toString());
            holder.ultimoContenido.setText(groupModels.get(position).getMensaje().getContenido());
        } else {
            holder.fechaUltimoMensaje.setText("");
            holder.ultimoContenido.setText("");
        }
        ArrayList<Integer> idLeido = new ArrayList<>();
        for (String id : groupModels.get(position).getMensaje().getIdleido()) {
            idLeido.add(Integer.parseInt(id));
        }
        if (idLeido.contains(Usuario.getInstance().getId())) {
            holder.newMessage.setVisibility(View.INVISIBLE);
        } else {
            holder.newMessage.setVisibility(View.VISIBLE);
        }

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toChat.putExtra("gender", groupModels.get(position).getChat().getGrupoUsuarioFK().getUsuario().getGenero());
                toChat.putExtra("idGrupo", groupModels.get(position).getChat().getGrupoUsuarioFK().getGrupo().getIdGrupo());
                toChat.putExtra("idGrupoUsuario", groupModels.get(position).getChat().getIdGrupoUsuario());


                context.startActivity(toChat);
            }
        });
    }catch(Exception e){

    }
    }

    @Override
    public int getItemCount() {return groupModels.size();}

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView personaChat;
        TextView newMessage;
        TextView ultimoContenido;
        TextView fechaUltimoMensaje;
        CardView cv;
       ImageView iconUser;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.newMessage = itemView.findViewById(R.id.newMessage);
            this.iconUser = itemView.findViewById(R.id.iconListChat);
            this.cv = itemView.findViewById(R.id.cardChat);
            this.personaChat = itemView.findViewById(R.id.personaChat);
            this.ultimoContenido = itemView.findViewById(R.id.contenidoMensaje);
            this.fechaUltimoMensaje = itemView.findViewById(R.id.CardChatFecha);

        }

    }
    public void iconAdd(Boolean gender, MyViewHolder holder) {

        if(gender == null){
            holder.iconUser.setImageResource(R.drawable.ic_app);
        } else {
            if (!gender) {
                holder.iconUser.setImageResource(R.drawable.ic_mujer);
            } else {
                holder.iconUser.setImageResource(R.drawable.ic_hombre);
            }
        }
    }

}
