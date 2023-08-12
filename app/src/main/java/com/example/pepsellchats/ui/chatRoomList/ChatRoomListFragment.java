package com.example.pepsellchats.ui.chatRoomList;

import static android.Manifest.permission.CALL_PHONE;
import static androidx.core.content.PermissionChecker.checkSelfPermission;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pepsellchats.R;
import com.example.pepsellchats.databinding.FragmentChatRoomListBinding;
import com.example.pepsellchats.ui.chatList.ChatListActivity;

import java.util.ArrayList;

public class ChatRoomListFragment extends Fragment implements ChatRoomItemRecyclerViewInterface {
    private FragmentChatRoomListBinding binding;
    private ChatRoomListViewModel chatRoomListViewModel;
    private ChatRoomListItemAdapter chatRoomListAdapter;
    private boolean phonePermissionGranted;
    private LiveData<ArrayList<ChatRoomListItem>> liveData;

    public static ChatRoomListFragment newInstance() {
        return new ChatRoomListFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        chatRoomListViewModel = new ViewModelProvider(this).get(ChatRoomListViewModel.class);

        binding = FragmentChatRoomListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Set search view
        setSearch();

        // Set RecyclerView
        setRecyclerView();

        return root;
    }

    private void makePhoneCall(String phoneNumber) {
        checkPhonePermission();

        if(phonePermissionGranted){
            phoneNumber = phoneNumber.trim();

            // Getting instance of Intent with action as ACTION_CALL
            Intent phone_intent = new Intent(Intent.ACTION_CALL);

            // Set data of Intent through Uri by parsing phone number
            phone_intent.setData(Uri.parse("tel:" + phoneNumber));

            // start Intent
            startActivity(phone_intent);
        }
    }

    private void setSearch() {
        SearchView searchView = binding.getRoot().findViewById(R.id.chat_room_list_search_view);
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

    private void filterList(String text) {
        ArrayList<ChatRoomListItem> filteredList = new ArrayList<>();
        ArrayList<ChatRoomListItem> chatRoomListItem = chatRoomListViewModel.getItems().getValue();

        if (chatRoomListItem != null) {
            for (ChatRoomListItem chatItem : chatRoomListItem) {
                if (chatItem.getName().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(chatItem);
                }
            }
        }

        if (filteredList.isEmpty()) {
            Toast.makeText(getContext(), "No chats found", Toast.LENGTH_SHORT).show();
        } else {
            chatRoomListAdapter.setFilteredList(filteredList);
        }
    }

    private void setRecyclerView() {
        makeRecyclerView(chatRoomListViewModel.getItems());
        // Get livedata
        liveData = chatRoomListViewModel.getItems();
        // Observation
        final Observer<ArrayList<ChatRoomListItem>> dataObserver = items -> makeRecyclerView(liveData);
        liveData.observe(this, dataObserver);
    }

    private void makeRecyclerView(LiveData<ArrayList<ChatRoomListItem>> liveData) {
        RecyclerView recyclerView = binding.getRoot().findViewById(R.id.chat_room_list_recycler_view);
        // Create adapter
        chatRoomListAdapter = new ChatRoomListItemAdapter(getContext(), liveData.getValue(), this);
        // Set adapter for list
        recyclerView.setAdapter(chatRoomListAdapter);
    }

    private void checkPhonePermission() {
        if (checkSelfPermission(getActivity(), CALL_PHONE) != PermissionChecker.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(CALL_PHONE);
        } else {
            //Permission already granted
            phonePermissionGranted = true;
        }
    }

    private final ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            result -> {
                if (result) {
                    // permission granted
                    phonePermissionGranted = true;

                } else {
                    //permission denied
                    phonePermissionGranted = false;
                    Toast.makeText(getActivity(), "Permission denied.", Toast.LENGTH_SHORT).show();
                    if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), CALL_PHONE)) {
                        //show permission snackbar
                    } else {
                        //display error dialog
                        Toast.makeText(getActivity(), "Error.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemClick(String code, int position) {
        ChatRoomListItem marker = liveData.getValue().get(position);
        if(code.equals("phone")){
            Toast.makeText(getContext(), marker.getPhone(), Toast.LENGTH_SHORT).show();
            makePhoneCall(marker.getPhone());
        } else if (code.equals("chat")) {
            Intent intent = new Intent(getActivity(), ChatListActivity.class);
            intent.putExtra("ChatID", marker.getChatId());

            startActivity(intent);
        }
    }
}