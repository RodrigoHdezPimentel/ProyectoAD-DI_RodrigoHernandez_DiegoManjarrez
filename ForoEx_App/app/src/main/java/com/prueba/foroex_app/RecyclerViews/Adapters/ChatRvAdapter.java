package com.prueba.foroex_app.RecyclerViews.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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

import com.prueba.foroex_app.ChatActivity;
import com.prueba.foroex_app.Class.Message;
import com.prueba.foroex_app.MainActivity;
import com.prueba.foroex_app.R;
import com.prueba.foroex_app.RetrofitConnection.Models.Grupo;
import com.prueba.foroex_app.RetrofitConnection.Models.GrupoUsuario;
import com.prueba.foroex_app.RetrofitConnection.Models.GrupoUsuarioFK;
import com.prueba.foroex_app.RetrofitConnection.Models.Usuario;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatRvAdapter extends RecyclerView.Adapter<ChatRvAdapter.MyViewHolder> {
    Context context;
    static ArrayList<Message> conversacionModels;
    RecyclerView recyclerView;

    public ChatRvAdapter(Context context, ArrayList<Message> conversacionModels, RecyclerView recyclerView) {
        this.context = context;
        ChatRvAdapter.conversacionModels = conversacionModels;
        this.recyclerView = recyclerView;
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

        holder.nombre.setText(conversacionModels.get(position).getNombreUsuario().toString());
        //Orientar el mensaje dependiendo de su procedencia
        if(conversacionModels.get(position).getIdUsuario() == Usuario.getInstance().getId()){
           // Log.d("Mi app", holder.usuarioMensaje.getId().toString()+" "+holder.usuarioMensaje.getName().toString());

            holder.cv.setCardBackgroundColor(ContextCompat.getColor(context, R.color.seed)); // Establecer el color de fondo
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(holder.constraintLayout);
            constraintSet.connect(holder.cv.getId(), ConstraintSet.RIGHT, holder.constraintLayout.getId(), ConstraintSet.RIGHT, 16);
            constraintSet.applyTo(holder.constraintLayout);

            //Hacer esto dentro de un alert dialogo para que se vea más bonito pero quitar el modo modificar el mensaje
            //solamente dejar el delete mensaje.
            holder.cv.setOnLongClickListener(new View.OnLongClickListener() {
                public boolean onLongClick(View v) {
                    ChatActivity.editConversacion( conversacionModels.get(position).getConversacion(), holder.Contenido, position);
                    return false;
                }
            });

        }else {
            holder.cv.setCardBackgroundColor(ContextCompat.getColor(context, R.color.md_theme_light_tertiaryContainer)); // Establecer el color de fondo
        }

        holder.cv.setElevation(10f);
        holder.fecha.setText(conversacionModels.get(position).getConversacion().getFecha().toString());

        holder.Contenido.setText(conversacionModels.get(position).getConversacion().getContenido());
        linkJoinChat(holder, position);
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
    //Este método se encarga de recibir el mensaje desde el hilo y cargalo en el adapter
    public void mensajeNuevo(Message newMensaje) {

        conversacionModels.add(newMensaje);
        notifyItemInserted(conversacionModels.size() - 1);
        recyclerView.smoothScrollToPosition(conversacionModels.size() - 1);

    }
    public void linkJoinChat(MyViewHolder holder, int position){
        if(holder.Contenido.getText().toString().contains("http//:localhost:8086/chat/join/") &&
                !Objects.equals(conversacionModels.get(position).getIdUsuario(), Usuario.getInstance().getId())){
         holder.cv.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 comprobarLink(holder,position);
             }
         });
        }
    }

        public void asignarChat(Integer idGrupo, Grupo grupo){
            //Primero nos asignamos al grupo
            Call<GrupoUsuario> call = MainActivity.grupoUsuarioInterface.create(new GrupoUsuario(null, "Nombre Grupo", null,
                    new GrupoUsuarioFK(Usuario.getInstance().getId(), idGrupo, Usuario.getInstance(), grupo)));
            call.enqueue(new Callback<GrupoUsuario>() {
                @Override
                public void onResponse(Call<GrupoUsuario> call, Response<GrupoUsuario> response1) {
                    if(!response1.isSuccessful()){
                        return;
                    }
                            //enviamos los datos al ChatActivity
                            Intent toChat = new Intent(context,ChatActivity.class);
                            toChat.putExtra("foto",grupo.getFoto());
                            toChat.putExtra("idGrupo", idGrupo);
                            toChat.putExtra("idGrupoUsuario", response1.body().getIdGrupoUsuario());
                            context.startActivity(toChat);

                }
                @Override
                public void onFailure(Call<GrupoUsuario> call, Throwable t) {

                }
            });
        }

public  void comprobarLink(MyViewHolder holder, int position){
    int longitud = holder.Contenido.getText().toString().length();
    String obtenerCodigo = holder.Contenido.getText().toString().substring(longitud - 10,longitud);

    Call<Boolean> call = MainActivity.grupoInterface.findUserInGroup(obtenerCodigo, Usuario.getInstance().getId());
       call.enqueue(new Callback<Boolean>() {
           @Override
           public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()){
                    if (Boolean.TRUE.equals(response.body())){
                        Toast.makeText(context, "Ya estás en el grupo", Toast.LENGTH_SHORT).show();

                    }else {
                        findGroupUser(obtenerCodigo);
                    }
                }
           }

           @Override
           public void onFailure(Call<Boolean> call, Throwable t) {

           }
       });
}
public void findGroupUser(String codigo){
    Call<Grupo> call = MainActivity.grupoInterface.findGroup(codigo);
    call.enqueue(new Callback<Grupo>() {
        @Override
        public void onResponse(Call<Grupo> call, Response<Grupo> response) {
            if (response.isSuccessful()){
               // Toast.makeText(context, "grupo encontrado", Toast.LENGTH_SHORT).show();
                asignarChat(response.body().getIdGrupo(), response.body());
                Toast.makeText(context, "¡Bienvenido al chat!", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(context, "Error en el link", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onFailure(Call<Grupo> call, Throwable t) {

        }
    });
}
}
