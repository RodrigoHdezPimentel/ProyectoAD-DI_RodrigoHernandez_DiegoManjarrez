package com.prueba.fragments.RecyclerViews.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Debug;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.prueba.fragments.Login_SignUP;
import com.prueba.fragments.R;
import com.prueba.fragments.RetrofitConnection.Interfaces.GrupoUsuarioInterface;
import com.prueba.fragments.RetrofitConnection.Models.Conversacion;
import com.prueba.fragments.RetrofitConnection.Models.GrupoUsuario;
import com.prueba.fragments.RetrofitConnection.Models.Usuario;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatRvAdapter extends RecyclerView.Adapter<ChatRvAdapter.MyViewHolder> {
    Context context;
    ArrayList<Conversacion> conversacionModels;

    public ChatRvAdapter(Context context, ArrayList<Conversacion> conversacionModels) {
        this.context = context;
        this.conversacionModels = conversacionModels;
    }
    @Override
    public int getItemViewType(int position) {
        return position;
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
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final GrupoUsuario[] grupoUsuario = {null};
        GrupoUsuarioInterface grupoUsuarioInterface = Login_SignUP.retrofitGrupoUsuario.create(GrupoUsuarioInterface.class);
        Call<GrupoUsuario> call = grupoUsuarioInterface.getById(conversacionModels.get(position).getIdGrupoUsuario());
        call.enqueue(new Callback<GrupoUsuario>() {
            @Override
            public void onResponse(@NonNull Call<GrupoUsuario> call, @NonNull Response<GrupoUsuario> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                grupoUsuario[0] = response.body();
                //holder.usuarioMensaje = grupoUsuario[0].getGrupoUsuarioFK().getUsuario();
                holder.nombre.setText(holder.usuarioMensaje.getName().toString());

                //Orientar el mensaje dependiendo de su procedencia
                if(holder.usuarioMensaje.getId() == Usuario.getInstance().getId()){
                    Log.d("Mi app", holder.usuarioMensaje.getId().toString()+" "+holder.usuarioMensaje.getName().toString());

                    holder.cv.setCardBackgroundColor(ContextCompat.getColor(context, R.color.seed)); // Establecer el color de fondo
                    ConstraintSet constraintSet = new ConstraintSet();
                    constraintSet.clone(holder.constraintLayout);
                    constraintSet.connect(holder.cv.getId(), ConstraintSet.RIGHT, holder.constraintLayout.getId(), ConstraintSet.RIGHT, 16);
                    constraintSet.applyTo(holder.constraintLayout);
                }
                   }
            @Override
            public void onFailure(Call<GrupoUsuario> call, Throwable t) {
            }
        });
        holder.cv.setElevation(10f);
        holder.cv.setCardBackgroundColor(ContextCompat.getColor(context, R.color.md_theme_light_tertiaryContainer)); // Establecer el color de fondo
        holder.fecha.setText(conversacionModels.get(position).getFecha().toString());
        holder.Contenido.setText(conversacionModels.get(position).getContenido());


    }

    @Override
    public int getItemCount() {return conversacionModels.size();}

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView fecha;
        TextView nombre;
        TextView Contenido;
        Usuario usuarioMensaje;
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
