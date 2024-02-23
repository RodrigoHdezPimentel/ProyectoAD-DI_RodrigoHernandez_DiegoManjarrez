package com.prueba.fragments.Fragments.MainFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.prueba.fragments.Login_SignUP;
import com.prueba.fragments.R;
import com.prueba.fragments.RecyclerViews.Adapters.ListaChatsRvAdapter;
import com.prueba.fragments.RecyclerViews.Adapters.PublicacionRvAdapter;
import com.prueba.fragments.RetrofitConnection.Interfaces.ChatInterface;
import com.prueba.fragments.RetrofitConnection.Interfaces.PublicacionInterface;
import com.prueba.fragments.RetrofitConnection.Models.Chat;
import com.prueba.fragments.RetrofitConnection.Models.Publicacion;
import com.prueba.fragments.RetrofitConnection.Models.Usuario;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Chats extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Chats() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Chats newInstance(String param1, String param2) {
        Chats fragment = new Chats();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    View view;
    ArrayList<Chat> ListaChats = new ArrayList<>();
    ChatInterface chatInterface;
    ProgressBar progressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chats, container, false);
        progressBar = view.findViewById(R.id.progressBar);
        return  view;
    }

    public void cargarChats(){
        chatInterface = Login_SignUP.retrofitChat.create(ChatInterface.class);
        Call<List<Chat>> call = chatInterface.getUserChats(Usuario.getInstance().getId());
        call.enqueue(new Callback<List<Chat>>() {
            @Override
            public void onResponse(Call<List<Chat>> call, Response<List<Chat>> response) {
                if (!response.isSuccessful()) {
                    //Log.e("Response err: ", response.message());
                    return;
                }

                ListaChats = (ArrayList<Chat>) response.body();
                progressBar.setVisibility(View.GONE);

                // Inflate the layout for this fragment
                RecyclerView MyRecyclerView = view.findViewById(R.id.ChatsListRecyclerView);

                ListaChatsRvAdapter adapter = new ListaChatsRvAdapter(view.getContext(), ListaChats);
                MyRecyclerView.setAdapter(adapter);
                MyRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
            }
            @Override
            public void onFailure(Call<List<Chat>> call, Throwable t) {

            }
        });
    }
}