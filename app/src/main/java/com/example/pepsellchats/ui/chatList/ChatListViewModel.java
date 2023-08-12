package com.example.pepsellchats.ui.chatList;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pepsellchats.retrofit.chatList.Chat;
import com.example.pepsellchats.retrofit.chatList.ChatListGeneral;
import com.example.pepsellchats.retrofit.chatList.ChatListPostBody;
import com.example.pepsellchats.retrofit.RetrofitApi;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatListViewModel extends ViewModel {
    // Body for retrofit
    private final static String TYPE = "CHAT_LIST";
    private final static String USER_ID = "380990143524";
    private final static String APP_ID = "1";
    private final static long DATE_TIME = 1690819233997L;
    private final static long START_PERIOD = 1690819233997L;
    private final static long FINISH_PERIOD = 1690819233997L;
    private final static int LIMIT = 10;
    private int chatRoomId;
    // Result liveData
    private final MutableLiveData<ArrayList<ChatListItem>> chats;

    public ChatListViewModel() {
        chats = new MutableLiveData<>();
        makeRetrofitQuery();
    }

    private void makeRetrofitQuery() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://wapp.pepsell.net/Pepsell2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);

        ChatListPostBody chatListPostBody = new ChatListPostBody(TYPE, USER_ID, APP_ID, DATE_TIME,
                chatRoomId, START_PERIOD, FINISH_PERIOD, LIMIT);

        Call<ChatListGeneral> call = retrofitApi.getChatList(chatListPostBody);

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
                chatListItem.add(new ChatListItem(item.getUser().getId(), item.getID(),
                        item.getUser().getName(), item.getUser().getLogo(), item.getDESCRIPTION(),
                        item.getMEDIA_URI(), item.getLAST_MESSAGE_DATE()));
            }
        }

        return chatListItem;
    }

    public LiveData<ArrayList<ChatListItem>> getChats() {
        return chats;
    }

    /**
     * For pass chat room id from activity
     * @param chatRoomId Chat room id
     */
    void setChatId(int chatRoomId){
        this.chatRoomId = chatRoomId;
        Log.d("setChatId: ", Integer.toString(chatRoomId));
    }
}