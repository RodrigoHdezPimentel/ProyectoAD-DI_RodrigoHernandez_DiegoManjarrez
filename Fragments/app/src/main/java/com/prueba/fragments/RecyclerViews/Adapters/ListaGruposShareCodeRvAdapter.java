package com.prueba.fragments.RecyclerViews.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prueba.fragments.ChatActivity;
import com.prueba.fragments.Class.ChatLastMessage;
import com.prueba.fragments.R;

import java.util.ArrayList;

public class ListaGruposShareCodeRvAdapter extends RecyclerView.Adapter<ListaGruposShareCodeRvAdapter.MyViewHolder> {
    Context context;
    ArrayList<ChatLastMessage> listaGrupos;

    public ListaGruposShareCodeRvAdapter(Context context, ArrayList<ChatLastMessage> listaGrupos) {
        this.context = context;
        this.listaGrupos = listaGrupos;
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cv_row_group_list_send_group_code, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //Nombre y Foto perfil
        holder.nombre.setText(listaGrupos.get(position).getChat().getNombre().toString());
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //Llamar a una funcion del chatActivity para compartir codigo
                if (!holder.checkBox.isChecked()) {
                    Toast.makeText(context, "añadido", Toast.LENGTH_SHORT).show();
                    ChatActivity.idsGrupoUsuarioShareedCodeGroups.remove(
                            listaGrupos.get(position).getChat().getIdGrupoUsuario());
                }else {
                    Toast.makeText(context, listaGrupos.get(position).getChat().getGrupoUsuarioFK().getIdgrupo().toString(), Toast.LENGTH_SHORT).show();
                    ChatActivity.idsGrupoUsuarioShareedCodeGroups.add(
                            listaGrupos.get(position).getChat().getIdGrupoUsuario());
                }
            }
        });
    }

    @Override
    public int getItemCount() {return listaGrupos.size();}

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nombre;
        CheckBox checkBox;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.nombre = itemView.findViewById(R.id.personaChat);
            this.checkBox = itemView.findViewById(R.id.chatCheckBox);
        }
    }
}
