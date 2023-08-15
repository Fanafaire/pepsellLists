package com.example.pepsellchats.ui.chatList;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static androidx.core.content.PermissionChecker.checkSelfPermission;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pepsellchats.R;
import com.example.pepsellchats.databinding.FragmentChatListBinding;
import com.example.pepsellchats.retrofit.BodyCallTypes;
import com.example.pepsellchats.retrofit.BodyConstants;
import com.example.pepsellchats.retrofit.chatList.post.ChatListPOSTReturn;
import com.example.pepsellchats.retrofit.chatList.post.ChatListQueryExecutor;
import com.example.pepsellchats.ui.chat.ChatActivity;
import com.example.pepsellchats.ui.chatList.recyclerView.ChatItemRecyclerViewInterface;
import com.example.pepsellchats.ui.chatList.recyclerView.ChatListItem;
import com.example.pepsellchats.ui.chatList.recyclerView.ChatListItemAdapter;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ChatListFragment extends Fragment implements ChatItemRecyclerViewInterface {
    private FragmentChatListBinding binding;
    private ChatListViewModel chatListViewModel;
    private LiveData<ArrayList<ChatListItem>> liveData;
    private View viewPopup;
    private boolean storagePermissionGranted;
    private Uri fileUri;

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
        createChat();

        return root;
    }

    private void createChat() {
        ImageView createChat = binding.getRoot().findViewById(R.id.chat_list_create_chat);
        createChat.setOnClickListener(view -> openSendPopup());
    }

    private void openSendPopup() {
        // Get layouts
        ConstraintLayout parent = binding.getRoot().findViewWithTag("chatListFragmentLayout");
        viewPopup = View.inflate(getContext(), R.layout.image_preview, null);

        // Hide close image button and image name set select
        viewPopup.findViewById(R.id.image_popup_image).setVisibility(View.GONE);
        viewPopup.findViewById(R.id.image_popup_close).setVisibility(View.GONE);
        TextView imageMes = viewPopup.findViewById(R.id.image_popup_image_name);
        imageMes.setText(R.string.no_image);

        // Call popup
        PopupWindow popupWindow = new PopupWindow(
                viewPopup,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                true
        );
        popupWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);

        // Set listener to sendImage button
        ImageView sendImage = viewPopup.findViewById(R.id.chat_bot_nav_image);
        sendImage.setOnClickListener(view1 -> {
//            checkStoragePermission();

            if (android.os.Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
                // only for gingerbread and newer versionsdd
                checkStoragePermission();
            }

            if (storagePermissionGranted && android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                mGetContent.launch("image/*");
            else if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
                mGetContent.launch("image/*");
            }
        });

        // Set listener to popup send image (button)
        ImageView sendChatImage = viewPopup.findViewById(R.id.chat_amc_send);

        sendChatImage.setOnClickListener(view -> {
            EditText editTextData = viewPopup.findViewById(R.id.chat_amc_write);
            String text = editTextData.getText().toString().trim();

            ImageView imagePopup = viewPopup.findViewById(R.id.image_popup_image);
            Uri sendUri = (imagePopup.getVisibility() == View.GONE) ? null : fileUri;

            if(!text.equals("") || sendUri != null){
                long messageTime = new Date().getTime();
                sendNewChat(sendUri, messageTime, text);

                popupWindow.dismiss();
            }
        });
    }

    private void sendNewChat(Uri uri, long messageTime, String text) {
        // Post image message to server
        ChatListQueryExecutor executor = new ChatListQueryExecutor();

        LiveData<ChatListPOSTReturn> response;
        if(uri == null){
            response = executor.getPOSRRespond(
                    BodyCallTypes.CREATE_TEXT_CHAT.toString(),
                    chatListViewModel.getChatRoomId(),
                    0,
                    text,
                    messageTime
            );
        } else {
            executor.setFile(uri);
            response = executor.getPOSRRespond(
                    BodyCallTypes.CREATE_MEDIA_CHAT.toString(),
                    chatListViewModel.getChatRoomId(),
                    0,
                    text,
                    messageTime
            );
        }

        // live data observer
        String finalUriString = (uri != null) ? uri.toString() : null;
        final Observer<ChatListPOSTReturn> dataObserver = items -> responseObserverBody(
                text,
                messageTime,
                finalUriString,
                response.getValue().getCHAT_ID());
        response.observe(getViewLifecycleOwner(), dataObserver);
    }

    private void responseObserverBody(String text, long messageTime, String uriString, long chatId) {
        // Set time
        Timestamp ts = new Timestamp(messageTime);
        Date date = new Date(ts.getTime());
        String strDate = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(date);



        ArrayList<ChatListItem> chatList = liveData.getValue();
        ChatListItem chatListItem = new ChatListItem(
                chatListViewModel.getChatRoomId(),
                BodyConstants.USER_ID,
                chatId,
                "Микола",
                "https://www.shutterstock.com/image-vector/portrait-beautiful-woman-halfturn-avatar-600w-1828327529.jpg",
                text,
                uriString,
                strDate
        );

        if (chatList != null) {
            chatList.size();
            chatList.add(0, chatListItem);

//            chatList.add(chatListItem);

            MutableLiveData<ArrayList<ChatListItem>> newMutableData = new MutableLiveData<>();
            newMutableData.setValue(chatList);

            liveData = newMutableData;
            setRecyclerView();
        }
    }

    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            uri -> {
                if(uri != null){
                    fileUri = uri;
                    // Set image
                    ImageView imagePopup = viewPopup.findViewById(R.id.image_popup_image);
                    imagePopup.setVisibility(View.VISIBLE);

                    // Set image name
                    TextView imageText = viewPopup.findViewById(R.id.image_popup_image_name);
                    imageText.setText(new File(uri.getPath()).getName());

                    Picasso.get().load(uri).into(imagePopup);

                    // Show closeImage button
                    ImageView close = viewPopup.findViewById(R.id.image_popup_close);
                    close.setVisibility(View.VISIBLE);

                    // Listener for close image
                    close.setOnClickListener(view1 -> {
                        TextView imageMes = viewPopup.findViewById(R.id.image_popup_image_name);
                        imageMes.setText(R.string.no_image);
                        viewPopup.findViewById(R.id.image_popup_image).setVisibility(View.GONE);
                        viewPopup.findViewById(R.id.image_popup_close).setVisibility(View.GONE);
                    });
                }
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

        // subject of the content. you can share anything
        String shareSubject = "Your Subject Here";

        // passing body of the content (additionalText is body of the content)
        sharingIntent.putExtra(Intent.EXTRA_TEXT, additionalText);

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