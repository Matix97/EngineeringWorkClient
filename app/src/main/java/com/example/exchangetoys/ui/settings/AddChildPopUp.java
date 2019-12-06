package com.example.exchangetoys.ui.settings;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.example.exchangetoys.R;

public class AddChildPopUp {

    //PopupWindow display method

    public void showPopupWindow(final View view) {


        //Create a View object yourself through inflater
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.add_child, null);


        //Specify the length and width through constants
        int width = view.getWidth() - 150;
        int height = view.getHeight() - 500;

        //Make Inactive Items Outside Of PopupWindow
        boolean focusable = true;

        //Create a window with our parameters
        final PopupWindow popupWindow = new PopupWindow(popupView, width/*900*/, height/*1300*/, focusable);

        //Set the location of the window on the screen
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 100);

        //Initialize the elements of our window, install the handler


        //Handler for clicking on the inactive zone of the window jak odkometnuję to trochę upierdliwe, ale w sumie ciekaawe

//        popupView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                //Close the window when clicked
//                popupWindow.dismiss();
//                return true;
//            }
//        });
    }

}