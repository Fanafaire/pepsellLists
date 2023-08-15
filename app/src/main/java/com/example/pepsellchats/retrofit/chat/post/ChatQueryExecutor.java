package com.example.pepsellchats.retrofit.chat.post;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.pepsellchats.retrofit.BodyCallTypes;
import com.example.pepsellchats.retrofit.BodyConstants;
import com.example.pepsellchats.retrofit.RetrofitApi;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatQueryExecutor {
    private final MutableLiveData<ChatPOSTReturn> respondMessage;
    private RetrofitApi retrofitApi;
    private File file;

    public ChatQueryExecutor() {
        initiateRetrofit();
        respondMessage = new MutableLiveData<>();
    }

    public MutableLiveData<ChatPOSTReturn> getPOSRRespond(String postType, long chatRoomId, long chatId, String messageText, long time){

        ChatPOSTBody chatPOSTBody = new ChatPOSTBody(
                postType,
                Long.toString(BodyConstants.USER_ID),
                BodyConstants.APP_ID,
                time,
                chatRoomId,
                chatId,
                new MessageTextForPost(messageText));

        Call<ChatPOSTReturn> call = null;

        if(BodyCallTypes.SEND_CHAT_TEXT.toString().equals(postType)){
            call = retrofitApi.postChatMessage(chatPOSTBody);
        } else if (BodyCallTypes.SEND_CHAT_MEDIA.toString().equals(postType) && file != null){
            call = retrofitApi.uploadImageMessage(file, chatPOSTBody);
        }

        if(call != null)
            generateCall(call);

        return respondMessage;
    }

    public void setFile(Uri imageUri){
        file = new File(imageUri.getPath());
    }

    private void generateCall(Call<ChatPOSTReturn> call) {
        call.enqueue(new Callback<ChatPOSTReturn>() {
            @Override
            public void onResponse(@NonNull Call<ChatPOSTReturn> call, @NonNull Response<ChatPOSTReturn> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    return;
                }

                respondMessage.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ChatPOSTReturn> call, @NonNull Throwable t) {
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
