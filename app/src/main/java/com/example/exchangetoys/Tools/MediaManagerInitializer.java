package com.example.exchangetoys.Tools;

import android.content.Context;

import com.cloudinary.android.MediaManager;

import java.util.HashMap;
import java.util.Map;

public class MediaManagerInitializer {
    private static MediaManagerInitializer mediaManagerInitializer;
    private static boolean isInitialize=false;
    public static MediaManagerInitializer getInstance(Context context){
        if(!isInitialize){
            new MediaManagerInitializer(context);
            isInitialize=true;
        }

        return mediaManagerInitializer;
    }
    private MediaManagerInitializer(Context context){
        Map config = new HashMap();
        config.put("cloud_name", "dxlmhjfv1");
        config.put("api_key", "766496874972834");
        config.put("api_secret", "wADqusLIUpVTvVD3aKBrLWGoHy4");
        MediaManager.init(context, config);
    }

}
