package com.example.exchangetoys.child;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exchangetoys.DTOs.ToyServiceData.Toy;
import com.example.exchangetoys.R;
import com.example.exchangetoys.Services.ServiceGenerator;
import com.example.exchangetoys.Services.UserService;
import com.example.exchangetoys.child.suggest.ToyArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SuggestedToys extends Activity {

    private RecyclerView toys;
    private ArrayList<Toy> download = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suggested_toy_child);
        toys = findViewById(R.id.my_toys);
        downloadToys();

    }

    public  void downloadToys(){
        UserService toyService = ServiceGenerator.createAuthorizedService(UserService.class);
        Call<List<Toy>> call = toyService.getChildSuggestion();
        call.enqueue(new Callback<List<Toy>>() {
            @Override
            public void onResponse(Call<List<Toy>> call, Response<List<Toy>> response) {
                if (response.isSuccessful()) {
                    download.clear();
                    for (Toy t:response.body())
                        download.add(t);

                        ToyArrayAdapter itemArrayAdapter = new ToyArrayAdapter(R.layout.toy_item_delete, download);
                        toys.setLayoutManager(new LinearLayoutManager(SuggestedToys.this));
                        toys.setItemAnimator(new DefaultItemAnimator());
                        toys.addItemDecoration(new DividerItemDecoration(SuggestedToys.this, DividerItemDecoration.VERTICAL));
                        toys.setAdapter(itemArrayAdapter);



                } else
                    Toast.makeText(SuggestedToys.this, "Error in GET toys \nToken"+ServiceGenerator.bearerToken, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Toy>> call, Throwable t) {
                Toast.makeText(SuggestedToys.this, "FAILURE Error in GET toys ", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
