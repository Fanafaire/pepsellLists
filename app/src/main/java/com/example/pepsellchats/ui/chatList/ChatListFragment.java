package com.example.pepsellchats.ui.chatList;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static androidx.core.content.PermissionChecker.checkSelfPermission;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pepsellchats.R;
import com.example.pepsellchats.databinding.FragmentChatListBinding;
import com.example.pepsellchats.retrofit.BodyCallTypes;
import com.example.pepsellchats.retrofit.chat.post.ChatQueryExecutor;
import com.example.pepsellchats.ui.chat.ChatActivity;
import com.example.pepsellchats.ui.chatList.recyclerView.ChatItemRecyclerViewInterface;
import com.example.pepsellchats.ui.chatList.recyclerView.ChatListItem;
import com.example.pepsellchats.ui.chatList.recyclerView.ChatListItemAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class ChatListFragment extends Fragment implements ChatItemRecyclerViewInterface {

    private FragmentChatListBinding binding;
    private ChatListViewModel chatListViewModel;
    private LiveData<ArrayList<ChatListItem>> liveData;
    private View viewPopup;
    private boolean storagePermissionGranted;

    public static ChatListFragment newInstance() {
        return new ChatListFragment();
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        chatListViewModel = new ViewModelProvider(requireActivity()).get(ChatListViewModel.class);

        binding = FragmentChatListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Set RecyclerView
        setRecyclerView();

        // Create chat block
        createTextChat();

        return root;
    }

    private void createTextChat() {
        ImageView createChat = binding.getRoot().findViewById(R.id.chat_list_create_chat);
        createChat.setOnClickListener(view -> openSendPopup());
    }

    private void openSendPopup() {
        // Get layouts
        ConstraintLayout parent = binding.getRoot().findViewWithTag("chatListFragmentLayout");
        viewPopup = View.inflate(getContext(), R.layout.send_chat_popup, null);

        // Call popup
        PopupWindow popupWindow = new PopupWindow(
                viewPopup,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                true
        );
        popupWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);

        // Set listener to load image
        ImageView imagePopupLoad = viewPopup.findViewById(R.id.send_chat_get_image);
        imagePopupLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkStoragePermission();
                if (storagePermissionGranted)
                    mGetContent.launch("image/*");
            }
        });

        // Set listener to popup send image (button)
        ImageView imagePopupSend = viewPopup.findViewById(R.id.send_chat_send);
        imagePopupSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editTextData = viewPopup.findViewById(R.id.send_chat_text);
                String text = editTextData.getText().toString().trim();

                if(!text.isEmpty()){
                    long messageTime = new Date().getTime();
                    sendImageMessage(null, messageTime, text);

                    popupWindow.dismiss();
                } else {
                    // todo
                }
            }
        });
    }

    private void sendImageMessage(Uri uri, long messageTime, String text) {

    }

    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            uri -> {
                // Set image
                ImageView imagePopup = viewPopup.findViewById(R.id.send_chat_image);
                Picasso.get().load(uri).into(imagePopup);
            });

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
        Log.d("ChatListFragment makeRecyclerView: ", "test");
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

            Log.d("ChatListFragment setChatRoomId: ", Long.toString(marker.getChatRoomId()) +
                    " * " + Long.toString(marker.getChatId()));

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

    private void checkStoragePermission() {
        if (checkSelfPermission(getActivity(), READ_EXTERNAL_STORAGE) != PermissionChecker.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(READ_EXTERNAL_STORAGE);
        } else {
            // Permission already granted
            storagePermissionGranted = true;
        }
    }

    private final ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            result -> {
                if (result) {
                    // Permission granted
                    storagePermissionGranted = true;
                    mGetContent.launch("image/*");

                } else {
                    // Permission denied
                    storagePermissionGranted = false;
                    Toast.makeText(getActivity(), "Permission denied.", Toast.LENGTH_SHORT).show();
                    if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), READ_EXTERNAL_STORAGE)) {
                        // Show permission snackbar
                    } else {
                        // Display error dialog
                        Toast.makeText(getActivity(), "Error.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
}