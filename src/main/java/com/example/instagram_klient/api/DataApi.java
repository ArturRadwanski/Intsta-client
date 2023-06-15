package com.example.instagram_klient.api;

import com.example.instagram_klient.model.Post;
import com.example.instagram_klient.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface DataApi {
    @GET("/api/photos/post")
    Call<List<Post>> getAllPosts(@Header("Authorization") String token);

    @GET("/api/photos/profile")
    Call<User> getProfile(@Header("Authorization") String token);
}
