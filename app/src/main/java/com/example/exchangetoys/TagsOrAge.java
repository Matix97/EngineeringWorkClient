package com.example.exchangetoys;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exchangetoys.ui.fragment.StringArrayAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class TagsOrAge {
    private Button accept;
    private RecyclerView recycle;
    private View view;
    PopupWindow popupWindow;
    public void showPopupWindow(final View view, String data, String option) {

        this.view = view;
        //Create a View object yourself through inflater
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.choose_tags_or_age, null);

      
        //Specify the length and width through constants
        int width = view.getWidth() - 150;
        int height = view.getHeight() - 300;

        //Make Inactive Items Outside Of PopupWindow
        boolean focusable = true;

        //Create a window with our parameters
        popupWindow = new PopupWindow(popupView, width/*900*/, height/*1300*/, focusable);

        //Set the location of the window on the screen
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 70);


     

        //Initialize the elements of our window, install the handler
        recycle=popupView.findViewById(R.id.recycle);
        String[] mTestArray;
        if(option.equals("age")){
            mTestArray = view.getResources().getStringArray(R.array.age_category);
        }else//tag
            mTestArray= view.getResources().getStringArray(R.array.tag_category);
        ArrayList<String> list=new ArrayList<>(Arrays.asList(mTestArray));
        StringArrayAdapter imageArrayAdapter=new StringArrayAdapter(list);
        recycle.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recycle.setItemAnimator(new DefaultItemAnimator());
        recycle.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));
        recycle.setAdapter(imageArrayAdapter);

        accept = popupView.findViewById(R.id.accept);
        accept.setOnClickListener(v -> {
            // TODO: 08/01/2020 confirm
        });


    }
}
