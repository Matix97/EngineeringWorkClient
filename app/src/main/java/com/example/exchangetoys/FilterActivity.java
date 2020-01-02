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
import com.example.exchangetoys.child.ChildMainActivity;
import com.example.exchangetoys.ui.dashboard.DashboardFragment;

public class FilterActivity {
    private View view;


    private Spinner mainCategory, age, tags;
    private EditText anyKeyword;
    private CheckBox isDidactic, isVintage;
    private Button confirm;
    private CheckBox checkBoxAnyKeyword, checkBoxCategorySpinner, checkBoxAgeSpinner,checkBoxTagSpinner,checkBoxDidactic,checkBoxVintage;


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
        mainCategory = popupView.findViewById(R.id.category_spinner); mainCategory.setEnabled(false);
        age = popupView.findViewById(R.id.age_spinner); age.setEnabled(false);
        tags = popupView.findViewById(R.id.tag_spinner); tags.setEnabled(false);
        anyKeyword = popupView.findViewById(R.id.any_keyword); anyKeyword.setEnabled(false);
        isDidactic = popupView.findViewById(R.id.is_didactic); isDidactic.setEnabled(false);
        isVintage = popupView.findViewById(R.id.is_vintage);  isVintage.setEnabled(false);
        confirm = popupView.findViewById(R.id.confirm_filtr);

        checkBoxAnyKeyword = popupView.findViewById(R.id.checkBoxAnyKeyword);
        checkBoxCategorySpinner = popupView.findViewById(R.id.checkBoxCategorySpinner);
        checkBoxAgeSpinner = popupView.findViewById(R.id.checkBoxAgeSpinner);
        checkBoxTagSpinner = popupView.findViewById(R.id.checkBoxTagSpinner);
        checkBoxDidactic = popupView.findViewById(R.id.checkBoxDidactic);
        checkBoxVintage = popupView.findViewById(R.id.checkBoxVintage);

        checkBoxAnyKeyword.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked) anyKeyword.setEnabled(true);
            else anyKeyword.setEnabled(false);
        });
        checkBoxCategorySpinner.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked) mainCategory.setEnabled(true);
            else mainCategory.setEnabled(false);
        });
        checkBoxAgeSpinner.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked) age.setEnabled(true);
            else age.setEnabled(false);
        });
        checkBoxTagSpinner.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked) tags.setEnabled(true);
            else tags.setEnabled(false);
        });
        checkBoxDidactic.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked) isDidactic.setEnabled(true);
            else isDidactic.setEnabled(false);
        });
        checkBoxVintage.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked) isVintage.setEnabled(true);
            else isVintage.setEnabled(false);
        });

        confirm.setOnClickListener(s -> {

            FilterDTO filterDTO = new FilterDTO();
            if(checkBoxAnyKeyword.isChecked()) filterDTO.setAnyKeyword(anyKeyword.getText().toString());
            if(checkBoxCategorySpinner.isChecked())filterDTO.setMainCategory(mainCategory.getSelectedItem().toString());
            if(checkBoxAgeSpinner.isChecked()) filterDTO.setAge(age.getSelectedItem().toString());
            if(checkBoxTagSpinner.isChecked()) filterDTO.setTags(tags.getSelectedItem().toString());
            if(checkBoxDidactic.isChecked())filterDTO.setIsDidactic(isDidactic.isChecked());
            if(checkBoxVintage.isChecked())filterDTO.setIsVintage(isVintage.isChecked());
            filterDTO.setLatitude(location.getLatitude());
            filterDTO.setLongitude(location.getLongitude());

            if(!whoCallMeXD.equals("child"))
                DashboardFragment.downloadToys(filterDTO,view);
            if(whoCallMeXD.equals("child"))
                ChildMainActivity.downloadToys2(filterDTO,view);
            popupWindow.dismiss();


        });


    }



}
