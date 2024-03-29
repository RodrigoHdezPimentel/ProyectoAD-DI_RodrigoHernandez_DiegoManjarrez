package com.prueba.fragments.RecyclerViews.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.prueba.fragments.Class.LoadConversation;
import com.prueba.fragments.ComentariosActivity;
import com.prueba.fragments.Login_SignUP;
import com.prueba.fragments.MainActivity;
import com.prueba.fragments.R;
import com.prueba.fragments.RetrofitConnection.Interfaces.ConversacionInterface;
import com.prueba.fragments.RetrofitConnection.Interfaces.GrupoUsuarioInterface;
import com.prueba.fragments.RetrofitConnection.Interfaces.PublicacionInterface;
import com.prueba.fragments.RetrofitConnection.Models.Conversacion;
import com.prueba.fragments.RetrofitConnection.Models.GrupoUsuario;
import com.prueba.fragments.RetrofitConnection.Models.Publicacion;
import com.prueba.fragments.RetrofitConnection.Models.Usuario;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatRvAdapter extends RecyclerView.Adapter<ChatRvAdapter.MyViewHolder> {
    Context context;
    static ArrayList<LoadConversation> conversacionModels;
    RecyclerView recyclerView;

    public ChatRvAdapter(Context context, ArrayList<LoadConversation> conversacionModels,RecyclerView recyclerView) {
        this.context = context;
        this.conversacionModels = conversacionModels;
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
        }else {
            holder.cv.setCardBackgroundColor(ContextCompat.getColor(context, R.color.md_theme_light_tertiaryContainer)); // Establecer el color de fondo
        }

        holder.cv.setElevation(10f);
        holder.fecha.setText(conversacionModels.get(position).getConversacion().getFecha().toString());
        holder.Contenido.setText(conversacionModels.get(position).getConversacion().getContenido());

        //Marcar mensajes como leidos
        ArrayList<Integer> idLeido = new ArrayList<>();
        for(String id : conversacionModels.get(position).getConversacion().getIdleido()){
            idLeido.add(Integer.parseInt(id));
        }
        if(!idLeido.contains(Usuario.getInstance().getId())){
            Call<Void> callUpdate = MainActivity.conversacionInterface.readMessage(Usuario.getInstance().getId(), conversacionModels.get(position).getConversacion().getIdConversacion());
            callUpdate.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (!response.isSuccessful()) {
                        Log.e("Response err: ", response.message());
                        return;
                    }
                }
                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                }
            });
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
    //Este método se encarga de recibir el mensaje desde el hilo y cargalo en el adapter
    public void mensajeNuevo(LoadConversation newMensaje) {

        conversacionModels.add(newMensaje);
        notifyItemInserted(conversacionModels.size() - 1);
        recyclerView.smoothScrollToPosition(conversacionModels.size() - 1);

    }
}
