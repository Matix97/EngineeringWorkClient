package com.example.exchangetoys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.exchangetoys.Services.ForegroundService;
import com.example.exchangetoys.child.ChildLoginActivity;

public class StartActivity extends Activity {
    ImageButton parentActivity, childActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);
        parentActivity = findViewById(R.id.parent_button);
        childActivity = findViewById(R.id.child_button);
        childActivity.setImageResource(R.drawable.child_button_picture);
        parentActivity.setImageResource(R.drawable.parents_button_picture);
        Intent serviceIntent = new Intent(this, ForegroundService.class);
        serviceIntent.putExtra("inputExtra", "Foreground Service Example in Android");
        ContextCompat.startForegroundService(this, serviceIntent);
        parentActivity.setOnClickListener(v -> {

            Intent intent = new Intent(this, ParentLoginActivity.class);
            startActivity(intent);
        });
        childActivity.setOnClickListener(v -> {
//            Intent serviceIntent = new Intent(this, ForegroundService.class);
//            stopService(serviceIntent);
            Intent intent = new Intent(this, ChildLoginActivity.class);
            startActivity(intent);
        });
    }
}
