package com.example.exchangetoys;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exchangetoys.Services.PhotoNetworkClient;
import com.example.exchangetoys.Services.PhotoService;
import com.example.exchangetoys.recycleAdapters.ImageAdapter;
import com.example.exchangetoys.recycleAdapters.ImageArrayAdapter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddToyActivity extends Activity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private EditText name, description;
    private Spinner age, category, tags;
    private CheckBox isDidactic, isVintage;
    private RecyclerView photos;
    private Button makePhoto, confirm;
    private ArrayList<ImageAdapter> itemList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_toy_activity);
        name = findViewById(R.id.editText);
        description = findViewById(R.id.editText2);
        age = findViewById(R.id.age_spinner2);
        category = findViewById(R.id.category_spinner2);
        tags = findViewById(R.id.tag_spinner2);
        isDidactic = findViewById(R.id.is_didactic2);
        isVintage = findViewById(R.id.is_vintage2);
        makePhoto = findViewById(R.id.make_photo_button);
        confirm = findViewById(R.id.confirm_adding_toy);
        makePhoto.setOnClickListener(v -> {
            makePhotoFunction();
        });
        confirm.setOnClickListener(v -> {
            confirmFunction();
        });
        itemList = new ArrayList<>();
        ImageArrayAdapter itemArrayAdapter = new ImageArrayAdapter(itemList,R.layout.fragment_photo);
        photos= findViewById(R.id.uploaded_photos);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(AddToyActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        photos.setLayoutManager(linearLayoutManager);
        photos.setItemAnimator(new DefaultItemAnimator());
        photos.addItemDecoration(new DividerItemDecoration(AddToyActivity.this, DividerItemDecoration.HORIZONTAL));
        photos.setAdapter(itemArrayAdapter);


    }

    private void makePhotoFunction() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
//                MediaStore.Images.Media.EXTERNAL_CONTENT_URI.getPath());
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            //Toast.makeText(this,"I made photo",Toast.LENGTH_SHORT);
        }

    }

    //to get image from camera
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            itemList.add(new ImageAdapter(imageBitmap));
           // ImageArrayAdapter itemArrayAdapter = new ImageArrayAdapter(itemList,R.layout.fragment_photo);
           // photos.setAdapter(itemArrayAdapter);
           // imageView.setImageBitmap(imageBitmap);
            uploadToServer(convertBitmapToFilePath(imageBitmap));
        }
    }

    private String convertBitmapToFilePath(Bitmap bitmap) {


//create a file to write bitmap data
        File f = new File(this.getCacheDir(), "temp_");//todo jakaś lepsza nazwa
        String path = null;

//Convert bitmap to byte array

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0 /*ignored for PNG*/, bos);
        byte[] bitmapdata = bos.toByteArray();

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
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }

    private void uploadToServer(String filePath) {
        Retrofit retrofit = PhotoNetworkClient.getRetrofitClient(this);
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
                    Toast.makeText(AddToyActivity.this, "I send photo", Toast.LENGTH_SHORT);
                else
                    Toast.makeText(AddToyActivity.this, "response isn't successful", Toast.LENGTH_SHORT);

            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(AddToyActivity.this, "on failure", Toast.LENGTH_SHORT);
            }
        });
    }

    private void confirmFunction() {

    }
}
