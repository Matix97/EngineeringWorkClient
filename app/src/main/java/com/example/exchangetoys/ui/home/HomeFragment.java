package com.example.exchangetoys.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exchangetoys.AddToyActivity;
import com.example.exchangetoys.DTOs.ToyServiceData.Toy;
import com.example.exchangetoys.R;
import com.example.exchangetoys.RentalToyActivity;
import com.example.exchangetoys.Services.ServiceGenerator;
import com.example.exchangetoys.Services.ToyService;
import com.example.exchangetoys.ui.fragment.ToyArrayAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private RecyclerView my_toys, rented_toys;
    private FloatingActionButton addToy,rentToy;
    private View root;
    private ArrayList<Toy> myToysData, rentedToysData;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_home, container, false);

        my_toys = root.findViewById(R.id.my_recycle_view_my_toy);
        rented_toys = root.findViewById(R.id.my_recycle_view_rented_toy);
        rentToy = root.findViewById(R.id.rent_toy);
        rentToy.setOnClickListener(v->{
            Intent intent = new Intent(root.getContext(), RentalToyActivity.class);
            startActivity(intent);
        });
        addToy = root.findViewById(R.id.add_toy);
        addToy.setOnClickListener(v -> {
            Intent intent = new Intent(root.getContext(), AddToyActivity.class);
            startActivity(intent);
        });
        // my_toys.getLayoutParams().height=root.getHeight()/2-50;
        //rented_toys.getLayoutParams().height=root.getHeight()/2-50;
        ///////////////////////
        // Initializing list view with the custom adapter
        myToysData = new ArrayList<>();
        rentedToysData = new ArrayList<>();

        ToyArrayAdapter itemArrayAdapter = new ToyArrayAdapter(R.layout.toy_item, rentedToysData);
        my_toys = root.findViewById(R.id.my_recycle_view_rented_toy);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        my_toys.setLayoutManager(linearLayoutManager);
        my_toys.setItemAnimator(new DefaultItemAnimator());
        my_toys.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));
        my_toys.setAdapter(itemArrayAdapter);

        TorDeleteAdvert itemArrayAdapter2 = new TorDeleteAdvert(R.layout.toy_delete_advert, myToysData);
        rented_toys = root.findViewById(R.id.my_recycle_view_my_toy);
        rented_toys.setLayoutManager(new LinearLayoutManager(root.getContext()));
        rented_toys.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        rented_toys.setItemAnimator(new DefaultItemAnimator());
        rented_toys.setAdapter(itemArrayAdapter2);
        download("my",itemArrayAdapter);
        download("rented",itemArrayAdapter2);

        //////////


        return root;
    }
    private void download(String which, TorDeleteAdvert toyArrayAdapter) {//"my" or "rented"
        ToyService toyService = ServiceGenerator.createAuthorizedService(ToyService.class);
        Call<List<Toy>> call = null;
        if (which.equals("my"))
            call = toyService.getYourToysAdvert();
        else if (which.equals("rented"))
            call = toyService.getYourRentedToys();
        else
            return;

        call.enqueue(new Callback<List<Toy>>() {
            @Override
            public void onResponse(Call<List<Toy>> call, Response<List<Toy>> response) {
                if (response.isSuccessful()) {//todo create list
                    if (which.equals("my")) {
                        myToysData.clear();
                        for (Toy t : response.body()) {
                            myToysData.add(t);
                        }
                        my_toys.setAdapter(toyArrayAdapter);
                    } else if (which.equals("rented")) {
                        rentedToysData.clear();
                        for (Toy t : response.body()) {
                            rentedToysData.add(t);
                        }
                        rented_toys.setAdapter(toyArrayAdapter);
                    }


                } else {
                    Toast.makeText(root.getContext(), "Error in GET toy " + which, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Toy>> call, Throwable t) {
                Toast.makeText(root.getContext(), "FAILURE Error in GET toy " + which, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void download(String which, ToyArrayAdapter toyArrayAdapter) {//"my" or "rented"
        ToyService toyService = ServiceGenerator.createAuthorizedService(ToyService.class);
        Call<List<Toy>> call = null;
        if (which.equals("my"))
            call = toyService.getYourToysAdvert();
        else if (which.equals("rented"))
            call = toyService.getYourRentedToys();
        else
            return;

        call.enqueue(new Callback<List<Toy>>() {
            @Override
            public void onResponse(Call<List<Toy>> call, Response<List<Toy>> response) {
                if (response.isSuccessful()) {//todo create list
                    if (which.equals("my")) {
                        myToysData.clear();
                        for (Toy t : response.body()) {
                            myToysData.add(t);
                        }
                        my_toys.setAdapter(toyArrayAdapter);
                    } else if (which.equals("rented")) {
                        rentedToysData.clear();
                        for (Toy t : response.body()) {
                            rentedToysData.add(t);
                        }
                        rented_toys.setAdapter(toyArrayAdapter);
                    }


                } else {
                    Toast.makeText(root.getContext(), "Error in GET toy " + which, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Toy>> call, Throwable t) {
                Toast.makeText(root.getContext(), "FAILURE Error in GET toy " + which, Toast.LENGTH_SHORT).show();
            }
        });
    }
}