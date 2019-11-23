package com.example.exchangetoys.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exchangetoys.MainActivity;
import com.example.exchangetoys.R;
import com.example.exchangetoys.ui.fragment.ChildArrayAdapter;
import com.example.exchangetoys.ui.fragment.ChildModelToRecycle;

import java.util.ArrayList;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    RecyclerView recyclerView;
    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        notificationsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText("Welcome " + MainActivity.loginName);
            }
        });
        // Initializing list view with the custom adapter
        ArrayList<ChildModelToRecycle> itemList = new ArrayList<>();

        ChildArrayAdapter itemArrayAdapter = new ChildArrayAdapter(R.layout.child_item, itemList);
        recyclerView = (RecyclerView)root.findViewById(R.id.my_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(itemArrayAdapter);

        // Populating list items
        for(int i=0; i<100; i++) {
            itemList.add(new ChildModelToRecycle("CHILD " + i));
        }

        //////////
        return root;
    }
}