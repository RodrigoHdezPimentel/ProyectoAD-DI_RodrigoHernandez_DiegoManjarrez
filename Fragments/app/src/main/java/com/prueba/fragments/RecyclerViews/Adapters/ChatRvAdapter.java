package com.prueba.fragments.RecyclerViews.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.prueba.fragments.Login_SignUP;
import com.prueba.fragments.MainActivity;
import com.prueba.fragments.R;
import com.prueba.fragments.RecyclerViews.Models.Chat;

import java.util.ArrayList;

public class ChatRvAdapter extends RecyclerView.Adapter<ChatRvAdapter.MyViewHolder> {
    Context context;
    ArrayList<Chat> chatModels;
    int posicionMarcada = 999999;

    public ChatRvAdapter(Context context, ArrayList<Chat> chatModels) {
        this.context = context;
        this.chatModels = chatModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cv_row_chat, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint({"SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.fecha.setText(chatModels.get(position).getFecha().toString());
        holder.Contenido.setText(chatModels.get(position).getContenido());
        holder.idDestino = chatModels.get(position).getIdDestino();
        holder.idOrigen = chatModels.get(position).getIdOrigen();

        if(holder.idOrigen != MainActivity.idRegistrado){
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(holder.constraintLayout);
            constraintSet.connect(holder.cv.getId(), ConstraintSet.RIGHT, holder.constraintLayout.getId(), ConstraintSet.RIGHT, 16);
            constraintSet.applyTo(holder.constraintLayout);
        }
    }

    @Override
    public int getItemCount() {return chatModels.size();}

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView fecha;
        TextView Contenido;
        Integer idDestino;
        Integer idOrigen;
        CardView cv;
        ConstraintLayout constraintLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.cv = itemView.findViewById(R.id.cardMessage);
            this.fecha = itemView.findViewById(R.id.CardFecha);
            this.Contenido = itemView.findViewById(R.id.contenidoPublicacion);
            this.constraintLayout = itemView.findViewById(R.id.ConstraitLayoutChatRow);
        }
    }
}
