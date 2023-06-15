package com.example.instagram_klient.api;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface LogInApi {

    @POST("/api/user/register")
    Call<String> register(@Body Map<String, String> body);

    @POST("/api/user/login")
    Call<String> logIn(@Body Map<String, String> body);

    @GET("/api/user/confirm/{token}")
    Call<String> confirmUser(@Path("token") String token);
}
