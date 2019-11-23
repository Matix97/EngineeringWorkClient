package com.example.exchangetoys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.annotation.Nullable;

public class StartActivity extends Activity {
    ImageButton parentActivity, childActivity;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);
        parentActivity =findViewById(R.id.parent_button);
        childActivity=findViewById(R.id.child_button);
        childActivity.setImageResource(R.drawable.child_button_picture);
        parentActivity.setImageResource(R.drawable.parents_button_picture);
        parentActivity.setOnClickListener(v->{
            Intent intent = new Intent(this, ParentLoginActivity.class);
            //intent.putExtra("Value1", ed1.getText().toString());              /opcjonalnie jakieś wartości
            startActivity(intent);
        });
        childActivity.setOnClickListener(v->{
            Intent intent = new Intent(this, ChildLoginActivity.class);
            startActivity(intent);
        });
    }
}
