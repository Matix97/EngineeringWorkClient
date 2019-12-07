package com.example.exchangetoys;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.exchangetoys.ui.fragment.ToyModelToRecycle;

public class ToyActivity extends Activity {
    ImageView image;
    TextView name, description;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toy_activity);
        Bundle bundle = getIntent().getExtras();
        ToyModelToRecycle toy = bundle.getParcelable("toy");
        image=findViewById(R.id.toy_activity_image);
        name=findViewById(R.id.toy_activity_name);
        description=findViewById(R.id.toy_activity_description);
        name.setText(toy.getName());
        description.setText(toy.getInfo());
        image.setImageResource(R.drawable.child_button_picture);
    }
}
