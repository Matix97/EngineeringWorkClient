package com.example.exchangetoys;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exchangetoys.DTOs.ToyServiceData.SuggestedToy;
import com.example.exchangetoys.DTOs.ToyServiceData.Toy;
import com.example.exchangetoys.DTOs.UserServiceData.Child;
import com.example.exchangetoys.DTOs.UserServiceData.ChildUpdateDTO;
import com.example.exchangetoys.Services.ServiceGenerator;
import com.example.exchangetoys.Services.UserService;
import com.example.exchangetoys.spinner.Item;
import com.example.exchangetoys.spinner.MultiSelectionSpinner;
import com.example.exchangetoys.ui.fragment.ToyArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChildSettings  extends Activity {


    private TextView childName, childRadius;
    private Button confirm;
    private SeekBar seekRadiusBar;
    private RecyclerView suggestedToy;
    private int seekBarValue;
    private ArrayList<Toy> toysData;
    private Child child;
    private TextView suggestionInfo;
    private SeekBar  suggestion;
    private Integer suggestionValue;
    MultiSelectionSpinner mySpinner;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
if(toysData==null)
    toysData=new ArrayList<>();
        setContentView(R.layout.child_settings);
        Bundle bundle = getIntent().getExtras();
     child = bundle.getParcelable("child");
        System.out.println("MY_TAG: Settings: "+child);
        childName=findViewById(R.id.child_name_text_view);
        childRadius=findViewById(R.id.radius_info);


        confirm=findViewById(R.id.save_child_settings);
        seekRadiusBar=findViewById(R.id.seekBar);
        suggestedToy=findViewById(R.id.suggested_toy_view);
        childName.setText(child.getChild_name());
        suggestionInfo = findViewById(R.id.suggestion_info);
        suggestion = findViewById(R.id.seekBarSuggestion);
        suggestionValue=child.getAmountOfSuggesstedToy();
        suggestion.setProgress(suggestionValue);

        suggestionInfo.setText("Amount of toys: "+suggestionValue);
        suggestion.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                suggestionValue=progress;
                suggestionInfo.setText("Amount of toys: "+suggestionValue);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBarValue=child.getChild_radius_area();
        seekRadiusBar.setProgress(seekBarValue);

        childRadius.setText("Chosen radius: " + seekBarValue + " km");
        seekRadiusBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                seekBarValue = progress;
                childRadius.setText("Chosen radius: " + progress + " km");
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {     }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {      }
        });
        confirm.setOnClickListener(v -> sandSaveRequest());

        ArrayList<Item> items = new ArrayList<>();
        for (String s: "0-3;4-7;8-12;13-15;16-100".split(";")) {
            if(child.getAvailableAge().contains(s))
                items.add(Item.builder().name(s).value(true).build());
            else
                items.add(Item.builder().name(s).value(false).build());

        }


        mySpinner =  findViewById(R.id.spn_items);


        ArrayList<Item> selectedItems = new ArrayList<>();
        for (String s:child.getAvailableAge().split(";")  ) {
            selectedItems.add(Item.builder().name(s).value(true).build());
        }
        mySpinner.setItems(items);
        mySpinner.setSelection(selectedItems);


        downloadSuggestion(child);

    }





    private void sandSaveRequest() {
        UserService userService = ServiceGenerator.createAuthorizedService(UserService.class);
        ChildUpdateDTO childUpdateDTO = new ChildUpdateDTO();
        childUpdateDTO.setChild_login(child.getChild_login());
        ArrayList<Item> selectedItems = mySpinner.getSelectedItems();
        StringBuilder stringBuilder =new StringBuilder();
        for (Item i: selectedItems ) {
            stringBuilder.append(i.getName());
            stringBuilder.append(";");
        }
        childUpdateDTO.setAvailableAge(stringBuilder.toString());
        childUpdateDTO.setAvailableTag("soft;funny;scary;groupToy;collector;boys;girls");
        childUpdateDTO.setChild_radius_area(seekBarValue);
        childUpdateDTO.setAmount(suggestionValue);
        Call<Child> call = userService.updateChild(childUpdateDTO);
        call.enqueue(new Callback<Child>() {
            @Override
            public void onResponse(Call<Child> call, Response<Child> response) {
                new AlertDialog.Builder(ChildSettings.this)
                        .setTitle("Success")
                        .setMessage("Settings were changing\n" +response.body().toString())
                        .setNegativeButton(android.R.string.ok, null)
                        .show();
            }

            @Override
            public void onFailure(Call<Child> call, Throwable t) {
                new AlertDialog.Builder(ChildSettings.this)
                        .setTitle("Warning")
                        .setMessage("Something go wrong")
                        .setNegativeButton(android.R.string.ok, null)
                        .show();
            }
        });
    }

    private void downloadSuggestion(Child child) {
        UserService userService = ServiceGenerator.createAuthorizedService(UserService.class);
        Call<List<SuggestedToy>> call = userService.getSuggestion();
        call.enqueue(new Callback<List<SuggestedToy>>() {
            @Override
            public void onResponse(Call<List<SuggestedToy>> call, Response<List<SuggestedToy>> response) {
                if (response.isSuccessful()) {
                    toysData.clear();
                    for (SuggestedToy s: response.body() ) {
                        if(s.getChild().getChild_login().equals(child.getChild_login())){
                            toysData.add(s.getToy());
                        }
                    }
                    ToyArrayAdapter itemArrayAdapter2 = new ToyArrayAdapter(R.layout.toy_item, toysData);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ChildSettings.this);
                    linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    suggestedToy.setLayoutManager(linearLayoutManager);
                    suggestedToy.addItemDecoration(new DividerItemDecoration(ChildSettings.this, DividerItemDecoration.HORIZONTAL));
                    suggestedToy.setItemAnimator(new DefaultItemAnimator());
                    suggestedToy.setAdapter(itemArrayAdapter2);

                } else {

                }
            }

            @Override
            public void onFailure(Call<List<SuggestedToy>> call, Throwable t) {

            }
        });
    }
}
