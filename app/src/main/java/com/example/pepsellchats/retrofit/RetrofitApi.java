package com.example.pepsellchats.retrofit;

import com.example.pepsellchats.retrofit.chatList.ChatListGeneral;
import com.example.pepsellchats.retrofit.chatList.ChatListPostBody;
import com.example.pepsellchats.retrofit.chatRoomList.ChatRoomList;
import com.example.pepsellchats.retrofit.chatRoomList.ChatRoomListPostBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitApi {
    @POST("api")
    Call<ChatRoomList> getChatRoomList(@Body ChatRoomListPostBody body);

    @POST("api")
    Call<ChatListGeneral> getChatList(@Body ChatListPostBody body);
}
