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
import com.prueba.fragments.Login_SignUP;
import com.prueba.fragments.MainActivity;
import com.prueba.fragments.R;
import com.prueba.fragments.RetrofitConnection.Models.Chat;

import java.util.ArrayList;

public class ListaChatsRvAdapter extends RecyclerView.Adapter<ListaChatsRvAdapter.MyViewHolder> {
    Context context;
    ArrayList<Chat> chatModels;
    int posicionMarcada = -1;

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
        if(chatModels.get(position).getIdDestino() == Login_SignUP.idRegistrado){
            holder.personaChat.setText(chatModels.get(position).getIdOrigen().toString());
        }else{
            holder.personaChat.setText(chatModels.get(position).getIdDestino().toString());

        }
        holder.fechaUltimoMensaje.setText(chatModels.get(position).getFecha());

        //No funciona el limite de caracteres
        if(holder.ultimoContenido.getText().length() > 20){
            holder.ultimoContenido.setText(chatModels.get(position).getContenido().substring(0, 17) + "...");
        }else{
            holder.ultimoContenido.setText(chatModels.get(position).getContenido());
        }
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toChat = new Intent(context, ChatActivity.class);
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
