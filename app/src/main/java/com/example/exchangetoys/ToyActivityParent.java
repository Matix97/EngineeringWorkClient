package com.example.exchangetoys;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exchangetoys.DTOs.ToyServiceData.Toy;
import com.example.exchangetoys.ui.fragment.ImageArrayAdapter;

import java.util.ArrayList;

public class ToyActivityParent extends Activity {
    private RecyclerView images;
    private TextView name, description;
private Button contactByEmail,contactByPhone;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toy_activity_parent);
        Bundle bundle = getIntent().getExtras();
        Toy toy = bundle.getParcelable("toy");
        //image=findViewById(R.id.toy_activity_image);
        name = findViewById(R.id.toy_activity_name);
        description = findViewById(R.id.toy_activity_description);
        name.setText(toy.getToy_name());
        description.setText(toy.getToy_description());

        if (toy.getToy_photos() != null && toy.getToy_photos() != "") {
            String[] urls = toy.getToy_photos().split(";");
            ArrayList<String> list = new ArrayList<>();
            for (String q : urls) list.add(q);
            ImageArrayAdapter imageArrayAdapter = new ImageArrayAdapter(list);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            images = findViewById(R.id.toy_activity_image);
            images.setLayoutManager(linearLayoutManager);
            images.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
            images.setItemAnimator(new DefaultItemAnimator());
            images.setAdapter(imageArrayAdapter);
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;

        ViewGroup.LayoutParams layoutParams = images.getLayoutParams();
        layoutParams.height = height / 2 - 200;
        images.setLayoutParams(layoutParams);


    }
}
