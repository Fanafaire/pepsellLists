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



//        switch (item.getItemId()) {
//            case R.id.cli_card_share:
//
//                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
//
//                // type of the content to be shared
//                sharingIntent.setType("text/plain");
//
//                // Body of the content
//                String shareBody = "Your Body Here";
//
//                // subject of the content. you can share anything
//                String shareSubject = "Your Subject Here";
//
//                // passing body of the content
//                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
//
//                // passing subject of the content
//                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject);
//                startActivity(Intent.createChooser(sharingIntent, "Share using"));
//                break;
//        }

}