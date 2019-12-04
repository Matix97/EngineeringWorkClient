package com.example.exchangetoys;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.exchangetoys.DTOs.BearerToken;
import com.example.exchangetoys.Services.ServiceGenerator;
import com.example.exchangetoys.Services.UserService;
import com.example.exchangetoys.Tools.EncryptionTools;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ParentLoginActivity extends Activity {

    EditText loginName, password;
    Button loginButton,registerButton;
    private UserService userService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parent_login_activity);
        loginButton=findViewById(R.id.child_login_button);
        registerButton=findViewById(R.id.register_parent_button);
        loginName=findViewById(R.id.login_name_child);
        password=findViewById(R.id.login_password_child);
        this.userService = ServiceGenerator.createService(UserService.class);
        loginButton.setOnClickListener(v->{
            //if(loginName.getText().toString().equals("admin") && password.getText().toString().equals("admin"))//na sztywno logowane
          String messageToEncrypt=loginName.getText().toString()+";"+password.getText().toString()+";"+"adult";
                try{
                Call<BearerToken> call = userService.login(EncryptionTools.encrypt(messageToEncrypt));
                call.enqueue(new Callback<BearerToken>() {
                    @Override
                    public void onResponse(Call<BearerToken> call, Response<BearerToken> response) {
                        if (response.isSuccessful()) {
                            ServiceGenerator.bearerToken = response.body().getString();
                            Toast.makeText(ParentLoginActivity.this, "Login succeeded", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ParentLoginActivity.this, ParentMainActivity.class);
                            intent.putExtra("name", loginName.getText().toString());             //opcjonalnie jakieś wartości
                            startActivity(intent);

                        } else
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

                    @Override
                    public void onFailure(Call<BearerToken> call, Throwable t) {
                        Toast.makeText(ParentLoginActivity.this, "error", Toast.LENGTH_SHORT).show();
                    }
                });}catch (Exception e)
                {
                    Toast.makeText(ParentLoginActivity.this, "no i pupa", Toast.LENGTH_SHORT).show();
                }




        });

    }
}
