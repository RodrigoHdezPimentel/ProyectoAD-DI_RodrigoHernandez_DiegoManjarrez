package com.prueba.fragments.RecyclerViews.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.prueba.fragments.ComentariosActivity;
import com.prueba.fragments.R;
import com.prueba.fragments.RetrofitConnection.Models.Like;
import com.prueba.fragments.RetrofitConnection.Models.Publicacion;
import com.prueba.fragments.RetrofitConnection.Models.Usuario;

import java.util.ArrayList;
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
        view = inflater.inflate(R.layout.cv_row_publicacion, parent, false);
        return new MyViewHolder(view);
    }

    View view;

    @SuppressLint({"SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.Tema.setText(publicacionModels.get(position).getTema().getTitulo());
        holder.Contenido.setText(publicacionModels.get(position).getContenido());
        holder.numLikes.setText(publicacionModels.get(position).getNumlikes().toString());
        holder.numComentarios.setText("0");
        holder.idPublicacion = publicacionModels.get(position).getId();
        holder.idPubliRef = publicacionModels.get(position).getIdpublirefer();
        holder.Titulo.setText(publicacionModels.get(position).getTitulo());
        holder.numComentarios.setText(publicacionModels.get(position).getComentarios().length + "");
        holder.userName.setText(publicacionModels.get(position).getUsuario().getName().toString());
        holder.likeImg.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                if (holder.liked) {
                    holder.liked = false;
                    holder.numLikes.setText((Integer.parseInt(holder.numLikes.getText().toString())) - 1 + "");
                    holder.likeImg.getDrawable().setColorFilter(ContextCompat.getColor(view.getContext(), R.color.black), PorterDuff.Mode.MULTIPLY);

                } else {
                    holder.liked = true;
                    holder.numLikes.setText((Integer.parseInt(holder.numLikes.getText().toString())) + 1 + "");
                    holder.likeImg.getDrawable().setColorFilter(ContextCompat.getColor(view.getContext(), R.color.seed), PorterDuff.Mode.MULTIPLY);

                }
            }
        });

        holder.comentarioImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toComentarios = new Intent(view.getContext(), ComentariosActivity.class);
                toComentarios.putExtra("id", holder.idPublicacion);
                view.getContext().startActivity(toComentarios);

            }
        });

        holder.listaLikes = publicacionModels.get(position).getLikes();
        for (Like l : holder.listaLikes) {

            if (l.getIdUsuario() == Usuario.getInstance().getId()) {
                holder.likeImg.getDrawable().setColorFilter(ContextCompat.getColor(view.getContext(), R.color.seed), PorterDuff.Mode.MULTIPLY);
                holder.liked = true;
            }
        }

    }

    @Override
    public int getItemCount() {
        return publicacionModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        Integer idPublicacion;
        Integer idPubliRef;
        boolean liked;
        TextView Tema;
        TextView Contenido;
        TextView numLikes;
        TextView numComentarios;
        ImageView likeImg;
        ImageView comentarioImg;
        TextView Titulo;
        CardView cv;
        TextView userName;
        ArrayList<Publicacion> comentarios;
        ArrayList<Like> listaLikes;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.cv = itemView.findViewById(R.id.cardPublicacion);
            this.Tema = itemView.findViewById(R.id.CardTema);
            this.Contenido = itemView.findViewById(R.id.contenidoPublicacion);
            this.numLikes = itemView.findViewById(R.id.numLikes);
            this.numComentarios = itemView.findViewById(R.id.numComentarios);
            this.likeImg = itemView.findViewById(R.id.liekButton);
            this.comentarioImg = itemView.findViewById(R.id.ImgComentarios);
            this.Titulo = itemView.findViewById(R.id.titulo);
            liked = false;
            this.comentarios = new ArrayList<>();
            this.userName = itemView.findViewById(R.id.textViewUserName);

        }
    }
}
