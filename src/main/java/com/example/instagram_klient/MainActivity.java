package com.example.instagram_klient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.instagram_klient.api.ApiHelpers;
import com.example.instagram_klient.model.Post;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String token = ApiHelpers.getToken(this);
        Log.d("xxxx", "onCreate: " + token);

        if(token.length() == 0) {
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);
        }
        else
        {
            Call<List<Post>> call = ApiHelpers.dataApi.getAllPosts(token);
            call.enqueue(new Callback<List<Post>>() {
                @Override
                public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                    if(response.isSuccessful())
                    {
                        Intent i = new Intent(MainActivity.this, ScrollingActivity.class);
                        Log.d("xxxx", "autoLogin");
                        Serializable serializable = (Serializable) response.body();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("posts", serializable);
                        startActivity(i, bundle);
                    }
                    else
                    {
                        Log.d("xxxx", "failedResponse");
                        Intent i = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(i);

                    }
                }

                @Override
                public void onFailure(Call<List<Post>> call, Throwable t) {
                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(i);
                    Log.d("xxxx", "utterlyFailed");
                }
            });
        }
    }
}