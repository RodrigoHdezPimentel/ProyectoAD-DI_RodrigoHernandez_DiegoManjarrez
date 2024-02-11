package com.prueba.fragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.prueba.fragments.RecyclerViews.Adapters.ChatRvAdapter;
import com.prueba.fragments.RecyclerViews.Adapters.ListaChatsRvAdapter;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        RecyclerView MyRecyclerView = findViewById(R.id.ConversationListRecyclerView);

        ChatRvAdapter adapter = new ChatRvAdapter(this, Login_SignUP.chatConversation);
        MyRecyclerView.setAdapter(adapter);
        MyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}