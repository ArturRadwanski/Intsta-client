package com.example.instagram_klient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.instagram_klient.api.ApiHelpers;
import com.example.instagram_klient.databinding.ActivityScrollingBinding;
import com.example.instagram_klient.model.User;
import com.example.instagram_klient.observabledata.Profile;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScrollingActivity extends AppCompatActivity {

    ProfileFragment profileFragment = new ProfileFragment();
    PostFragment postFragment = new PostFragment();
    ActivityScrollingBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScrollingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(postFragment);
        handleNavigation();
    }
    private void handleNavigation()
    {
        binding.menuBottom.setOnItemSelectedListener(v -> {
            v.setChecked(true);
            switch (v.getItemId()) {

                case R.id.profileMenu:
                    replaceFragment(profileFragment);
                    String token = ApiHelpers.getToken(ScrollingActivity.this);
                    Call<User> call = ApiHelpers.dataApi.getProfile(token);
                    call.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            assert response.body() != null;
                            Log.d("xxxx", response.body().toString());
                            if(response.isSuccessful())
                            {
                                User user = response.body();
                                Profile prof = new Profile();
                                profileFragment.binding.setUser(prof);

                                prof.setEmail(user.getEmail());
                                prof.setName(user.getName());
                                prof.setLastName(user.getLastName());
                                user.setPhotoId(user.getPhotoId());




                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Log.d("xxxx", t.getMessage());
                        }
                    });
                    break;
                case R.id.logOutMenu:
                    ApiHelpers.setToken(ScrollingActivity.this, "");
                    Intent i = new Intent(ScrollingActivity.this, LoginActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    break;
                case R.id.homeMenu:
                    replaceFragment(postFragment);
            }
            return false;
        });
    }
    private void replaceFragment(Fragment fragment)
    {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .commit();
    }
}