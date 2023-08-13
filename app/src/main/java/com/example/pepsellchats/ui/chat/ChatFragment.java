package com.example.pepsellchats.ui.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pepsellchats.R;
import com.example.pepsellchats.databinding.FragmentChatBinding;
import com.example.pepsellchats.ui.chatList.ChatListItem;

import java.util.ArrayList;

public class ChatFragment extends Fragment implements MessageRecyclerViewInterface {

    private FragmentChatBinding binding;
    private ChatViewModel chatViewModel;
    private LiveData<ArrayList<ChatItem>> liveData;
    // post
    private EditText messageTextView;
    private ImageView sendTextIm;
    private ChatListItem cardInfo;

    public static ChatFragment newInstance() {
        return new ChatFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        chatViewModel =
                new ViewModelProvider(this).get(ChatViewModel.class);

        binding = FragmentChatBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        messageTextView = root.findViewById(R.id.chat_amc_write);
        sendTextIm = root.findViewById(R.id.chat_amc_send);
        cardInfo = chatViewModel.getCardInfo();

        sendTextIm.setOnClickListener((v-> sendMessage()));

        // Set RecyclerView
        setRecyclerView();

        return root;
    }

    private void sendMessage() {
        String message = messageTextView.getText().toString().trim();
        if(message.isEmpty())
            return;


    }

    private void setRecyclerView() {
        // Fixed alert on console: skipped layout, works correct without this line
        makeRecyclerView(chatViewModel.getMessages());
        // Get livedata
        liveData = chatViewModel.getMessages();
        // Observation
        final Observer<ArrayList<ChatItem>> dataObserver = items -> makeRecyclerView(liveData);
        liveData.observe(this, dataObserver);
    }

    private void makeRecyclerView(LiveData<ArrayList<ChatItem>> liveData) {
        RecyclerView recyclerView = binding.getRoot().findViewById(R.id.chat_recycler_view);
        // Create adapter
        MessageAdapter messageAdapter = new MessageAdapter(getContext(), liveData.getValue(), this);
        // Set adapter for list
        recyclerView.setAdapter(messageAdapter);
    }

    @Override
    public void onScroll(int size, int position) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}