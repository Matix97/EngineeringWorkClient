package com.example.exchangetoys;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.exchangetoys.DTOs.ToyServiceData.SuggestedToy;
import com.example.exchangetoys.DTOs.UserServiceData.Child;
import com.example.exchangetoys.Services.ServiceGenerator;
import com.example.exchangetoys.Services.UserService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChildSettings  extends Activity {

    private Child child;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.child_settings);
        Bundle bundle = getIntent().getExtras();
        child = bundle.getParcelable("child");

        downloadSuggestion();

    }

    private void downloadSuggestion() {
        UserService userService = ServiceGenerator.createAuthorizedService(UserService.class);
        Call<List<SuggestedToy>> call = userService.getSuggestion();
        call.enqueue(new Callback<List<SuggestedToy>>() {
            @Override
            public void onResponse(Call<List<SuggestedToy>> call, Response<List<SuggestedToy>> response) {
                if (response.isSuccessful()) {


                } else {

                }
            }

            @Override
            public void onFailure(Call<List<SuggestedToy>> call, Throwable t) {

            }
        });
    }
}
