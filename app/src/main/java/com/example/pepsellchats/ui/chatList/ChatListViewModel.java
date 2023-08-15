package com.example.pepsellchats.ui.chatList;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.pepsellchats.retrofit.BodyCallTypes;
import com.example.pepsellchats.retrofit.BodyConstants;
import com.example.pepsellchats.retrofit.chatList.get.Chat;
import com.example.pepsellchats.retrofit.chatList.get.ChatListGeneral;
import com.example.pepsellchats.retrofit.chatList.get.ChatListGETBody;
import com.example.pepsellchats.retrofit.RetrofitApi;
import com.example.pepsellchats.ui.chatList.recyclerView.ChatListItem;

import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatListViewModel extends AndroidViewModel {
    private static long chatRoomId;
    // Result liveData
    private final MutableLiveData<ArrayList<ChatListItem>> chats;

    public ChatListViewModel(@NonNull Application application) {
        super(application);
        chats = new MutableLiveData<>();

        // Query started after setChatRoomId() from activity
    }

    /**
     * Query starts here!!!
     *
     * For pass chat room id from activity
     * @param chatRoomId Chat room id
     */
    void setChatRoomId(long chatRoomId){
        this.chatRoomId = chatRoomId;
        makeRetrofitQuery();
    }

    private void makeRetrofitQuery() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://wapp.pepsell.net/Pepsell2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);

        ChatListGETBody chatListGETBody = new ChatListGETBody(
                BodyCallTypes.CHAT_LIST.toString(),
                Long.toString(BodyConstants.USER_ID),
                BodyConstants.APP_ID,
                new Date().getTime(),
                chatRoomId,
                BodyConstants.START_PERIOD,
                BodyConstants.FINISH_PERIOD,
                BodyConstants.LIMIT_CHAT_LISTS);

        Call<ChatListGeneral> call = retrofitApi.getChatList(chatListGETBody);

        generateCall(call);
    }

    private void generateCall(Call<ChatListGeneral> call) {
        call.enqueue(new Callback<ChatListGeneral>() {
            @Override
            public void onResponse(@NonNull Call<ChatListGeneral> call, @NonNull Response<ChatListGeneral> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    return;
                }

                chats.setValue(setInitialData(response.body().getChatList()));
            }

            @Override
            public void onFailure(@NonNull Call<ChatListGeneral> call, @NonNull Throwable t) {
            }
        });
    }

    private ArrayList<ChatListItem> setInitialData(ArrayList<Chat> chatItem) {
        ArrayList<ChatListItem> chatListItem = new ArrayList<>();

        if (chatItem != null) {
            for (Chat item : chatItem) {
                chatListItem.add(new ChatListItem(
                        item.getChatroomID(),
                        item.getUser().getId(),
                        item.getID(),
                        item.getUser().getName(),
                        item.getUser().getLogo(),
                        item.getDESCRIPTION(),
                        item.getMEDIA_URI(),
                        item.getLAST_MESSAGE_DATE()));
            }
        }

        return chatListItem;
    }

    public LiveData<ArrayList<ChatListItem>> getChats() {
        return chats;
    }

    public long getChatRoomId() {
        return chatRoomId;
    }
}