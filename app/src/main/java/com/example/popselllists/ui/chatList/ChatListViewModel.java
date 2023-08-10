package com.example.popselllists.ui.chatList;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.popselllists.retrofit.ChatRoomList;
import com.example.popselllists.retrofit.Chatroom;
import com.example.popselllists.retrofit.PostBody;
import com.example.popselllists.retrofit.RetrofitApi;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatListViewModel extends ViewModel {
    private final static String TYPE = "CHATROOM_LIST";
    private final static String USER_ID = "380990143524";
    private final static String APP_ID = "1";
    private final static long DATE_TIME = 1690819233997L;

    private final MutableLiveData<ArrayList<ChatListItem>> cardsItems;

    public ChatListViewModel() {
        cardsItems = new MutableLiveData<>();

        makeChatroomsData();
    }

    private void makeChatroomsData() {
        Call<ChatRoomList> call = makeRetrofitQuery();

        generateCall(call);
    }

    private Call<ChatRoomList> makeRetrofitQuery() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://wapp.pepsell.net/Pepsell2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);

        PostBody postBody = new PostBody(TYPE, USER_ID, APP_ID, DATE_TIME);

        return retrofitApi.getPosts(postBody);
    }

    private void generateCall(Call<ChatRoomList> call) {
        Log.d("generateCall: ", "in");
        call.enqueue(new Callback<ChatRoomList>() {
            @Override
            public void onResponse(@NonNull Call<ChatRoomList> call, @NonNull Response<ChatRoomList> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    return;
                }

                cardsItems.setValue(setInitialData(response.body().getChatrooms()));
            }

            @Override
            public void onFailure(@NonNull Call<ChatRoomList> call, @NonNull Throwable t) {
            }
        });

    }

    public LiveData<ArrayList<ChatListItem>> getItems() {
        return cardsItems;
    }

    private ArrayList<ChatListItem> setInitialData(ArrayList<Chatroom> chatroomItems){
        ArrayList<ChatListItem> chatListItem = new ArrayList<>();

        if (chatroomItems != null) {
            for (Chatroom item : chatroomItems) {
                chatListItem.add(new ChatListItem(item.getName(), item.getDESCRIPTION(), item.getHotlinePhone(), null));
            }
        }

        return chatListItem;
    }
}