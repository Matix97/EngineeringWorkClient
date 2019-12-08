package com.example.exchangetoys.Services;

import java.io.File;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PhotoService {
   // @Multipart()
    @POST("/upload")
    Call<ResponseBody> uploadImage(@Query("key") String key, @Query("image") File string);

}
