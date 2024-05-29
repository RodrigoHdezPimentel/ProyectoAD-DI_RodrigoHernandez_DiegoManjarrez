package com.prueba.foroex_app.RecyclerViews.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prueba.foroex_app.MainActivity;
import com.prueba.foroex_app.R;
import com.prueba.foroex_app.RetrofitConnection.Models.Usuario;

import java.util.ArrayList;

public class ChatUsersRvAdapter extends RecyclerView.Adapter<ChatUsersRvAdapter.MyViewHolder> {
    Context context;
    ArrayList<Usuario> usuariosModels;

    public ChatUsersRvAdapter(Context context, ArrayList<Usuario> usuariosModels) {
        this.context = context;
        this.usuariosModels = usuariosModels;
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cv_row_usuario, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //Nombre y Foto perfil
        if(usuariosModels.get(position).getName().equals(Usuario.getInstance().getName())){
            holder.nombre.setText("(" + usuariosModels.get(position).getName() + ") Tu");
        }else {
            holder.nombre.setText(usuariosModels.get(position).getName());
        }
        MainActivity.addPicture(holder.foto, context, usuariosModels.get(position).getFoto());
    }

    @Override
    public int getItemCount() {return usuariosModels.size();}

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nombre;
        ImageView foto;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.foto = itemView.findViewById(R.id.fotoUsuario);
            this.nombre = itemView.findViewById(R.id.nombreUsuario);
        }
    }
}
