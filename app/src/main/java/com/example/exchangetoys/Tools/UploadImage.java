package com.example.exchangetoys.Tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.Toast;

import com.example.exchangetoys.Services.PhotoNetworkClient;
import com.example.exchangetoys.Services.PhotoService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UploadImage {
    private static Context context;
   private static Bitmap bitmap;
   private static String path;
    public static void execute(Bitmap bitmap, Context context){
        UploadImage.context=context;
        UploadImage.bitmap=bitmap;
        convertBitmapToFilePath();
        uploadToServer(path);
    }

    private static void convertBitmapToFilePath() {
        String currentDate = new SimpleDateFormat("ddMMyyyy_HHmmss", Locale.getDefault()).format(new Date());
        File f = new File(context.getCacheDir(), "temp_"+currentDate );//todo jakaś lepsza nazwa
        //Convert bitmap to byte array
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0 /*ignored for PNG*/, bos);
        byte[] bitmapData = bos.toByteArray();

        //write the bytes in file
        FileOutputStream fos = null;
        try {
            f.createNewFile();
            fos = new FileOutputStream(f);
            path = f.getPath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fos.write(bitmapData);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void uploadToServer(String filePath) {
        Retrofit retrofit = PhotoNetworkClient.getRetrofitClient(context);
        PhotoService uploadAPIs = retrofit.create(PhotoService.class);
        //Create a file object using file path
        File file = new File(filePath);
        // Create a request body with file and image media type
        RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), file);
        // Create MultipartBody.Part using file request-body,file name and part name
        MultipartBody.Part part = MultipartBody.Part.createFormData("image", file.getName(), fileReqBody);
        //Create request body with text description and text media type
        RequestBody description = RequestBody.create(MediaType.parse("text/plain"), "image-type");
        //
        Call call = uploadAPIs.uploadImage(PhotoNetworkClient.KEY_API,  part,description);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful())
                    Toast.makeText(context, "I send photo", Toast.LENGTH_SHORT);
                else
                    Toast.makeText(context, "response isn't successful", Toast.LENGTH_SHORT);

            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(context, "on failure", Toast.LENGTH_SHORT);
            }
        });
    }
}
