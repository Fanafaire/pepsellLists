package com.example.pepsellchats.ui.chat;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static androidx.core.content.PermissionChecker.checkSelfPermission;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pepsellchats.R;
import com.example.pepsellchats.databinding.FragmentChatBinding;
import com.example.pepsellchats.retrofit.BodyCallTypes;
import com.example.pepsellchats.retrofit.BodyConstants;
import com.example.pepsellchats.retrofit.chat.post.ChatPOSTReturn;
import com.example.pepsellchats.retrofit.chat.post.ChatQueryExecutor;
import com.example.pepsellchats.ui.chat.recyclerView.ChatItem;
import com.example.pepsellchats.ui.chat.recyclerView.MessageAdapter;
import com.example.pepsellchats.ui.chatList.recyclerView.ChatListItem;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class ChatFragment extends Fragment {
    private FragmentChatBinding binding;
    private ChatViewModel chatViewModel;
    private LiveData<ArrayList<ChatItem>> liveData;
    private boolean storagePermissionGranted;
    private static ChatListItem cardInfo;

    public static ChatFragment newInstance() {
        return new ChatFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        chatViewModel = new ViewModelProvider(this).get(ChatViewModel.class);

        binding = FragmentChatBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Set RecyclerView
        setRecyclerView();

        // Send message block
        setTextSend();
        setImageSend();

        // Set header card
        setHeaderCard();

        return root;
    }

    private void setHeaderCard() {
        cardInfo = chatViewModel.getCardInfo();

        TextView chatName = binding.getRoot().findViewById(R.id.chat_cli_card_chat_name);
        chatName.setText(cardInfo.getChatDescription());

        TextView time = binding.getRoot().findViewById(R.id.chat_cli_card_time);
        time.setText(cardInfo.getChatLastMesDate());

        TextView userName = binding.getRoot().findViewById(R.id.chat_cli_name);
        userName.setText(cardInfo.getUserName());

        ImageView userLogo = binding.getRoot().findViewById(R.id.chat_cli_logo);
        Picasso.get().load(cardInfo.getUserLogoURI()).into(userLogo);

        Log.d("setHeaderCard: ", "" + cardInfo.getChatDescription());

//        TextView image = binding.getRoot().findViewById(R.id.chat_cli_card_image_card);
//        Picasso.get().load(cardInfo.getChatMediaURI()).into(image);

    }

    private void setImageSend() {
        ImageView sendImage = binding.getRoot().findViewById(R.id.chat_bot_nav_image);
        sendImage.setOnClickListener(view -> {
            checkStoragePermission();

            if (storagePermissionGranted)
                mGetContent.launch("image/*");
        });
    }

    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            uri -> {
                // Get layouts
                ConstraintLayout parent = binding.getRoot().findViewWithTag("chatFragmentLayout");
                View view = View.inflate(getContext(), R.layout.image_preview, null);

                // Set text
                TextView mText = binding.getRoot().findViewById(R.id.chat_amc_write);
                TextView popupText = view.findViewById(R.id.chat_amc_write);
                popupText.setText(mText.getText().toString());

                // Call popup
                PopupWindow popupWindow = new PopupWindow(
                        view,
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        true
                );
                popupWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);

                // Set image
                ImageView imagePopup = view.findViewById(R.id.image_popup_image);
                Picasso.get().load(uri).resize(parent.getWidth(), 0).into(imagePopup);

                // Set image name (current is a path)
                TextView imageText = view.findViewById(R.id.image_popup_image_name);
                imageText.setText(new File(uri.getPath()).getName());

                // Set listener to popup send image (button)
                ImageView imagePopupSend = view.findViewById(R.id.chat_amc_send);
                imagePopupSend.setOnClickListener(view12 -> {
                    long messageTime = new Date().getTime();
                    String popupTextLoad = popupText.getText().toString().trim();
                    sendImageMessage(uri, messageTime, popupTextLoad);
                    popupText.setText("");

                    popupWindow.dismiss();
                });

                // Set listener to close button
                ImageView closeIm = view.findViewById(R.id.image_popup_close);
                closeIm.setOnClickListener(view1 -> {
                    mText.setText(popupText.getText().toString());
                    popupWindow.dismiss();
                });
            });

    private void sendImageMessage(Uri uri, long messageTime, String loadText) {
        // Post image message to server
        ChatQueryExecutor executor = new ChatQueryExecutor();
        executor.setFile(uri);
        LiveData<ChatPOSTReturn> response = executor.getPOSRRespond(
                BodyCallTypes.SEND_CHAT_MEDIA.toString(),
                chatViewModel.getChatRoomId(),
                chatViewModel.getChatId(),
                loadText,
                messageTime
        );

        // live data observer
        final Observer<ChatPOSTReturn> dataObserver = items -> responseObserverBody(loadText, messageTime, uri.toString());
        response.observe(getViewLifecycleOwner(), dataObserver);
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

    private void setTextSend() {
        ImageView mSend = binding.getRoot().findViewById(R.id.chat_amc_send);
        mSend.setOnClickListener(view -> sendMessage());
    }

    private void sendMessage() {
        // Get text from edited text and make edited text empty after it
        TextView mText = binding.getRoot().findViewById(R.id.chat_amc_write);
        String text = mText.getText().toString().trim();
        mText.setText("");

        // Post text message to server
        ChatQueryExecutor executor = new ChatQueryExecutor();
        long messageTime = new Date().getTime();
        LiveData<ChatPOSTReturn> response = executor.getPOSRRespond(
                BodyCallTypes.SEND_CHAT_TEXT.toString(),
                chatViewModel.getChatRoomId(),
                chatViewModel.getChatId(),
                text,
                messageTime
        );

        // liva data observer
        final Observer<ChatPOSTReturn> dataObserver = items -> responseObserverBody(text, messageTime, null);
        response.observe(getViewLifecycleOwner(), dataObserver);
    }

    private void responseObserverBody(String text, long messageTime, String uriString) {
        ArrayList<ChatItem> messagesList = liveData.getValue();
        ChatItem chatItem = new ChatItem(
                BodyConstants.USER_ID,
                BodyConstants.USER_ID,
                null,
                null,
                uriString,
                text,
                messageTime);

        if (messagesList != null) {
            messagesList.add(chatItem);

            MutableLiveData<ArrayList<ChatItem>> newMutableData = new MutableLiveData<>();
            newMutableData.setValue(messagesList);

            liveData = newMutableData;
            setRecyclerView();
        }
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
        MessageAdapter messageAdapter = new MessageAdapter(getContext(), reverseArray(liveData.getValue()));
        // Set adapter for list
        recyclerView.setAdapter(messageAdapter);
    }

    private ArrayList<ChatItem> reverseArray(ArrayList<ChatItem> value) {
        ArrayList<ChatItem> result = new ArrayList<>();
        if (value != null) {
            for (int i = value.size() - 1; i >= 0; i--) {
                result.add(value.get(i));
            }
        }
        return result;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}