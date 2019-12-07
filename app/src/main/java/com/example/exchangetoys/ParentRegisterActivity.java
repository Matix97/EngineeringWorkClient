package com.example.exchangetoys;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.exchangetoys.Services.ServiceGenerator;
import com.example.exchangetoys.Services.UserService;
import com.example.exchangetoys.Tools.EncryptionTools;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//todo add location to register
public class ParentRegisterActivity extends Activity {
    Button accept;
    EditText name, surname, email, phoneNumber, password, confirmPassword;
    private UserService userService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.userService = ServiceGenerator.createService(UserService.class);

        setContentView(R.layout.register_activity);
        name = findViewById(R.id.name_register);
        surname = findViewById(R.id.surname_register);
        email = findViewById(R.id.email_register);
        phoneNumber = findViewById(R.id.phone_number_register);
        password = findViewById(R.id.password_register);
        confirmPassword = findViewById(R.id.confirm_password_register);

        accept = findViewById(R.id.confirm_register_adult);
        accept.setOnClickListener(v -> {
            tryRegister();
        });
    }

    private void tryRegister() {
        if (ifAllRequireFieldAreFill()) {
            if (password.getText().toString().equals(confirmPassword.getText().toString())) {
                registerPostHandler();
            }
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Warning")
                    .setMessage("Please fill all require fields")
                    .setNegativeButton(android.R.string.ok, null)
                    .show();
        }
    }

    private boolean ifAllRequireFieldAreFill() {
        return TextUtils.isEmpty(name.getText().toString()) && TextUtils.isEmpty(surname.getText().toString())
                && TextUtils.isEmpty(email.getText().toString())
                && TextUtils.isEmpty(password.getText().toString()) && TextUtils.isEmpty(confirmPassword.getText().toString());
    }

    private void registerPostHandler() {
        String messageToEncrypt = name.getText().toString() + ";" + surname.getText().toString()
                + ";" + password.getText().toString() + ";" + phoneNumber.getText().toString()
                + ";" + email.getText().toString();
        try {
            Call<Void> call = userService.registerParent(EncryptionTools.encrypt(messageToEncrypt));
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        new AlertDialog.Builder(ParentRegisterActivity.this)
                                .setTitle("Register")
                                .setMessage("Welcome in toys' world")
                                .setNegativeButton(android.R.string.ok, null)
                                .show();

                    } else {
                        Toast.makeText(ParentRegisterActivity.this, "error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(ParentRegisterActivity.this, "error 2", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Toast.makeText(ParentRegisterActivity.this, "error 3", Toast.LENGTH_SHORT).show();
        }


    }
}
