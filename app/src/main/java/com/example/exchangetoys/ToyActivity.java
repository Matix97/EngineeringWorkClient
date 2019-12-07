package com.example.exchangetoys;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

public class ToyActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toy_activity);
    }
}
