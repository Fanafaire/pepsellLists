package com.example.popselllists.ui.chatList;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CALL_PHONE;
import static androidx.core.content.PermissionChecker.checkSelfPermission;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popselllists.MainActivity;
import com.example.popselllists.R;
import com.example.popselllists.databinding.FragmentChatListBinding;
import com.example.popselllists.ui.dashboard.DashboardFragment;

import java.util.ArrayList;

public class ChatListFragment extends Fragment implements ChatItemRecyclerViewInterface {

    private static final int REQUEST_CALL = 1;
    private FragmentChatListBinding binding;
    private View root;
    private ChatListViewModel chatListViewModel;
    private ChatListItemAdapter chatListAdapter;
    private boolean phonePermissionGranted;
    private LiveData<ArrayList<ChatListItem>> liveData;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        chatListViewModel = new ViewModelProvider(this).get(ChatListViewModel.class);

        binding = FragmentChatListBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        // Set search view
        setSearch();

        // Set RecyclerView
        setRecyclerView();

        return root;
    }

    private void makePhoneCall(String phoneNumber) {
        checkPhonePermission();

        phoneNumber = phoneNumber.trim();

        // Getting instance of Intent with action as ACTION_CALL
        Intent phone_intent = new Intent(Intent.ACTION_CALL);

        // Set data of Intent through Uri by parsing phone number
        phone_intent.setData(Uri.parse("tel:" + phoneNumber));

        // start Intent
        startActivity(phone_intent);
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

    private void filterList(String text) {
        ArrayList<ChatListItem> filteredList = new ArrayList<>();
        ArrayList<ChatListItem> chatListItem = chatListViewModel.getItems().getValue();

        if (chatListItem != null) {
            for (ChatListItem chatItem : chatListItem) {
                if (chatItem.getName().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(chatItem);
                }
            }
        }

        if (filteredList.isEmpty()) {
            Toast.makeText(getContext(), "No chats found", Toast.LENGTH_SHORT).show();
        } else {
            chatListAdapter.setFilteredList(filteredList);
        }
    }

    private void setRecyclerView() {
        makeRecyclerView(chatListViewModel.getItems());
        // Get livedata
        liveData = chatListViewModel.getItems();
        // Observation
        final Observer<ArrayList<ChatListItem>> dataObserver = items -> makeRecyclerView(liveData);
        liveData.observe(this, dataObserver);
    }

    private void makeRecyclerView(LiveData<ArrayList<ChatListItem>> liveData) {
        RecyclerView recyclerView = root.findViewById(R.id.chat_list_recycler_view);
        // Create adapter
        chatListAdapter = new ChatListItemAdapter(getContext(), liveData.getValue(), this);
        // Set adapter for list
        recyclerView.setAdapter(chatListAdapter);
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
        ChatListItem marker = liveData.getValue().get(position);;
        if(code.equals("phone")){
            Toast.makeText(getContext(), marker.getPhone(), Toast.LENGTH_SHORT).show();
            makePhoneCall(marker.getPhone());
        } else if (code.equals("chat")) {
            Toast.makeText(getContext(), Integer.toString(marker.getChatId()), Toast.LENGTH_SHORT).show();

//            Intent intent = new Intent(getActivity(), DashboardFragment.class);
//            intent.putExtra("ChatID", marker.getChatId());
        }
    }
}