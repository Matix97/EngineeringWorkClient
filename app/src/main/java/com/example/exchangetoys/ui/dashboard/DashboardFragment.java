package com.example.exchangetoys.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exchangetoys.FilterActivity;
import com.example.exchangetoys.R;
import com.example.exchangetoys.ui.fragment.ToyArrayAdapter;
import com.example.exchangetoys.ui.fragment.ToyModelToRecycle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private RecyclerView toys;
    private FloatingActionButton filterButton;
    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_main_list_of_toys, container, false);

        toys=root.findViewById(R.id.my_toys);
        filterButton = root.findViewById(R.id.filter_button);
        filterButton.setOnClickListener(v -> {
            FilterActivity filterActivity = new FilterActivity();
            filterActivity.showPopupWindow(root);
        });
        // my_toys.getLayoutParams().height=root.getHeight()/2-50;
        //rented_toys.getLayoutParams().height=root.getHeight()/2-50;
        ///////////////////////
        // Initializing list view with the custom adapter
        ArrayList<ToyModelToRecycle> itemList = new ArrayList<>();
        // Populating list items
        for(int i=0; i<100; i++) {
            itemList.add(new ToyModelToRecycle("Toy " + i,"opis",R.drawable.child_button_picture));
        }
        ToyArrayAdapter itemArrayAdapter = new ToyArrayAdapter(R.layout.toy_item, itemList);

        toys.setLayoutManager(new LinearLayoutManager(root.getContext()));
        toys.setItemAnimator(new DefaultItemAnimator());
        toys.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        toys.setAdapter(itemArrayAdapter);
        return root;
    }
}