package com.example.popselllists.ui.chatList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popselllists.R;
import com.example.popselllists.databinding.FragmentChatListBinding;

import java.util.ArrayList;

public class ChatListFragment extends Fragment {

    private FragmentChatListBinding binding;
    private View root;
    private ChatListViewModel chatListViewModel;
    private ChatListItemAdapter chatListAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        chatListViewModel =
                new ViewModelProvider(this).get(ChatListViewModel.class);

        binding = FragmentChatListBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        // Set search view
        setSearch();

        // Set RecyclerView
        setRecyclerView();

        return root;
    }

    private void setSearch() {
        SearchView searchView = root.findViewById(R.id.chat_list_search_view);
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
        // For not skipping layout
        makeRecyclerView(chatListViewModel.getItems());
        // Start list initiating
        LiveData<ArrayList<ChatListItem>> liveData = chatListViewModel.getItems();

        final Observer<ArrayList<ChatListItem>> dataObserver = items -> makeRecyclerView(liveData);

        chatListViewModel.getItems().observe(this, dataObserver);
    }

    private void makeRecyclerView(LiveData<ArrayList<ChatListItem>> liveData) {
        RecyclerView recyclerView = root.findViewById(R.id.chat_list_recycler_view);
        // Create adapter
        chatListAdapter = new ChatListItemAdapter(getContext(), liveData.getValue());
        // Set adapter for list
        recyclerView.setAdapter(chatListAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}