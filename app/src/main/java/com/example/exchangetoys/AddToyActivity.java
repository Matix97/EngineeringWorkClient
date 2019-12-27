package com.example.exchangetoys;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cloudinary.android.MediaManager;
import com.example.exchangetoys.DTOs.ToyServiceData.AddToyDTO;
import com.example.exchangetoys.Services.ServiceGenerator;
import com.example.exchangetoys.Services.ToyService;
import com.example.exchangetoys.Tools.ImageUtils;
import com.example.exchangetoys.Tools.UploadImage;
import com.example.exchangetoys.Tools.UploadedPhotoURL;
import com.example.exchangetoys.recycleAdapters.ImageAdapter;
import com.example.exchangetoys.recycleAdapters.ImageArrayAdapter;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddToyActivity extends Activity {

    static final int REQUEST_TAKE_PHOTO = 1;
    private EditText name, description;
    private Spinner age, category, tags;
    private CheckBox isDidactic, isVintage;
    private RecyclerView photos;
    private Button makePhoto, confirm;
    private ArrayList<ImageAdapter> itemList;
    private File pictureImagePath = null;
    private UploadedPhotoURL uploadedPhotoURLs;

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
            dispatchTakePictureIntent();

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

        Map config = new HashMap();
        config.put("cloud_name", "dxlmhjfv1");
        config.put("api_key", "766496874972834");
        config.put("api_secret", "wADqusLIUpVTvVD3aKBrLWGoHy4");
        MediaManager.init(this, config);

        uploadedPhotoURLs.init();


    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        return image;
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
                pictureImagePath=photoFile;
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);

            }
        }
    }

//    //to get image from camera
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            File imgFile = pictureImagePath;
            if(imgFile.exists()){
                Bitmap myBitmap =  ImageUtils.getInstant().getCompressedBitmap(imgFile.getAbsolutePath());
                itemList.add(new ImageAdapter(myBitmap));
                try {

                    UploadImage.execute(myBitmap,this);
                    UploadedPhotoURL.IMAGE_COUNT_TO_UPLOAD++;//to synchronise

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }


    private void confirmFunction() {
        if(UploadedPhotoURL.ALL_IMAGE_UPLOADED){
            ArrayList<String> photoURLS = UploadedPhotoURL.getUrls();
            ToyService toyService = ServiceGenerator.createAuthorizedService(ToyService.class);
            AddToyDTO addToyDTO=new AddToyDTO();
            addToyDTO.setName(name.getText().toString());
            addToyDTO.setDescription(description.getText().toString());
            addToyDTO.setAgeRange(age.getSelectedItem().toString());
            addToyDTO.setCategory(category.getSelectedItem().toString());
            ArrayList<String> tagsTemp=new ArrayList<>();
            tagsTemp.add(tags.getSelectedItem().toString());
            addToyDTO.setTags(tagsTemp);
            addToyDTO.setIfDidactic(isDidactic.isChecked());
            addToyDTO.setIfVintage(isVintage.isChecked());
            addToyDTO.setToysFactoryName("");
            addToyDTO.setPhotosURLs(photoURLS);
            Call<Void> call = toyService.addToy(addToyDTO);
            call.enqueue(new Callback<Void>() {
                @Override // TODO: 27/12/2019 jakieś komunikaty czy ogłoszenie dodane 
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {

                    } else
                    {
                        
                    }
                        
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                   
                }
            });

            UploadedPhotoURL.clear();
        }
    }
}
