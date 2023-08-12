package com.example.popselllists.ui.chatList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.popselllists.R;
import com.example.popselllists.databinding.FragmentChatListBinding;
import com.example.popselllists.retrofit.Chatroom;

import java.util.ArrayList;

public class ChatListFragment extends Fragment {

    private FragmentChatListBinding binding;
    private TextView textView2;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ChatListViewModel chatListViewModel =
                new ViewModelProvider(this).get(ChatListViewModel.class);

        binding = FragmentChatListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        textView2 = root.findViewById(R.id.text_dashboard);
//        updateTextView(chatListViewModel.getChatrooms());

        return root;
    }

    private void updateTextView(LiveData<ArrayList<Chatroom>> liveData) {
        // Create the observer which updates the UI.
        final Observer<ArrayList<Chatroom>> nameObserver = chatrooms -> {
            // Update the UI
            if(chatrooms != null)
                textView2.append(makeText(chatrooms));
            else
                textView2.append("Have no chatrooms");
        };

        liveData.observe(this, nameObserver);
    }

    private String makeText(ArrayList<Chatroom> chatrooms){
        StringBuilder content = new StringBuilder();
        for (Chatroom postItem : chatrooms) {
            content.append("Status: ").append(postItem.getName()).append("\n");
        }

        return content.toString();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}