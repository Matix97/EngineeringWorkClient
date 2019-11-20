package com.example.exchangetoys;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
        loginButton.setOnClickListener(v->{
            if(loginName.getText().toString().equals("admin") && password.getText().toString().equals("admin"))//na sztywno logowane
            {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
            else{
                new AlertDialog.Builder(ParentLoginActivity.this)
                        .setTitle("Bad Login")
                        .setMessage("Are You sure about Your login and password?")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.ok, null)
                       // .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }

        });

    }
}
