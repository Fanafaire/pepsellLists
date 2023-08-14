package com.example.pepsellchats.ui.chat;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.pepsellchats.R;

public class ChatActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // hide action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, ChatFragment.newInstance())
                    .commitNow();
        }

        long chatRoomId = getIntent().getLongExtra("chatRoomId", 0);
        long chatId = getIntent().getLongExtra("chatId", 0);

        Log.d("ChatActivity: ", Long.toString(chatRoomId));

        ChatViewModel viewModel = new ViewModelProvider(this).get(ChatViewModel.class);
        viewModel.setChatIds(chatRoomId, chatId);
    }
}