package com.example.exchangetoys.Services;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface PhotoService {
    @Multipart()
    @POST("/upload")
    Call<ResponseBody> uploadImage(@Query ("key") String key,@Part() MultipartBody.Part file);

}
