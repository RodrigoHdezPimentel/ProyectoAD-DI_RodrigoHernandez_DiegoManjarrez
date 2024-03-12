package com.prueba.fragments.RecyclerViews.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.prueba.fragments.ChatActivity;
import com.prueba.fragments.Login_SignUP;
import com.prueba.fragments.R;
import com.prueba.fragments.RetrofitConnection.Interfaces.ConversacionInterface;
import com.prueba.fragments.RetrofitConnection.Models.Conversacion;
import com.prueba.fragments.RetrofitConnection.Models.Grupo;
import com.prueba.fragments.RetrofitConnection.Models.GrupoUsuario;
import com.prueba.fragments.RetrofitConnection.Models.Usuario;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaChatsRvAdapter extends RecyclerView.Adapter<ListaChatsRvAdapter.MyViewHolder> {
    Context context;
    ArrayList<GrupoUsuario> groupModels;
    Intent toChat = null;

    public ListaChatsRvAdapter(Context context, ArrayList<GrupoUsuario> chatModels) {
        this.context = context;
        this.groupModels = chatModels;
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
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        toChat = new Intent(context, ChatActivity.class);

        holder.personaChat.setText(groupModels.get(position).getGrupo().getNombre().toString());
        iconAdd(groupModels.get(position).getUsuario().getGenero(), holder);

        //Ultimo mensaje
        ConversacionInterface conversacionInterface = Login_SignUP.retrofitConversacion.create(ConversacionInterface.class);
        Call <Conversacion> call = conversacionInterface.getLastMessage(groupModels.get(position).getGrupo().getIdGrupo());
        call.enqueue(new Callback<Conversacion>() {
            @Override
            public void onResponse(Call<Conversacion> call, Response<Conversacion> response) {
                if(!response.isSuccessful()){
                    return;
                }
                holder.fechaUltimoMensaje.setText(response.body().getFecha().toString());
                holder.ultimoContenido.setText(response.body().getContenido().toString());
            }

            @Override
            public void onFailure(Call<Conversacion> call, Throwable t) {

            }
        });
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    toChat.putExtra("gender",groupModels.get(position).getUsuario().getGenero());
                    toChat.putExtra("idGrupo", groupModels.get(position).getGrupo().getIdGrupo());
                    toChat.putExtra("idGrupoUsuario", groupModels.get(position).getIdgrupousuario());


                context.startActivity(toChat);
            }
        });

    }

    @Override
    public int getItemCount() {return groupModels.size();}

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView personaChat;
        TextView ultimoContenido;
        TextView fechaUltimoMensaje;
        CardView cv;
       ImageView iconUser;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iconUser = itemView.findViewById(R.id.iconListChat);
            this.cv = itemView.findViewById(R.id.cardChat);
            this.personaChat = itemView.findViewById(R.id.personaChat);
            this.ultimoContenido = itemView.findViewById(R.id.contenidoMensaje);
            this.fechaUltimoMensaje = itemView.findViewById(R.id.CardChatFecha);

        }

    }
    public void iconAdd(Boolean gender, MyViewHolder holder) {


        if (!gender) {
            holder.iconUser.setImageResource(R.drawable.ic_mujer);
//            ViewGroup.LayoutParams layoutParams = holder.iconUser.getLayoutParams();
//            layoutParams.height = 200; // Altura en píxeles
//            layoutParams.width = 200; // Anchura en píxeles
//            holder.iconUser.setLayoutParams(layoutParams);
        } else if (gender) {
            holder.iconUser.setImageResource(R.drawable.ic_hombre);
        } else {
            holder.iconUser.setImageResource(R.drawable.ic_app);
        }
    }


}
