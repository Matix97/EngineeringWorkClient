package com.example.exchangetoys.Tools;

import android.content.Context;
import android.graphics.Bitmap;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class UploadImage {

    public static void execute(Bitmap bitmap, Context context) throws IOException {

        //Convert bitmap to byte array
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50 /*ignored for PNG*/, bos);
        byte[] bitmapData = bos.toByteArray();
        //create file
        String currentDate = new SimpleDateFormat("ddMMyyyy_HHmmss", Locale.getDefault()).format(new Date());
        File f = new File(context.getCacheDir(), "temp_" + currentDate + ".jpg");
        f.createNewFile();
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(bitmapData);
        fos.flush();
        fos.close();

        String requestId = MediaManager.get().upload(f.getAbsolutePath()).callback(new UploadCallback() {
            @Override
            public void onStart(String requestId) {
                // your code here
            }

            @Override
            public void onProgress(String requestId, long bytes, long totalBytes) {
                // example code starts here
                Double progress = (double) bytes / totalBytes;
                // post progress to app UI (e.g. progress bar, notification)
                // example code ends here
            }

            @Override
            public void onSuccess(String requestId, Map resultData) {
                // your code here
                // TODO: 27/12/2019 tutaj mamy adres url, który musimy do bazki zapisać
                UploadedPhotoURL.addUrl(resultData.get("url").toString());
            }

            @Override
            public void onError(String requestId, ErrorInfo error) {
                // your code here
            }

            @Override
            public void onReschedule(String requestId, ErrorInfo error) {
                // your code here
            }
        }).dispatch();


    }

}
