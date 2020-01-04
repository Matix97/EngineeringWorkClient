package com.example.exchangetoys.Services;

import com.example.exchangetoys.DTOs.ToyServiceData.AddToyDTO;
import com.example.exchangetoys.DTOs.ToyServiceData.FilterDTO;
import com.example.exchangetoys.DTOs.ToyServiceData.RentalDTO;
import com.example.exchangetoys.DTOs.ToyServiceData.Toy;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ToyService {

    @POST("toy/filter")
    Call<List<Toy>> getToys(@Body FilterDTO filter);

    @POST("toy")
//add new advertisement
    Call<Void> addToy(@Body AddToyDTO toy);

    @POST("editToy")
    Call<Void> editToy(@Body Toy toy);

    @GET("toy/yourAdvert")
    Call<List<Toy>> getYourToysAdvert();

    @GET("toy/yourRentedToy")
    Call<List<Toy>> getYourRentedToys();

    @POST("confirmRent")
    Call<Void> confirmRent(@Body RentalDTO rental);

    @POST("toy/want")
    Call<Void> iWantAToy(@Body Long todId);

    @POST("toy/rent")
    Call<Void> rentToy(@Body RentalDTO rentalDTO);

}
