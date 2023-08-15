package com.example.pepsellchats.retrofit;

import com.example.pepsellchats.retrofit.chat.get.ChatGETBody;
import com.example.pepsellchats.retrofit.chat.get.ChatGeneral;
import com.example.pepsellchats.retrofit.chat.post.ChatPOSTBody;
import com.example.pepsellchats.retrofit.chat.post.ChatPOSTReturn;
import com.example.pepsellchats.retrofit.chatList.get.ChatListGeneral;
import com.example.pepsellchats.retrofit.chatList.get.ChatListGETBody;
import com.example.pepsellchats.retrofit.chatList.post.ChatListPOSTBody;
import com.example.pepsellchats.retrofit.chatList.post.ChatListPOSTReturn;
import com.example.pepsellchats.retrofit.chatRoomList.ChatRoomList;
import com.example.pepsellchats.retrofit.chatRoomList.ChatRoomListGETBody;

import java.io.File;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RetrofitApi {
    @POST(BodyConstants.URL_UPLOAD)
    Call<ChatRoomList> getChatRoomList(@Body ChatRoomListGETBody body);

    @POST(BodyConstants.URL_UPLOAD)
    Call<ChatListGeneral> getChatList(@Body ChatListGETBody body);

    @POST(BodyConstants.URL_UPLOAD)
    Call<ChatGeneral> getChatMessages(@Body ChatGETBody body);

    @POST(BodyConstants.URL_UPLOAD)
    Call<ChatPOSTReturn> postChatMessage(@Body ChatPOSTBody body);

    @Multipart
    @POST(BodyConstants.URL_UPLOAD_IMAGE)
    Call<ChatPOSTReturn> uploadImageMessage(
            @Part("FILE") File file,
            @Part("DATA_PACKET") ChatPOSTBody body
    );

    @POST(BodyConstants.URL_UPLOAD)
    Call<ChatListPOSTReturn> postTextChat(@Body ChatListPOSTBody body);

    @Multipart
    @POST(BodyConstants.URL_UPLOAD_IMAGE)
    Call<ChatListPOSTReturn> postImageChat(
            @Part("FILE") File file,
            @Part("DATA_PACKET") ChatListPOSTBody body
    );
}
