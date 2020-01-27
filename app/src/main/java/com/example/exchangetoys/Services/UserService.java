package com.example.exchangetoys.Services;


import com.example.exchangetoys.DTOs.ToyServiceData.JwtResponse;
import com.example.exchangetoys.DTOs.ToyServiceData.SuggestedToy;
import com.example.exchangetoys.DTOs.ToyServiceData.Toy;
import com.example.exchangetoys.DTOs.UserServiceData.Child;
import com.example.exchangetoys.DTOs.UserServiceData.ChildUpdateDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserService {

    @POST("register")
    Call<Void> registerParent(@Header("Authorization") String message);

    @POST("authenticate")
    Call<JwtResponse> login(@Header("Authorization") String message);

    @GET("child")
    Call<List<Child>> getChild();

    @GET("adults/suggestion")
    Call<List<SuggestedToy>> getSuggestion();

    @POST("child")
    Call<Void> registerChild(@Body String message);

    @GET("child/my-data")
    Call<Child> getMyChildData();



    @GET("child/want")
    Call<List<Toy>> getMySuggestion();

    @POST("child/update")
    Call<Child> updateChild(@Body ChildUpdateDTO childUpdateDTO);

    @GET("child/suggestion")
    Call<List<Toy>> getChildSuggestion();
//    @POST("updateChild")
//    Call<Void> updateChild(@Body Child child);
//
//    @POST("suggestToy")
//    Call<Void> suggestToy(@Body SuggestedToy suggestedToy);

}
