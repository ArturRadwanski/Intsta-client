package com.example.instagram_klient.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.instagram_klient.api.LogInApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiHelpers {

    static final String PREF_TOKEN_NAME= "token";
    static Gson gson = new GsonBuilder()
            .setLenient()
            .create();
    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.55.111:3000")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();


    public static final LogInApi logInApi = retrofit.create(LogInApi.class);
    public static final DataApi dataApi = retrofit.create(DataApi.class);



    public static SharedPreferences getSharedPreferences(Context ctx)
    {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }
    public static void setToken(Context ctx, String token)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_TOKEN_NAME, token);
        editor.apply();
    }
    public static String getToken(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_TOKEN_NAME, "");
    }
}
