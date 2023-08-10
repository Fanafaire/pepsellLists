package com.example.popselllists.ui.dashboard;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.popselllists.retrofit.Chatroom;
import com.example.popselllists.retrofit.RetrofitApi;
import com.example.popselllists.retrofit.ChatRoomList;
import com.example.popselllists.retrofit.PostBody;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DashboardViewModel extends ViewModel {

    private final static String TYPE = "CHATROOM_LIST";
    private final static String USER_ID = "380990143524";
    private final static String APP_ID = "1";
    private final static long DATE_TIME = 1690819233997L;

    private final MutableLiveData<ArrayList<Chatroom>> chatrooms;

    public DashboardViewModel() {
        chatrooms = new MutableLiveData<>();
        makeRetrofitQuery();
    }

    private void makeRetrofitQuery() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://wapp.pepsell.net/Pepsell2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);

        PostBody postBody = new PostBody(TYPE, USER_ID, APP_ID, DATE_TIME);

        Call<ChatRoomList> call = retrofitApi.getPosts(postBody);

        generateCall(call);
    }

    public LiveData<ArrayList<Chatroom>> getChatrooms() {
        return chatrooms;
    }

    private void generateCall(Call<ChatRoomList> call) {
        call.enqueue(new Callback<ChatRoomList>() {
            @Override
            public void onResponse(@NonNull Call<ChatRoomList> call, @NonNull Response<ChatRoomList> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    return;
                }

                chatrooms.setValue(response.body().getChatrooms());
            }

            @Override
            public void onFailure(@NonNull Call<ChatRoomList> call, @NonNull Throwable t) {
            }
        });
    }
}