package com.example.pepsellchats.ui.chat;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pepsellchats.R;
import com.example.pepsellchats.databinding.FragmentChatBinding;

import java.util.ArrayList;

public class ChatFragment extends Fragment{
    private FragmentChatBinding binding;
    private ChatViewModel chatViewModel;
    private LiveData<ArrayList<ChatItem>> liveData;

    public static ChatFragment newInstance() {
        return new ChatFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        chatViewModel = new ViewModelProvider(this).get(ChatViewModel.class);

        binding = FragmentChatBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Set RecyclerView
        setRecyclerView();

        long text = chatViewModel.getChatRoomId();
        long text2 = chatViewModel.getChatId();
        Log.d("YYYYYYYYYYYYYYYYYYYYYY: ", Long.toString(text) + " ^ " + Long.toString(text2));

        TextView mText = root.findViewById(R.id.chat_amc_write);
        mText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), mText.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        return root;
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
        Log.d("ChatFragment makeRecyclerView: ", "test");
        RecyclerView recyclerView = binding.getRoot().findViewById(R.id.chat_recycler_view);
        // Create adapter
        MessageAdapter messageAdapter = new MessageAdapter(getContext(), liveData.getValue());
        // Set adapter for list
        recyclerView.setAdapter(messageAdapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}