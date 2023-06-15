package com.example.instagram_klient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.instagram_klient.api.ApiHelpers;
import com.example.instagram_klient.databinding.ActivityLoginBinding;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setUpEvents();
    }

    private void setUpEvents()
    {
        binding.registerButton.setOnClickListener(view -> {
            Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(i);
        });
        binding.loginButton.setOnClickListener(view -> {
            Map<String, String> body = new HashMap<>();
            body.put("email", binding.emailInput.getText().toString());
            body.put("password", binding.passwordInput.getText().toString());

            Call<String> call = ApiHelpers.logInApi.logIn(body);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if(response.isSuccessful())
                    {
                        if(binding.stayLoggedCheckBox.isChecked())
                            ApiHelpers.setToken(LoginActivity.this, response.body());
                        Intent i = new Intent(LoginActivity.this, ScrollingActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });
        });
    }
}