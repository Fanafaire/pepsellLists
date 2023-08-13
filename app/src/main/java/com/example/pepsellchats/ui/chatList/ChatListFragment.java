package com.example.pepsellchats.ui.chatList;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pepsellchats.R;
import com.example.pepsellchats.databinding.FragmentChatListBinding;
import com.example.pepsellchats.ui.chat.ChatActivity;

import java.util.ArrayList;

public class ChatListFragment extends Fragment implements ChatItemRecyclerViewInterface {

    private FragmentChatListBinding binding;
    private ChatListViewModel chatListViewModel;
    private LiveData<ArrayList<ChatListItem>> liveData;

    public static ChatListFragment newInstance() {
        return new ChatListFragment();
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        chatListViewModel =
                new ViewModelProvider(this).get(ChatListViewModel.class);

        binding = FragmentChatListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Set RecyclerView
        setRecyclerView();

        return root;
    }

    private void setRecyclerView() {
        // Fixed alert on console: skipped layout, works correct without this line
        makeRecyclerView(chatListViewModel.getChats());
        // Get livedata
        liveData = chatListViewModel.getChats();
        // Observation
        final Observer<ArrayList<ChatListItem>> dataObserver = items -> makeRecyclerView(liveData);
        liveData.observe(this, dataObserver);
    }

    private void makeRecyclerView(LiveData<ArrayList<ChatListItem>> liveData) {
        RecyclerView recyclerView = binding.getRoot().findViewById(R.id.chat_list_recycler_view);
        // Create adapter
        ChatListItemAdapter chatListAdapter = new ChatListItemAdapter(getContext(), liveData.getValue(), this);
        // Set adapter for list
        recyclerView.setAdapter(chatListAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemClick(String code, int position, String additionalText) {
        ChatListItem marker = liveData.getValue().get(position);
        if (code.equals("chat")) {
            Intent intent = new Intent(getActivity(), ChatActivity.class);
            // Top card
            intent.putExtra("Card_userName", marker.getUserName());
            intent.putExtra("Card_message", marker.getChatDescription());
            intent.putExtra("Card_time", marker.getChatLastMesDate());
            intent.putExtra("Card_logo", marker.getUserLogoURI());
            intent.putExtra("Card_media", marker.getChatMediaURI());

            // For GET
            intent.putExtra("chatRoomId", marker.getChatRoomId());
            intent.putExtra("chatId", marker.getChatId());

            startActivity(intent);
        } else if(code.equals("share")) {
            makeShare(additionalText);
        }
    }

    private void makeShare(String additionalText) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);

        // type of the content to be shared
        sharingIntent.setType("text/plain");

        // Body of the content
        String shareBody = additionalText;

        // subject of the content. you can share anything
        String shareSubject = "Your Subject Here";

        // passing body of the content
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);

        // passing subject of the content
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject);
        startActivity(Intent.createChooser(sharingIntent, "Share using"));
    }
}