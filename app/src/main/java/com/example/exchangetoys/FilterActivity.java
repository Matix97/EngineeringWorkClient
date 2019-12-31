package com.example.exchangetoys;

import android.location.Location;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Spinner;

import com.example.exchangetoys.DTOs.ToyServiceData.FilterDTO;
import com.example.exchangetoys.ui.dashboard.DashboardFragment;

public class FilterActivity {
    private View view;


    private Spinner mainCategory, age, tags;
    private EditText anyKeyword;
    private CheckBox isDidactic, isVintage;
    private Button confirm;

    public void showPopupWindow(final View v,Location location, String whoCallMeXD) {
        this.view = v;
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
        mainCategory = popupView.findViewById(R.id.category_spinner);
        age = popupView.findViewById(R.id.age_spinner);
        tags = popupView.findViewById(R.id.tag_spinner);
        anyKeyword = popupView.findViewById(R.id.any_keyword);
        isDidactic = popupView.findViewById(R.id.is_didactic);
        isVintage = popupView.findViewById(R.id.is_vintage);
        confirm = popupView.findViewById(R.id.confirm_filtr);
        confirm.setOnClickListener(s -> {

            FilterDTO filterDTO = new FilterDTO(mainCategory.getSelectedItem().toString(),
                    age.getSelectedItem().toString(), tags.getSelectedItem().toString(),
                    anyKeyword.getText().toString(), isDidactic.isChecked(), isVintage.isChecked(),
                    location.getLatitude(), location.getLongitude());
            if(!whoCallMeXD.equals("child"))
                DashboardFragment.downloadToys(filterDTO,view);
            popupWindow.dismiss();
          //  ParentMainActivity.filtr(view);

        });


    }



}
