//package com.example.exchangetoys.Tools;
//
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.net.Uri;
//import android.os.Environment;
//import android.provider.MediaStore;
//import android.widget.ImageView;
//
//import java.io.File;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//public class TakePhotoAndReturnBitmap {
//
//    private String pictureImagePath = "";
//    private void openBackCamera() {
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = timeStamp + ".jpg";
//        File storageDir = Environment.getExternalStoragePublicDirectory(
//                Environment.DIRECTORY_PICTURES);
//        pictureImagePath = storageDir.getAbsolutePath() + "/" + imageFileName;
//        File file = new File(pictureImagePath);
//        Uri outputFileUri = Uri.fromFile(file);
//        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
//        startActivityForResult(cameraIntent, 1);
//    }
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == 1) {
//            File imgFile = new  File(pictureImagePath);
//            if(imgFile.exists()){
//                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
////                ImageView myImage = (ImageView) findViewById(R.id.imageviewTest);
////                myImage.setImageBitmap(myBitmap);
//
//            }
//        }
//
//    }
//}
