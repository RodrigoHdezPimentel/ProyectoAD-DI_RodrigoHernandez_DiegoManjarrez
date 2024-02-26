package com.prueba.fragments.RecyclerViews.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.prueba.fragments.ChatActivity;
import com.prueba.fragments.R;
import com.prueba.fragments.RetrofitConnection.Models.Chat;
import com.prueba.fragments.RetrofitConnection.Models.Usuario;

import java.util.ArrayList;

public class ListaChatsRvAdapter extends RecyclerView.Adapter<ListaChatsRvAdapter.MyViewHolder> {
    Context context;
    ArrayList<Chat> chatModels;
    Intent toChat = null;

    public ListaChatsRvAdapter(Context context, ArrayList<Chat> chatModels) {
        this.context = context;
        this.chatModels = chatModels;
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
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        toChat = new Intent(context, ChatActivity.class);

        if(chatModels.get(position).getIdDestino() == Usuario.getInstance().getId()){
            holder.personaChat.setText(chatModels.get(position).getUsuarioOr().getName().toString());
            holder.fechaUltimoMensaje.setText(chatModels.get(position).getUsuarioOr().getId().toString());
            toChat.putExtra("idConv", chatModels.get(position).getUsuarioOr().getId());
        }else{
            holder.personaChat.setText(chatModels.get(position).getUsuarioDes().getName().toString());
            holder.fechaUltimoMensaje.setText(chatModels.get(position).getUsuarioDes().getId().toString());
            toChat.putExtra("idConv", chatModels.get(position).getUsuarioDes().getId());
        }
        //holder.fechaUltimoMensaje.setText(chatModels.get(position).getFecha());

        //No funciona el limite de caracteres
        if(holder.ultimoContenido.getText().length() > 20){
            holder.ultimoContenido.setText(chatModels.get(position).getContenido().substring(0, 17) + "...");
        }else{
            holder.ultimoContenido.setText(chatModels.get(position).getContenido());
        }
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chatModels.get(position).getIdDestino() == Usuario.getInstance().getId()){
                    toChat.putExtra("idConv", chatModels.get(position).getUsuarioOr().getId());
                }else{
                    toChat.putExtra("idConv", chatModels.get(position).getUsuarioDes().getId());
                }
                context.startActivity(toChat);
            }
        });

    }

    @Override
    public int getItemCount() {return chatModels.size();}

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView personaChat;
        TextView ultimoContenido;
        TextView fechaUltimoMensaje;
        CardView cv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.cv = itemView.findViewById(R.id.cardChat);
            this.personaChat = itemView.findViewById(R.id.personaChat);
            this.ultimoContenido = itemView.findViewById(R.id.contenidoMensaje);
            this.fechaUltimoMensaje = itemView.findViewById(R.id.CardChatFecha);

        }
    }
}
