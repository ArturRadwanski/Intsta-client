package com.example.instagram_klient;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.example.instagram_klient.api.ApiHelpers;
import com.example.instagram_klient.api.LogInApi;
import com.example.instagram_klient.databinding.ActivityRegisterBinding;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setUpEvents();
    }
    private void setUpEvents()
    {
        binding.loginButton.setOnClickListener(view -> {
            Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(i);
        });
        binding.registerButton.setOnClickListener(view -> {

            String name = binding.nameInput.getText().toString();
            String lastName = binding.lastNameInput.getText().toString();
            String email = binding.emailInput.getText().toString();
            String password = binding.passwordInput.getText().toString();

            Map<String, String> map = new HashMap<>();
            map.put("name", name);
            map.put("lastName", lastName);
            map.put("email", email);
            map.put("password", password);
            Log.d("xxxx", name + " " + lastName + " " + email + " " + password);
            Call<String> call = ApiHelpers.logInApi.register(map);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if(response.isSuccessful())
                    {
                        Log.d("xxxx", response.body());
                       new AlertDialog.Builder(RegisterActivity.this)
                               .setTitle("verify")
                               .setMessage("copy it into your browser to verify http://localhost:3000/api/user/confirm" + response.body())
                               .setPositiveButton("verify", (dialog, which) -> {
                                   Call<String> confirmCall = ApiHelpers.logInApi.confirmUser(response.body());
                                   confirmCall.enqueue(new Callback<String>() {
                                       @Override
                                       public void onResponse(Call<String> call, Response<String> response) {
                                           Log.d("xxxx", response.body());
                                           Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                                           i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                           startActivity(i);
                                       }

                                       @Override
                                       public void onFailure(Call<String> call, Throwable t) {
                                            Log.d("xxxx", t.getMessage());
                                       }
                                   });
                               })
                               .create()
                               .show();
                    }
                    else {
                        Log.d("xxxx",Integer.toString(response.code()) );
                        Log.d("xxxx", response.message());
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    t.printStackTrace();
                    Log.d("xxxx", t.getMessage());
                    Log.d("xxxx", t.getCause().getMessage());
                }
            });
        });
    }
}