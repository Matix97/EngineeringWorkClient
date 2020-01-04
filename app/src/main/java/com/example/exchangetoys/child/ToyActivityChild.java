package com.example.exchangetoys.child;

import android.app.Activity;
import android.app.AlertDialog;
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
import com.example.exchangetoys.R;
import com.example.exchangetoys.Services.ServiceGenerator;
import com.example.exchangetoys.Services.ToyService;
import com.example.exchangetoys.ui.fragment.ImageArrayAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ToyActivityChild extends Activity {
    private RecyclerView images;
    private TextView name, description;
    private Button iWantThisToy;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toy_activity_child);
        Bundle bundle = getIntent().getExtras();
        Toy toy = bundle.getParcelable("toy");
        //image=findViewById(R.id.toy_activity_image);
        name=findViewById(R.id.toy_activity_name);
        description=findViewById(R.id.toy_activity_description);
        name.setText(toy.getToy_name());
        description.setText(toy.getToy_description());
        iWantThisToy=findViewById(R.id.iWantThisToy);
        iWantThisToy.setOnClickListener(v->{
            iWantThisToy(toy.getToy_id());
        });

        if (toy.getToy_photos() != null && toy.getToy_photos() != "") {
            String[] urls = toy.getToy_photos().split(";");
            ArrayList<String> list =new ArrayList<>();
            for (String q:urls) list.add(q);
            ImageArrayAdapter imageArrayAdapter = new ImageArrayAdapter(list);
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
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
        int width = displayMetrics.widthPixels;
        ViewGroup.LayoutParams layoutParams= images.getLayoutParams();
        layoutParams.height=height/2-200;
        images.setLayoutParams(layoutParams);
    }

    private void iWantThisToy(Long toyId) {
        ToyService toyService = ServiceGenerator.createAuthorizedService(ToyService.class);
        Call<Void> call = toyService.iWantAToy(toyId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    new AlertDialog.Builder(ToyActivityChild.this)
                            .setTitle("Success")
                            .setMessage("Your parent will receive info about this toy")
                            .setNegativeButton(android.R.string.ok, null)
                            .show();
                } else
                    new AlertDialog.Builder(ToyActivityChild.this)
                            .setTitle("Something wrong")
                            .setMessage("Try again ")
                            .setNegativeButton(android.R.string.ok, null)
                            .show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                new AlertDialog.Builder(ToyActivityChild.this)
                        .setTitle("Failure")
                        .setMessage("Check internet connection or restart application")
                        .setNegativeButton(android.R.string.ok, null)
                        .show();
            }
        });

    }
}
