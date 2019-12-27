package com.example.exchangetoys.Tools;

import java.util.ArrayList;

public class UploadedPhotoURL {
    private static ArrayList<String> urls;
    public static boolean ALL_IMAGE_UPLOADED;
    public static int IMAGE_COUNT_TO_UPLOAD;

    public static void init(){
        urls=new ArrayList<>();
        ALL_IMAGE_UPLOADED=false;
        IMAGE_COUNT_TO_UPLOAD=0;
    }
    public static void clear(){
        urls.clear();
        ALL_IMAGE_UPLOADED=false;
        IMAGE_COUNT_TO_UPLOAD=0;
    }

    public static ArrayList<String> getUrls() {
        return urls;
    }
    public static void addUrl(String s){
        urls.add(s);
        if(IMAGE_COUNT_TO_UPLOAD==urls.size()){
            ALL_IMAGE_UPLOADED=true;
        }
    }

}
