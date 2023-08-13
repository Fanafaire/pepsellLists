package com.example.pepsellchats.retrofit;

import com.example.pepsellchats.retrofit.chat.ChatGETBody;
import com.example.pepsellchats.retrofit.chat.ChatGeneral;
import com.example.pepsellchats.retrofit.chatList.ChatListGeneral;
import com.example.pepsellchats.retrofit.chatList.ChatListGETBody;
import com.example.pepsellchats.retrofit.chatRoomList.ChatRoomList;
import com.example.pepsellchats.retrofit.chatRoomList.ChatRoomListGETBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitApi {
    @POST("api")
    Call<ChatRoomList> getChatRoomList(@Body ChatRoomListGETBody body);

    @POST("api")
    Call<ChatListGeneral> getChatList(@Body ChatListGETBody body);

    @POST("api")
    Call<ChatGeneral> getChatMessages(@Body ChatGETBody body);

    @POST("api")
    Call<ChatGeneral> postChatMessage(@Body ChatGETBody body);
}
