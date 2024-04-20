package com.prueba.fragments.RecyclerViews.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.prueba.fragments.ChatActivity;
import com.prueba.fragments.Class.ChatListUser;
import com.prueba.fragments.MainActivity;
import com.prueba.fragments.R;

import java.util.ArrayList;

public class ListaChatsRvAdapter extends RecyclerView.Adapter<ListaChatsRvAdapter.MyViewHolder> {
    Context context;
    ArrayList<ChatListUser> groupModels;
    Intent toChat = null;

    public ListaChatsRvAdapter(Context context, ArrayList<ChatListUser> chatModels) {
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
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @SuppressLint({"SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        toChat = new Intent(context, ChatActivity.class);
    try {
        holder.personaChat.setText(groupModels.get(position).getChat().getNombre());

        MainActivity.addPicture(holder.iconChat,context,groupModels.get(position).getChat().getGrupoUsuarioFK().getGrupo().getFoto());
        //Para Controlar los Objetos Conversacion vacios que no tengan mensajes
        if (groupModels.get(position).getMensaje() != null) {
            holder.fechaUltimoMensaje.setText(groupModels.get(position).getMensaje().getFecha().toString());
            holder.ultimoContenido.setText(groupModels.get(position).getMensaje().getContenido());
        } else {
            holder.fechaUltimoMensaje.setText("");
            holder.ultimoContenido.setText("");
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
       ImageView iconChat;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.newMessage = itemView.findViewById(R.id.newMessage);
            this.iconChat = itemView.findViewById(R.id.iconListChat);
            this.cv = itemView.findViewById(R.id.cardChat);
            this.personaChat = itemView.findViewById(R.id.personaChat);
            this.ultimoContenido = itemView.findViewById(R.id.contenidoMensaje);
            this.fechaUltimoMensaje = itemView.findViewById(R.id.CardChatFecha);

        }

    }

}
