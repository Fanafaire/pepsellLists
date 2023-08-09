package com.example.popselllists.retrofit;

import org.json.JSONObject;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface JsonPlaseHolderApi {
    // relative url here https://wapp.pepsell.net/Pepsell2


    @POST("api")
    Call<Post> getPosts(@Body PostBody body);
}
