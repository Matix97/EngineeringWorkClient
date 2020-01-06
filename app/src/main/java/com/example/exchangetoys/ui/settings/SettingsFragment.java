package com.example.exchangetoys.ui.settings;

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

import com.example.exchangetoys.DTOs.UserServiceData.Child;
import com.example.exchangetoys.R;
import com.example.exchangetoys.Services.ServiceGenerator;
import com.example.exchangetoys.Services.UserService;
import com.example.exchangetoys.ui.fragment.ChildArrayAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsFragment extends Fragment {

    private View root;
    private UserService userService;
    private RecyclerView recyclerView;
    private static ArrayList<Child> childrenList;
    private FloatingActionButton addChildButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_settings, container, false);

        addChildButton = root.findViewById(R.id.floatingActionButton);
        addChildButton.setOnClickListener(v -> addChildHandler(inflater, container));
        // Initializing list view with the custom adapter
        if(childrenList==null)
            childrenList = new ArrayList<>();


        recyclerView = root.findViewById(R.id.my_recycle_view);


        downloadChild();

        return root;
    }

    private void addChildHandler(LayoutInflater inflater, ViewGroup container) {
//        Snackbar.make(root, "Here's a Snackbar", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show();
        AddChildPopUp addChildPopUp = new AddChildPopUp();
        addChildPopUp.showPopupWindow(root);
    }

    private void downloadChild() {

        UserService toyService = ServiceGenerator.createAuthorizedService(UserService.class);
        Call<List<Child>> call = toyService.getChild();
        call.enqueue(new Callback<List<Child>>() {
            @Override
            public void onResponse(Call<List<Child>> call, Response<List<Child>> response) {
                if (response.isSuccessful()) {//todo create list
                    childrenList.clear();
                    for (Child s:response.body()  ) {
                        childrenList.add(s);
                    }
                    ChildArrayAdapter itemArrayAdapter = new ChildArrayAdapter(R.layout.child_item, childrenList);
                    recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
                    recyclerView.setItemAnimator(new DefaultItemAnimator());

                        recyclerView.addItemDecoration(new DividerItemDecoration(root.getContext(), DividerItemDecoration.VERTICAL));
                    recyclerView.setAdapter(itemArrayAdapter);

                } else {
                       Toast.makeText(root.getContext(), "Error in GET child \nToken"+ServiceGenerator.bearerToken, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Child>> call, Throwable t) {
                Toast.makeText(root.getContext(), "FAILURE Error in GET child ", Toast.LENGTH_SHORT).show();
            }
        });


    }
}