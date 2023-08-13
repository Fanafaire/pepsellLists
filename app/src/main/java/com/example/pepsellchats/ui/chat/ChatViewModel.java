package com.example.pepsellchats.ui.chat;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pepsellchats.retrofit.RetrofitApi;
import com.example.pepsellchats.retrofit.chat.ChatGETBody;
import com.example.pepsellchats.retrofit.chat.ChatGeneral;
import com.example.pepsellchats.retrofit.chat.Message;
import com.example.pepsellchats.ui.chatList.ChatListItem;

import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatViewModel extends ViewModel {
    // For get/post body
    private final static String TYPE = "CHAT_HISTORY";
    private final static String USER_ID = "380990143524";
    private final static String APP_ID = "1";
    private final static long START_PERIOD = 1690819233997L;
    private final static long FINISH_PERIOD = 1690819233997L;
    private final static int LIMIT = 30;
    // Ids for get body
    private long chatRoomId, chatId;
    // For card
    private String card_userName, card_message, card_time, card_logo, card_media;
    // Result liveData
    private final MutableLiveData<ArrayList<ChatItem>> messages;

    public ChatViewModel() {
        messages = new MutableLiveData<>();
        makeRetrofitQuery();
    }

    private void makeRetrofitQuery() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://wapp.pepsell.net/Pepsell2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);

        ChatGETBody chatGETBody = new ChatGETBody(TYPE, USER_ID, APP_ID, new Date().getTime(),
                chatRoomId, chatId, START_PERIOD, FINISH_PERIOD, LIMIT);

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
            for(int i = messagesList.size() - 1; i >= 0; i--){
                chatItemList.add(new ChatItem(accUserId, messagesList.get(i).getFromUser().getId(),
                        messagesList.get(i).getFromUser().getName(), messagesList.get(i).getFromUser().getLogo(),
                        messagesList.get(i).getMediaURI(), messagesList.get(i).getText(), messagesList.get(i).getMessageTime()));
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
        this.chatRoomId = chatRoomId;
        this.chatId = chatId;
        Log.d("setChatId: ", Long.toString(chatRoomId));
    }

    /**
     * For get pass from activity to fragment
     */
    void setCardInfo(String card_userName, String card_message, String card_time, String card_logo, String card_media){
        this.card_userName = card_userName;
        this.card_message = card_message;
        this.card_time = card_time;
        this.card_logo = card_logo;
        this.card_media = card_media;
    }

    public ChatListItem getCardInfo(){
        return new ChatListItem(0, 0, 0,
                card_userName, card_logo, card_message, card_media, card_time);
    }
}