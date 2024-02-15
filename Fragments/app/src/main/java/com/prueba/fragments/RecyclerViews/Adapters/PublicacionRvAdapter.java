package com.prueba.fragments.RecyclerViews.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
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

import com.prueba.fragments.ComentariosActivity;
import com.prueba.fragments.MainActivity;
import com.prueba.fragments.R;
import com.prueba.fragments.RetrofitConnection.Interfaces.PublicacionInterface;
import com.prueba.fragments.RetrofitConnection.Interfaces.TemaInterface;
import com.prueba.fragments.RetrofitConnection.Interfaces.UsuarioInterface;
import com.prueba.fragments.RetrofitConnection.Models.Publicacion;
import com.prueba.fragments.RetrofitConnection.Models.Tema;
import com.prueba.fragments.RetrofitConnection.Models.Usuario;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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
        holder.Tema.setText(MainActivity.listaTemas.get(publicacionModels.get(position).getIdtema()-1));
        holder.Contenido.setText(publicacionModels.get(position).getContenido());
        holder.numLikes.setText(publicacionModels.get(position).getNumlikes().toString());
        holder.numComentarios.setText("0");
        holder.idPublicacion = publicacionModels.get(position).getId();
        holder.Titulo.setText(publicacionModels.get(position).getTitulo());
        //holder.userName.setText(getUserName(publicacionModels.get(position).getIdusuario()).getName());
        getUserName(publicacionModels.get(position).getIdusuario(), new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                Usuario usuario = response.body();
                if (usuario != null) {
                    holder.userName.setText(usuario.getName());
                }
            }
            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                // Manejar el error
                Log.e("Error al cargar usuario", t.getMessage());
            }
        });

        holder.likeImg.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
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
        holder.comentarioImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toComentarios = new Intent(view.getContext(), ComentariosActivity.class);
                toComentarios.putExtra("id", holder.idPublicacion);
                view.getContext().startActivity(toComentarios);

            }
        });
    }


    @Override
    public int getItemCount() {
        return publicacionModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        Integer idPublicacion;
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

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.cv = itemView.findViewById(R.id.cardPublicacion);
            this.Tema = itemView.findViewById(R.id.CardTema);
            this.Contenido = itemView.findViewById(R.id.contenidoPublicacion);
            this.numLikes = itemView.findViewById(R.id.numLikes);
            this.numComentarios = itemView.findViewById(R.id.numComentarios);
            this.likeImg = itemView.findViewById(R.id.liekButton);
            this.comentarioImg = itemView.findViewById(R.id.ImgComentarios);
            this.idPublicacion = 0;
            this.Titulo = itemView.findViewById(R.id.titulo);
            liked = false;
            this.userName = itemView.findViewById(R.id.textViewUserName);

        }
    }
    /*public Usuario getUserName(Integer idUsuario){
        final Usuario[] newUser = new Usuario[1];
        UsuarioInterface UserInterface = MainActivity.retrofitUser.create(UsuarioInterface.class);
        Call<Usuario> call = UserInterface.getUserById(idUsuario);
        call.enqueue(new Callback<Usuario>() {

            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (!response.isSuccessful()) {
                    Log.e("Response err: ", response.message());
                    return;
                }
                newUser[0] = response.body();

            }


            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                return;
            }

        });
        return newUser[0];
    }*/
    public void getUserName(Integer idUsuario, final Callback<Usuario> callback) {
        UsuarioInterface UserInterface = MainActivity.retrofitUser.create(UsuarioInterface.class);
        Call<Usuario> call = UserInterface.getUserById(idUsuario);
        call.enqueue(new Callback<Usuario>() {

            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (!response.isSuccessful()) {
                    Log.e("Response err: ", response.message());
                    callback.onFailure(call, new Throwable("Error al cargar el usuario"));
                    return;
                }
                callback.onResponse(call, response);
            }


            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                callback.onFailure(call, t);
            }

        });
    }

}
