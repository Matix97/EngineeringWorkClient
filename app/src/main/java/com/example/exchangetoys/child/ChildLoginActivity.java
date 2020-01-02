package com.example.exchangetoys.child;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.exchangetoys.DTOs.ToyServiceData.JwtResponse;
import com.example.exchangetoys.R;
import com.example.exchangetoys.Services.ServiceGenerator;
import com.example.exchangetoys.Services.UserService;
import com.example.exchangetoys.Tools.EncryptionTools;
import com.example.exchangetoys.Tools.MediaManagerInitializer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChildLoginActivity extends Activity {
    EditText login, password;
    Button confirm;
    private UserService userService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        MediaManagerInitializer.getInstance(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.child_login_activity);
        confirm = findViewById(R.id.child_login_button);
        login = findViewById(R.id.login_name_child);
        password = findViewById(R.id.login_password_child);
        this.userService = ServiceGenerator.createService(UserService.class);
        confirm.setOnClickListener(v -> {
            String messageToEncrypt = login.getText().toString() + ";" + password.getText().toString() + ";" + "child";
            try {
                this.userService = ServiceGenerator.createService(UserService.class);
                Call<JwtResponse> call = userService.login(EncryptionTools.encrypt(messageToEncrypt));

                call.enqueue(new Callback<JwtResponse>() {
                    @Override
                    public void onResponse(Call<JwtResponse> call, Response<JwtResponse> response) {
                        if (response.isSuccessful()) {
                            ServiceGenerator.role="child";
                            ServiceGenerator.bearerToken = response.body().getJwttoken();
                            Intent intent = new Intent(ChildLoginActivity.this, ChildMainActivity.class);
                            startActivity(intent);

                        } else
                            new AlertDialog.Builder(ChildLoginActivity.this)
                                    .setTitle("Bad Login")
                                    .setMessage("Are You sure about Your login and password?")
                                    .show();
                    }

                    @Override
                    public void onFailure(Call<JwtResponse> call, Throwable t) {
                        Toast.makeText(ChildLoginActivity.this, "error", Toast.LENGTH_SHORT).show();


                    }
                });
            } catch (Exception e) {
                Toast.makeText(ChildLoginActivity.this, "not correct....", Toast.LENGTH_SHORT).show();
            }

        });


    }
}
