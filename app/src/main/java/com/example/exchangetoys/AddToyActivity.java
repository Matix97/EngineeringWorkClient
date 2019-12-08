package com.example.exchangetoys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class AddToyActivity extends Activity {

    private EditText name, description;
    private Spinner age,category,tags;
    private CheckBox isDidactic, isVintage;
    private RecyclerView photos;
    private Button makePhoto,confirm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_toy_activity);
        name=findViewById(R.id.editText);
        description=findViewById(R.id.editText2);
        age=findViewById(R.id.age_spinner2);
        category=findViewById(R.id.category_spinner2);
        tags=findViewById(R.id.tag_spinner2);
        isDidactic=findViewById(R.id.is_didactic2);
        isVintage=findViewById(R.id.is_vintage2);
        makePhoto=findViewById(R.id.make_photo_button);
        confirm=findViewById(R.id.confirm_adding_toy);
        makePhoto.setOnClickListener(v->{
            makePhotoFunction();
        });
        confirm.setOnClickListener(v->{
            confirmFunction();
        });



    }
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private void makePhotoFunction() {
        Intent takePictureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void confirmFunction() {

    }
}
