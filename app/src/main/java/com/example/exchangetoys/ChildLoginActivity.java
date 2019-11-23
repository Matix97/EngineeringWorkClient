package com.example.exchangetoys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

public class ChildLoginActivity extends Activity {
    EditText login,password;
    Button confirm;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.child_login_activity);
        confirm=findViewById(R.id.child_login_button);
        login=findViewById(R.id.login_name_child);
        password=findViewById(R.id.login_password_child);
        confirm.setOnClickListener(v->{
            Intent intent = new Intent(this, ChildMainActivity.class);
            startActivity(intent);
        });


    }
}
