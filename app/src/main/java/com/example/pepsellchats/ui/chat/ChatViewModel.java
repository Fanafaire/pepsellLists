package com.example.pepsellchats.ui.chat;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.pepsellchats.retrofit.BodyCallTypes;
import com.example.pepsellchats.retrofit.BodyConstants;
import com.example.pepsellchats.retrofit.RetrofitApi;
import com.example.pepsellchats.retrofit.chat.get.ChatGETBody;
import com.example.pepsellchats.retrofit.chat.get.ChatGeneral;
import com.example.pepsellchats.retrofit.chat.get.Message;
import com.example.pepsellchats.ui.chat.recyclerView.ChatItem;
import com.example.pepsellchats.ui.chatList.recyclerView.ChatListItem;

import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatViewModel extends AndroidViewModel {
    // Ids for get body
    private static long chatRoomId, chatId;
    private static ChatListItem cardInfo;
    // Result liveData
    private final MutableLiveData<ArrayList<ChatItem>> messages;

    public ChatViewModel(@NonNull Application application) {
        super(application);
        messages = new MutableLiveData<>();
        makeRetrofitQuery();
    }

    private void makeRetrofitQuery() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BodyConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);

        ChatGETBody chatGETBody = new ChatGETBody(
                BodyCallTypes.CHAT_HISTORY.toString(),
                Long.toString(BodyConstants.USER_ID),
                BodyConstants.APP_ID,
                new Date().getTime(),
                chatRoomId,
                chatId,
                BodyConstants.START_PERIOD,
                BodyConstants.FINISH_PERIOD,
                BodyConstants.LIMIT_MESSAGES);

        Call<ChatGeneral> call = retrofitApi.getChatMessages(chatGETBody);

        generateCall(call);
    }
    private void generateCall(Call<ChatGeneral> call) {
        call.enqueue(new Callback<ChatGeneral>() {
            @Override
            public void onResponse(@NonNull Call<ChatGeneral> call, @NonNull Response<ChatGeneral> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    return;
                }

                messages.setValue(setInitialData(response.body().getMESSAGES(), response.body().getUSER_ID()));
            }

            @Override
            public void onFailure(@NonNull Call<ChatGeneral> call, @NonNull Throwable t) {
            }
        });
    }

    private ArrayList<ChatItem> setInitialData(ArrayList<Message> messagesList, long accUserId) {
        ArrayList<ChatItem> chatItemList = new ArrayList<>();

        if (messagesList != null) {
            for(int i = 0; i < messagesList.size(); i++){
                chatItemList.add(new ChatItem(
                        accUserId,
                        messagesList.get(i).getFromUser().getId(),
                        messagesList.get(i).getFromUser().getName(),
                        messagesList.get(i).getFromUser().getLogo(),
                        messagesList.get(i).getMediaURI(),
                        messagesList.get(i).getText(),
                        messagesList.get(i).getMessageTime()));
            }
        }

        return chatItemList;
    }

    public LiveData<ArrayList<ChatItem>> getMessages() {
        return messages;
    }

    /**
     * For pass chat room id from activity
     */
    void setChatIds(long chatRoomId, long chatId){
        ChatViewModel.chatRoomId = chatRoomId;
        ChatViewModel.chatId = chatId;
        makeRetrofitQuery();
        Log.d("setChatId: ", Long.toString(chatRoomId));
    }

    public long getChatRoomId() {
        return chatRoomId;
    }

    public long getChatId() {
        return chatId;
    }

    /**
     * For get pass from activity to fragment
     */
    void setCardInfo(String card_userName, String card_message, String card_time, String card_logo, String card_media){
        cardInfo = new ChatListItem(
                0, 0, 0,
                card_userName, card_logo,
                card_message, card_media, card_time
        );
    }

    public ChatListItem getCardInfo(){
        return cardInfo;
    }
}
