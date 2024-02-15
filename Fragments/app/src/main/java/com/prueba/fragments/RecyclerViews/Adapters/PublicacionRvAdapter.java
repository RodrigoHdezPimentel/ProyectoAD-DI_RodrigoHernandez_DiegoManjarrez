package com.prueba.fragments.RecyclerViews.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.prueba.fragments.R;
import com.prueba.fragments.RetrofitConnection.Models.Publicacion;

import java.util.List;


public class PublicacionRvAdapter extends RecyclerView.Adapter<PublicacionRvAdapter.MyViewHolder> {
    Context context;
    List<Publicacion> publicacionModels;
    int posicionMarcada = -1;

    public PublicacionRvAdapter(Context context, List<Publicacion> publicacionModels) {
        this.context = context;
        this.publicacionModels = publicacionModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cv_row_publicacion, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint({"SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.Tema.setText(publicacionModels.get(position).getIdtema().toString());
        holder.Contenido.setText(publicacionModels.get(position).getContenido());
        holder.numLikes.setText(publicacionModels.get(position).getNumlikes().toString());
        holder.numComentarios.setText("0");

        holder.likeImg.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                Toast.makeText(context, (holder.liked)+ "", Toast.LENGTH_SHORT).show();

                if (holder.liked) {
                    holder.liked = false;
                    holder.numLikes.setText((Integer.parseInt(holder.numLikes.getText().toString()))-1 + "");
                    holder.likeImg.getDrawable().setColorFilter(ContextCompat.getColor(view.getContext(), R.color.black), PorterDuff.Mode.MULTIPLY);

                }else{
                    holder.liked = true;
                    holder.numLikes.setText((Integer.parseInt(holder.numLikes.getText().toString()))+1 + "");
                    holder.likeImg.getDrawable().setColorFilter(ContextCompat.getColor(view.getContext(), R.color.seed), PorterDuff.Mode.MULTIPLY);

                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return publicacionModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        Integer idTema;
        boolean liked;
        TextView Tema;
        TextView Contenido;
        TextView numLikes;
        TextView numComentarios;
        ImageView likeImg;
        CardView cv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.cv = itemView.findViewById(R.id.cardPublicacion);
            this.Tema = itemView.findViewById(R.id.CardTema);
            this.Contenido = itemView.findViewById(R.id.contenidoPublicacion);
            this.numLikes = itemView.findViewById(R.id.numLikes);
            this.numComentarios = itemView.findViewById(R.id.numComentarios);
            this.likeImg = itemView.findViewById(R.id.liekButton);
            liked = false;
        }
    }
}
