package com.prueba.fragments.RecyclerViews.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.prueba.fragments.MainActivity;
import com.prueba.fragments.R;
import com.prueba.fragments.RecyclerViews.Models.Publicacion;

import java.util.ArrayList;

public class PublicacionRvAdapter extends RecyclerView.Adapter<PublicacionRvAdapter.MyViewHolder> {
    Context context;
    ArrayList<Publicacion> publicacionModels;
    int posicionMarcada = 999999;

    public PublicacionRvAdapter(Context context, ArrayList<Publicacion> divisaModels) {
        this.context = context;
        this.publicacionModels = divisaModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cv_row_publicacion, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.Tema.setText(publicacionModels.get(position).getIdtema().toString());
        holder.Contenido.setText(publicacionModels.get(position).getContenido());
        holder.numLikes.setText(publicacionModels.get(position).getNumlikes().toString());
        holder.numComentarios.setText("query");
    }

    @Override
    public int getItemCount() {return publicacionModels.size();}

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Tema;
        TextView Contenido;
        TextView numLikes;
        TextView numComentarios;
        CardView cv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.cv = itemView.findViewById(R.id.cardPublicacion);
            this.Tema = itemView.findViewById(R.id.CardTema);
            this.Contenido = itemView.findViewById(R.id.contenidoPublicacion);
            this.numLikes = itemView.findViewById(R.id.numLikes);
            this.numComentarios = itemView.findViewById(R.id.numComentarios);
        }
    }
}
