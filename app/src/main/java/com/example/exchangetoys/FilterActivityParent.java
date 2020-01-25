package com.example.exchangetoys;

import android.location.Location;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.exchangetoys.DTOs.ToyServiceData.FilterDTO;
import com.example.exchangetoys.child.ChildMainActivity;
import com.example.exchangetoys.ui.dashboard.DashboardFragment;

public class FilterActivityParent {
    private View view;


    private Spinner mainCategory, age, tags;
    private EditText anyKeyword;
    private CheckBox isDidactic, isVintage;
    private Button confirm;
    private CheckBox checkBoxAnyKeyword, checkBoxCategorySpinner, checkBoxAgeSpinner,checkBoxTagSpinner,checkBoxDidactic,checkBoxVintage,checkBoxRadius;
    private TextView radiusInfo;
    private SeekBar seekBarRadius;
    private Integer seekBarValue;


    public void showPopupWindow(final View v,Location location, String whoCallMeXD) {
        this.view = v;
        //Create a View object yourself through inflater
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.filtr_activity_parent, null);
        //Specify the length and width through constants
        int width = view.getWidth() - 150;
        int height = view.getHeight() - 300;
        //Make Inactive Items Outside Of PopupWindow
        boolean focusable = true;
        //Create a window with our parameters
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        //Set the location of the window on the screen
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 90);
        //todo find view by id for all components
        mainCategory = popupView.findViewById(R.id.category_spinner); mainCategory.setEnabled(false);
        age = popupView.findViewById(R.id.age_spinner); age.setEnabled(false);
        tags = popupView.findViewById(R.id.tag_spinner); tags.setEnabled(false);
        anyKeyword = popupView.findViewById(R.id.any_keyword); anyKeyword.setEnabled(false);
        isDidactic = popupView.findViewById(R.id.is_didactic); isDidactic.setEnabled(false);
        isVintage = popupView.findViewById(R.id.is_vintage);  isVintage.setEnabled(false);
        radiusInfo = popupView.findViewById(R.id.radius_info); radiusInfo.setEnabled(false);
        seekBarRadius = popupView.findViewById(R.id.seekBarRadius); seekBarRadius.setEnabled(false);
        confirm = popupView.findViewById(R.id.confirm_filtr);

        checkBoxAnyKeyword = popupView.findViewById(R.id.checkBoxAnyKeyword);
        checkBoxCategorySpinner = popupView.findViewById(R.id.checkBoxCategorySpinner);
        checkBoxAgeSpinner = popupView.findViewById(R.id.checkBoxAgeSpinner);
        checkBoxTagSpinner = popupView.findViewById(R.id.checkBoxTagSpinner);
        checkBoxDidactic = popupView.findViewById(R.id.checkBoxDidactic);
        checkBoxVintage = popupView.findViewById(R.id.checkBoxVintage);
        checkBoxRadius = popupView.findViewById(R.id.checkBoxRadius);

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
        checkBoxRadius.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked) {
                seekBarRadius.setEnabled(true);
                radiusInfo.setEnabled(true);
            }
            else {
                seekBarRadius.setEnabled(false);
                radiusInfo.setEnabled(false);
            }
        });
        seekBarRadius.setProgress(30);
        radiusInfo.setText("Chosen radius: " + 30 + " km");
        seekBarValue=30;
        seekBarRadius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                seekBarValue = progress;
                radiusInfo.setText("Chosen radius: " + progress + " km");
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        confirm.setOnClickListener(s -> {

            FilterDTO filterDTO = new FilterDTO();
            if(checkBoxAnyKeyword.isChecked()) filterDTO.setAnyKeyword(anyKeyword.getText().toString());
            if(checkBoxCategorySpinner.isChecked())filterDTO.setMainCategory(mainCategory.getSelectedItem().toString());
            if(checkBoxAgeSpinner.isChecked()) filterDTO.setAge(age.getSelectedItem().toString());
            if(checkBoxTagSpinner.isChecked()) filterDTO.setTags(tags.getSelectedItem().toString());
            if(checkBoxDidactic.isChecked())filterDTO.setIsDidactic(isDidactic.isChecked());
            if(checkBoxVintage.isChecked())filterDTO.setIsVintage(isVintage.isChecked());
            if(location!=null){
                filterDTO.setLatitude(location.getLatitude());
                filterDTO.setLongitude(location.getLongitude());
            }
            else{
                // TODO: 03/01/2020 LOCATION
                filterDTO.setLongitude(19.36222803);
                filterDTO.setLatitude(51.8746158);
            }

            filterDTO.setRadius(null);

            if(!whoCallMeXD.equals("child")){
                if(checkBoxRadius.isChecked())filterDTO.setRadius( seekBarValue);
                DashboardFragment.downloadToys(filterDTO,view);
            }

            if(whoCallMeXD.equals("child"))
                ChildMainActivity.downloadToys2(filterDTO,view,location);
            popupWindow.dismiss();


        });


    }



}
