package com.prueba.fragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.prueba.fragments.Fragments.MainFragment.Chats;
import com.prueba.fragments.RecyclerViews.Adapters.ChatRvAdapter;
import com.prueba.fragments.RecyclerViews.Adapters.ListaChatsRvAdapter;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        RecyclerView MyRecyclerView = findViewById(R.id.ConversationListRecyclerView);

        ChatRvAdapter adapter = new ChatRvAdapter(this, MainActivity.chatConversation);
        MyRecyclerView.setAdapter(adapter);
        MyRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ImageView arrow = findViewById(R.id.arrow);
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent listChat = new Intent(ChatActivity.this, MainActivity.class);
                startActivity(listChat);
            }
        });
    }
}