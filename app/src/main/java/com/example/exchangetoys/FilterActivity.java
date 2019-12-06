package com.example.exchangetoys;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.example.exchangetoys.Services.ServiceGenerator;
import com.example.exchangetoys.Services.UserService;

public class FilterActivity {
    private View view;
    private UserService userService;

    public void showPopupWindow(final View v) {
        this.view = v;
        this.userService = ServiceGenerator.createService(UserService.class);
        //Create a View object yourself through inflater
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.filtr_activity, null);
        //Specify the length and width through constants
        int width = view.getWidth() - 150;
        int height = view.getHeight() - 300;
        //Make Inactive Items Outside Of PopupWindow
        boolean focusable = true;
        //Create a window with our parameters
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        //Set the location of the window on the screen
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 70);
        //todo find view by id for all components


    }
}
