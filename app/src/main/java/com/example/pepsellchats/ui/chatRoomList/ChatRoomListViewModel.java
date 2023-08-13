package com.example.pepsellchats.ui.chatRoomList;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pepsellchats.retrofit.chatRoomList.ChatRoomList;
import com.example.pepsellchats.retrofit.chatRoomList.Chatroom;
import com.example.pepsellchats.retrofit.chatRoomList.ChatRoomListGETBody;
import com.example.pepsellchats.retrofit.RetrofitApi;

import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatRoomListViewModel extends ViewModel {
    private final static String BASE_URL = "https://wapp.pepsell.net/Pepsell2/";
    private final static String TYPE = "CHATROOM_LIST";
    private final static String USER_ID = "380990143524";
    private final static String APP_ID = "1";

    private final MutableLiveData<ArrayList<ChatRoomListItem>> cardsItems;

    public ChatRoomListViewModel() {
        cardsItems = new MutableLiveData<>();

        makeChatroomsData();
    }

    private void makeChatroomsData() {
        Call<ChatRoomList> call = makeRetrofitQuery();

        generateCall(call);
    }

    private Call<ChatRoomList> makeRetrofitQuery() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);

        ChatRoomListGETBody chatRoomListGETBody = new ChatRoomListGETBody(TYPE, USER_ID, APP_ID, new Date().getTime());

        return retrofitApi.getChatRoomList(chatRoomListGETBody);
    }

    private void generateCall(Call<ChatRoomList> call) {
        call.enqueue(new Callback<ChatRoomList>() {
            @Override
            public void onResponse(@NonNull Call<ChatRoomList> call,
                                   @NonNull Response<ChatRoomList> response) {
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

    public LiveData<ArrayList<ChatRoomListItem>> getItems() {
        return cardsItems;
    }

    private ArrayList<ChatRoomListItem> setInitialData(ArrayList<Chatroom> chatroomItems) {
        ArrayList<ChatRoomListItem> chatRoomListItem = new ArrayList<>();

        if (chatroomItems != null) {
            for (Chatroom item : chatroomItems) {
                chatRoomListItem.add(new ChatRoomListItem(item.getID(), item.getName(),
                        item.getDESCRIPTION(), item.getHotlinePhone(), item.getMEDIA_URI()));
            }
        }

        return chatRoomListItem;
    }
}