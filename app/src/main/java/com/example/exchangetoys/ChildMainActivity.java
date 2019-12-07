package com.example.exchangetoys;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exchangetoys.ui.fragment.ToyArrayAdapter;
import com.example.exchangetoys.ui.fragment.ToyModelToRecycle;

import java.util.ArrayList;

public class ChildMainActivity extends AppCompatActivity {

    private RecyclerView toys;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_main);
        toys = findViewById(R.id.my_toys);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////potem wywalilć
        // Initializing list view with the custom adapter
        ArrayList<ToyModelToRecycle> itemList = new ArrayList<>();
        // Populating list items
        for (int i = 0; i < 100; i++) {
            itemList.add(new ToyModelToRecycle("Toy " + i, "opis", R.drawable.child_button_picture));
        }
        ToyArrayAdapter itemArrayAdapter = new ToyArrayAdapter(R.layout.toy_item, itemList);

        toys.setLayoutManager(new LinearLayoutManager(this));
        toys.setItemAnimator(new DefaultItemAnimator());
        toys.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        toys.setAdapter(itemArrayAdapter);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }
}
