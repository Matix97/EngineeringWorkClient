package com.example.exchangetoys.Services;


import com.example.exchangetoys.DTOs.BearerToken;
import com.example.exchangetoys.DTOs.ToyServiceData.SuggestedToy;
import com.example.exchangetoys.DTOs.UserServiceData.Child;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserService {

    @POST("register")
    Call<Void> registerParent(@Header("Authorization") byte[] message);
    @POST("registerChild")
    Call<Child> registerChild(@Body Child child);
    @POST("authenticate")
    Call<BearerToken> login(@Header("Authorization") byte[] message);
    @POST("updateChild")
    Call<Void> updateChild(@Body Child child);
    @POST("suggestToy")
    Call<Void> suggestToy(@Body SuggestedToy suggestedToy);

}
