package com.example.popselllists.retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitApi {
    @POST("api")
    Call<ChatRoomList> getPosts(@Body PostBody body);
}
