package com.example.pepsellchats.ui.chat;

import android.os.Bundle;

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

        // ids for get/post body
        long chatRoomId = getIntent().getLongExtra("chatRoomId", 0);
        long chatId = getIntent().getLongExtra("chatId", 0);

        // Data for char card
        String card_userName = getIntent().getStringExtra("Card_userName");
        String card_message = getIntent().getStringExtra("Card_message");
        String card_time = getIntent().getStringExtra("Card_time");
        String card_logo = getIntent().getStringExtra("Card_logo");
        String card_media = getIntent().getStringExtra("Card_media");

        ChatViewModel viewModel = new ViewModelProvider(this).get(ChatViewModel.class);
        viewModel.setChatIds(chatRoomId, chatId);
        viewModel.setCardInfo(card_userName, card_message, card_time, card_logo, card_media);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, ChatFragment.newInstance())
                    .commitNow();
        }
    }
}
