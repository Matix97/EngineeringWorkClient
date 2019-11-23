package com.example.exchangetoys.Services;


import com.example.exchangetoys.DTOs.BearerToken;
import com.example.exchangetoys.DTOs.UserServiceData.Adult;
import com.example.exchangetoys.DTOs.UserServiceData.Child;
import com.example.exchangetoys.DTOs.UserServiceData.ChoosenChild;
import com.example.exchangetoys.DTOs.UserServiceData.RegisterChild;
import com.example.exchangetoys.DTOs.UserServiceData.RegisterParent;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserService {

    @POST("registerParent")
    Call<Adult> registerParent(@Body RegisterParent register);
    @POST("registerChild")
    Call<Child> registerChild(@Body RegisterChild register);
    @POST("login")
    Call<BearerToken> login(@Header("Authorization") String basicAuthCredentials);
    @POST("chooseChild")
    Call<Child> chooseChild(@Body ChoosenChild choosenChild);
    @POST("updateChild")
    Call<Void> updateChild(@Body ChoosenChild choosenChild);

}
