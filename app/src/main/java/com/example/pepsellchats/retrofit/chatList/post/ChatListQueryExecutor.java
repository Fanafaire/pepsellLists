package com.example.pepsellchats.retrofit.chatList.post;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.pepsellchats.retrofit.BodyCallTypes;
import com.example.pepsellchats.retrofit.BodyConstants;
import com.example.pepsellchats.retrofit.RetrofitApi;
import com.example.pepsellchats.retrofit.chat.post.ChatPOSTBody;
import com.example.pepsellchats.retrofit.chat.post.MessageTextForPost;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatListQueryExecutor {
    private final MutableLiveData<Long> respondMessage;
    private RetrofitApi retrofitApi;

    public ChatListQueryExecutor() {
        initiateRetrofit();
        respondMessage = new MutableLiveData<>();
    }

    public MutableLiveData<Long> getPOSRRespond(String postType, long chatRoomId, long chatId, String messageText, long time){

        ChatListChatPOSTBody chatListChatPOSTBody = new ChatListChatPOSTBody(chatId, chatRoomId, messageText);

        ChatListPOSTBody chatListPOSTBody = new ChatListPOSTBody(
                postType,
                Long.toString(BodyConstants.USER_ID),
                BodyConstants.APP_ID,
                time,
                chatListChatPOSTBody);

        Call<ChatListPOSTReturn> call = null;

        if(BodyCallTypes.CREATE_TEXT_CHAT.toString().equals(postType)){
            call = retrofitApi.postTextChat(chatListPOSTBody);
        } else if (BodyCallTypes.CREATE_MEDIA_CHAT.toString().equals(postType)){}

        if(call != null)
            generateCall(call);

        return respondMessage;
    }

    private void generateCall(Call<ChatListPOSTReturn> call) {
        call.enqueue(new Callback<ChatListPOSTReturn>() {
            @Override
            public void onResponse(@NonNull Call<ChatListPOSTReturn> call, @NonNull Response<ChatListPOSTReturn> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    return;
                }

                respondMessage.setValue(response.body().getCHAT_ID());
                Log.d("POST return: ", response.body().getSTATUS());
            }

            @Override
            public void onFailure(@NonNull Call<ChatListPOSTReturn> call, @NonNull Throwable t) {
                Log.d("POST return: ", "fail");
            }
        });
    }

    private void initiateRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BodyConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitApi = retrofit.create(RetrofitApi.class);
    }
}
