package com.example.exchangetoys.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exchangetoys.R;
import com.example.exchangetoys.ui.fragment.ToyArrayAdapter;
import com.example.exchangetoys.ui.fragment.ToyModelToRecycle;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private RecyclerView my_toys, rented_toys;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        my_toys = root.findViewById(R.id.my_recycle_view_my_toy);
        rented_toys = root.findViewById(R.id.my_recycle_view_rented_toy);
        // my_toys.getLayoutParams().height=root.getHeight()/2-50;
        //rented_toys.getLayoutParams().height=root.getHeight()/2-50;
        ///////////////////////
        // Initializing list view with the custom adapter
        ArrayList<ToyModelToRecycle> itemList = new ArrayList<>();
        // Populating list items
        for (int i = 0; i < 100; i++) {
            itemList.add(new ToyModelToRecycle("Toy " + i, "opis", R.drawable.child_button_picture));
        }
        ToyArrayAdapter itemArrayAdapter = new ToyArrayAdapter(R.layout.toy_item, itemList);
        my_toys = root.findViewById(R.id.my_recycle_view_rented_toy);
        my_toys.setLayoutManager(new LinearLayoutManager(root.getContext()));
        my_toys.setItemAnimator(new DefaultItemAnimator());
        my_toys.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        my_toys.setAdapter(itemArrayAdapter);

        ToyArrayAdapter itemArrayAdapter2 = new ToyArrayAdapter(R.layout.toy_item, itemList);
        rented_toys = root.findViewById(R.id.my_recycle_view_my_toy);
        rented_toys.setLayoutManager(new LinearLayoutManager(root.getContext()));
        rented_toys.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        rented_toys.setItemAnimator(new DefaultItemAnimator());
        rented_toys.setAdapter(itemArrayAdapter2);


        //////////


        return root;
    }
}