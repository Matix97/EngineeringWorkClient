package com.example.exchangetoys.Services;


import com.example.exchangetoys.DTOs.ToyServiceData.JwtResponse;
import com.example.exchangetoys.DTOs.ToyServiceData.SuggestedToy;
import com.example.exchangetoys.DTOs.UserServiceData.Child;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserService {

    @POST("register")
    Call<Void> registerParent(@Header("Authorization") byte[] message);

    @POST("child")
    Call<Void> registerChild(@Body String message);

    @POST("authenticate")
    Call<JwtResponse> login(@Header("Authorization") byte[] message);

    @POST("updateChild")
    Call<Void> updateChild(@Body Child child);

    @POST("suggestToy")
    Call<Void> suggestToy(@Body SuggestedToy suggestedToy);

}
