package com.example.exchangetoys;

import android.app.Activity;
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
import com.example.exchangetoys.ui.fragment.ToyArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChildSettings  extends Activity {


    private TextView childName, childRadius,childTags, childAgeRange;
    private Button confirm;
    private SeekBar seekRadiusBar;
    private RecyclerView suggestedToy;
    private int seekBarValue;
    private ArrayList<Toy> toysData;
    private Child child;
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
        childTags=findViewById(R.id.chosen_tags);
        childAgeRange=findViewById(R.id.chosen_age_range);
        confirm=findViewById(R.id.save_child_settings);
        seekRadiusBar=findViewById(R.id.seekBar);
        suggestedToy=findViewById(R.id.suggested_toy_view);
        childName.setText(child.getChild_name());
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
        childTags.setText(child.getAvailableTag());
        childAgeRange.setText(child.getAvailableAge());
//        SpannableString spannableString = new SpannableString(childTags.getText().toString());
//        ClickableSpan clickableSpan1 = new ClickableSpan() {
//            @Override
//            public void onClick(View widget) {
//                setTagsAction();
//            }
//        };
//        spannableString.setSpan(clickableSpan1, 1,childTags.getText().toString().length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        childAgeRange.setOnClickListener(v -> setAgeAction());
        childTags.setOnClickListener(v -> setTagsAction());

        downloadSuggestion(child);

    }

    private void setTagsAction() {
        TagsOrAge tagsOrAge=new TagsOrAge();
        tagsOrAge.showPopupWindow(confirm.getRootView(),childTags.getText().toString(),"");
    }

    private void setAgeAction() {

           }

    private void sandSaveRequest() {
        UserService userService = ServiceGenerator.createAuthorizedService(UserService.class);
        ChildUpdateDTO childUpdateDTO = new ChildUpdateDTO();
        childUpdateDTO.setChild_login(child.getChild_login());
        childUpdateDTO.setAvailableAge(childAgeRange.getText().toString());
        childUpdateDTO.setAvailableTag(childTags.getText().toString());
        childUpdateDTO.setChild_radius_area(seekBarValue);
        childUpdateDTO.setAmount(10);// TODO: 25/01/2020 zró jakiś wybór
        Call<Child> call = userService.updateChild(childUpdateDTO);
        call.enqueue(new Callback<Child>() {
            @Override
            public void onResponse(Call<Child> call, Response<Child> response) {

            }

            @Override
            public void onFailure(Call<Child> call, Throwable t) {

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
