package com.example.pepsellchats.ui.chatList;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.pepsellchats.R;

public class ChatListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // hide action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int chatId = getIntent().getIntExtra("ChatID", 0);
        ChatListViewModel viewModel = new ViewModelProvider(this).get(ChatListViewModel.class);
        viewModel.setChatId(chatId);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, ChatListFragment.newInstance())
                    .commitNow();
        }
    }
}