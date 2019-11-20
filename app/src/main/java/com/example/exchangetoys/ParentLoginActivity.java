package com.example.exchangetoys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.example.exchangetoys.ui.ChildLoginActivity;

public class ParentLoginActivity extends Activity {

    EditText loginName, password;
    Button loginButton,registerButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parent_login_activity);
        loginButton=findViewById(R.id.paretn_login_button);
        registerButton=findViewById(R.id.register_parent_button);
        loginName=findViewById(R.id.login_name_parent);
        password=findViewById(R.id.login_password_parent);
        loginName.setOnClickListener(v->{
            if(loginName.getText().equals("admin") && password.getText().equals("admin"))//na sztywno logowane
            {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
            else{

            }
        });

    }
}
