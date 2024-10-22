package com.example.exchangetoys;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.exchangetoys.DTOs.ToyServiceData.JwtResponse;
import com.example.exchangetoys.Services.ServiceGenerator;
import com.example.exchangetoys.Services.UserService;
import com.example.exchangetoys.Tools.MediaManagerInitializer;
import com.example.exchangetoys.notification.ForegroundService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ParentLoginActivity extends Activity {

    EditText loginName, password;
    Button loginButton, registerButton;
    private UserService userService;
//Timer t;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        MediaManagerInitializer.getInstance(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parent_login_activity);
        loginButton = findViewById(R.id.child_login_button);
        registerButton = findViewById(R.id.register_parent_button);
        loginName = findViewById(R.id.login_name_child);
        password = findViewById(R.id.login_password_child);
        this.userService = ServiceGenerator.createService(UserService.class);
        try{
            Bundle bundle = getIntent().getExtras();
            loginName.setText(bundle.getString("login"));
            password.setText(bundle.getString("pass"));
        }catch (Exception e){

        }

        loginButton.setOnClickListener(v -> {

            String messageToEncrypt = loginName.getText().toString() + ";" + password.getText().toString() + ";" + "adult";
            try {

//                //to test
//                ServiceGenerator.role="adult";
//                Intent intent = new Intent(ParentLoginActivity.this, ParentMainActivity.class);
//                startActivity(intent);
                this.userService = ServiceGenerator.createService(UserService.class);
                ServiceGenerator.bearerToken=null;
                Call<JwtResponse> call = userService.login(messageToEncrypt);

                call.enqueue(new Callback<JwtResponse>() {
                    @Override
                    public void onResponse(Call<JwtResponse> call, Response<JwtResponse> response) {
                        if (response.isSuccessful()) {
                            ServiceGenerator.role="adult";
                            ServiceGenerator.bearerToken = response.body().getJwttoken();
//                            Intent i= new Intent(ParentLoginActivity.this, NotificationService.class);
//                            i.putExtra("token",ServiceGenerator.bearerToken);
//                          //  ParentLoginActivity.this.startService(i);
//                            ContextCompat.startForegroundService(ParentLoginActivity.this, i);
                            Intent serviceIntent = new Intent(ParentLoginActivity.this, ForegroundService.class);
                            serviceIntent.putExtra("token",ServiceGenerator.bearerToken);
                            ContextCompat.startForegroundService(ParentLoginActivity.this, serviceIntent);
                            Toast.makeText(ParentLoginActivity.this, "Login succeeded", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ParentLoginActivity.this, ParentMainActivity.class);
                            intent.putExtra("token",ServiceGenerator.bearerToken);
                            startActivity(intent);

                        } else
                            new AlertDialog.Builder(ParentLoginActivity.this)
                                    .setTitle("Bad Login")
                                    .setMessage("Are You sure about Your login and password?")
                                    .setNegativeButton(android.R.string.ok, null)
                                    .show();
                    }

                    @Override
                    public void onFailure(Call<JwtResponse> call, Throwable t) {
                        Toast.makeText(ParentLoginActivity.this, "error", Toast.LENGTH_SHORT).show();


                    }
                });
            } catch (Exception e) {
                Toast.makeText(ParentLoginActivity.this, "not correct....", Toast.LENGTH_SHORT).show();
            }


        });
        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(ParentLoginActivity.this, ParentRegisterActivity.class);
            startActivity(intent);
        });

    }
}
