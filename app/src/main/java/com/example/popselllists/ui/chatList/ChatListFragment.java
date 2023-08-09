package com.example.popselllists.ui.chatList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popselllists.R;
import com.example.popselllists.databinding.FragmentChatListBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ChatListFragment extends Fragment {

    private FragmentChatListBinding binding;
    private SearchView searchView;
    private View root;
    private ChatListViewModel chatListViewModel;
    private ChatListItemAdapter chatListAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        chatListViewModel =
                new ViewModelProvider(this).get(ChatListViewModel.class);

        binding = FragmentChatListBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        // Set RecyclerView
        setRecyclerView();

        // Set search view
        setSearch();

        return root;
    }

    private void setSearch() {
        searchView = root.findViewById(R.id.chat_list_search_view);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                filterList(s);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filterList(s);
                return true;
            }
        });
    }

    private void filterList(String text){
        ArrayList<ChatListItem> filteredList = new ArrayList<>();
        ArrayList<ChatListItem> chatListItem = chatListViewModel.getItems().getValue();

        if(chatListItem != null) {
            for (ChatListItem chatItem : chatListItem) {
                if (chatItem.getName().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(chatItem);
                }
            }
        }

        if(filteredList.isEmpty()){
            Toast.makeText(getContext(), "No chats found", Toast.LENGTH_SHORT).show();
        } else {
            chatListAdapter.setFilteredList(filteredList);
        }
    }

    private void setRecyclerView() {
        // Start list initiating
        ArrayList<ChatListItem> chatListItem = chatListViewModel.getItems().getValue();
        RecyclerView recyclerView = root.findViewById(R.id.chat_list_recycler_view);
        // Create adapter
        chatListAdapter = new ChatListItemAdapter(getContext(), chatListItem);
        // Set adapter for list
        recyclerView.setAdapter(chatListAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}