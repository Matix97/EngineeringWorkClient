package com.example.exchangetoys.ui.settings;

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
import com.example.exchangetoys.Services.UserService;
import com.example.exchangetoys.ui.fragment.ChildArrayAdapter;
import com.example.exchangetoys.ui.fragment.ChildModelToRecycle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class SettingsFragment extends Fragment {

    private View root;
    private UserService userService;
    private RecyclerView recyclerView;
    private ArrayList<ChildModelToRecycle> childrenList;
    private FloatingActionButton addChildButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_settings, container, false);

        addChildButton = root.findViewById(R.id.floatingActionButton);
        addChildButton.setOnClickListener(v -> addChildHandler(inflater, container));
        // Initializing list view with the custom adapter
        childrenList = new ArrayList<>();

        ChildArrayAdapter itemArrayAdapter = new ChildArrayAdapter(R.layout.child_item, childrenList);
        recyclerView = root.findViewById(R.id.my_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(itemArrayAdapter);

        downloadChild();

        return root;
    }

    private void addChildHandler(LayoutInflater inflater, ViewGroup container) {
        Snackbar.make(root, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        AddChildPopUp addChildPopUp = new AddChildPopUp();
        addChildPopUp.showPopupWindow(root);
    }

    private void downloadChild() {
        // Populating list items
        for (int i = 0; i < 100; i++) {
            childrenList.add(new ChildModelToRecycle("CHILD " + i));
        }
        //todo change to equivalent request

    }
}