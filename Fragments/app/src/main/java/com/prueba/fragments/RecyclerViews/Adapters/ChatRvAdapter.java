package com.prueba.fragments.RecyclerViews.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.prueba.fragments.R;
import com.prueba.fragments.RetrofitConnection.Models.Conversacion;
import com.prueba.fragments.RetrofitConnection.Models.Usuario;

import java.util.ArrayList;

public class ChatRvAdapter extends RecyclerView.Adapter<ChatRvAdapter.MyViewHolder> {
    Context context;
    ArrayList<Conversacion> conversacionModels;

    public ChatRvAdapter(Context context, ArrayList<Conversacion> conversacionModels) {
        this.context = context;
        this.conversacionModels = conversacionModels;
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
        holder.cv.setElevation(10f);
        holder.cv.setCardBackgroundColor(ContextCompat.getColor(context, R.color.md_theme_light_tertiaryContainer)); // Establecer el color de fondo
        holder.nombre.setText(
                conversacionModels.get(position).getGrupoUsuario().getGrupoUsuarioFK().
                        getUsuario().getName().toString());
        holder.fecha.setText(conversacionModels.get(position).getFecha().toString());
        holder.Contenido.setText(conversacionModels.get(position).getContenido());

        //Orientar el mensaje dependiendo de su procedencia
        if(conversacionModels.get(position).getGrupoUsuario().getGrupoUsuarioFK().getUsuario().getId() != Usuario.getInstance().getId()){
            holder.cv.setCardBackgroundColor(ContextCompat.getColor(context, R.color.seed)); // Establecer el color de fondo
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(holder.constraintLayout);
            constraintSet.connect(holder.cv.getId(), ConstraintSet.RIGHT, holder.constraintLayout.getId(), ConstraintSet.RIGHT, 16);
            constraintSet.applyTo(holder.constraintLayout);
        }
    }

    @Override
    public int getItemCount() {return conversacionModels.size();}

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView fecha;
        TextView nombre;
        TextView Contenido;
        CardView cv;
        ConstraintLayout constraintLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.cv = itemView.findViewById(R.id.cardMessage);
            this.fecha = itemView.findViewById(R.id.CardFecha);
            this.Contenido = itemView.findViewById(R.id.contenidoPublicacion);
            this.constraintLayout = itemView.findViewById(R.id.ConstraitLayoutChatRow);
            this.nombre = itemView.findViewById(R.id.nombreConversacion);
        }
    }
}
