package com.prueba.fragments.RecyclerViews.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prueba.fragments.R;
import com.prueba.fragments.RetrofitConnection.Models.Usuario;

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

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //Nombre y Foto perfil
        if(usuariosModels.get(position).getName().equals(Usuario.getInstance().getName())){
            holder.nombre.setText("(" + usuariosModels.get(position).getName().toString() + ") Tu");
        }else {
            holder.nombre.setText(usuariosModels.get(position).getName().toString());
        }
        if(usuariosModels.get(position).getGenero() == null){
            holder.foto.setImageResource(R.drawable.ic_app);
        }else{
            if (!usuariosModels.get(position).getGenero()) {
                holder.foto.setImageResource(R.drawable.ic_mujer);
            } else {
                holder.foto.setImageResource(R.drawable.ic_hombre);
            }
        }
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
