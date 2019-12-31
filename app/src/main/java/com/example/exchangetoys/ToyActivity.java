package com.example.exchangetoys;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.exchangetoys.DTOs.ToyServiceData.Toy;
import com.example.exchangetoys.Tools.DownloadImageTask;

public class ToyActivity extends Activity {
    ImageView image;
    TextView name, description;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toy_activity);
        Bundle bundle = getIntent().getExtras();
        Toy toy = bundle.getParcelable("toy");
        //image=findViewById(R.id.toy_activity_image);
        name=findViewById(R.id.toy_activity_name);
        description=findViewById(R.id.toy_activity_description);
        name.setText(toy.getToy_name());
        description.setText(toy.getToy_description());
        new DownloadImageTask( findViewById(R.id.toy_activity_image))
                .execute("https://i.ibb.co/7bmC1Kh/19889-en-1.jpg");
    }
}
