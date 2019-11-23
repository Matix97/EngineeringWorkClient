package com.example.exchangetoys.Services;

import com.example.exchangetoys.DTOs.ToyServiceData.Rental;
import com.example.exchangetoys.DTOs.ToyServiceData.Toy;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ToyService {

    @GET("toys")
    Call<List<Toy>> getToys();
//    @POST("toys/choosing")//download more information about specific toy ///w sumie zbędne, od razu całe info
//    Call<Void> chooseToy(@Body ExtendedToy extendedToy);
    @POST("addToy")//add new advertisement
    Call<Void> addToy(@Body Toy toy);
    @POST("editToy")
    Call<Void> editToy(@Body Toy toy);
    @GET("toys/yourAdvert")
    Call<List<Toy>> getYourToysAdvert();
    @GET("toys/yourRentedToy")
    Call<List<Toy>> getYourRentedToys();
    @POST("confirmRent")
    Call<Void> confirmRent(@Body Rental rental );

}
