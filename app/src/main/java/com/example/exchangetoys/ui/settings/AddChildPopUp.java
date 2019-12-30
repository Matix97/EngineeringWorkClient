package com.example.exchangetoys.ui.settings;

import android.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.exchangetoys.R;
import com.example.exchangetoys.Services.ServiceGenerator;
import com.example.exchangetoys.Services.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddChildPopUp {

    private EditText name, login, password, confirmPassword, childAge;
    private TextView radius_info;
    private SeekBar radius;
    private Integer seekBarValue;
    //PopupWindow display method
    private View view;
    private UserService userService;
    private Button accept;
     PopupWindow popupWindow;
    public void showPopupWindow(final View view) {

        this.view = view;
        //Create a View object yourself through inflater
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.add_child, null);

        this.userService = ServiceGenerator.createAuthorizedService(UserService.class);
        //Specify the length and width through constants
        int width = view.getWidth() - 150;
        int height = view.getHeight() - 300;

        //Make Inactive Items Outside Of PopupWindow
        boolean focusable = true;

        //Create a window with our parameters
         popupWindow = new PopupWindow(popupView, width/*900*/, height/*1300*/, focusable);

        //Set the location of the window on the screen
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 70);
        name = popupView.findViewById(R.id.name_child_register);
        login = popupView.findViewById(R.id.login_child_register);
        password = popupView.findViewById(R.id.password_register_child);
        confirmPassword = popupView.findViewById(R.id.confirm_password_register_child);
        childAge = popupView.findViewById(R.id.child_age);
        radius_info = popupView.findViewById(R.id.radius_info);
        radius = popupView.findViewById(R.id.seekBar);
        radius.setProgress(30);
        radius_info.setText("Chosen radius: " + 30 + " km");
        seekBarValue=30;
        radius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                seekBarValue = progress;
                radius_info.setText("Chosen radius: " + progress + " km");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub


            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
        });

        //Initialize the elements of our window, install the handler
        accept = popupView.findViewById(R.id.confirm_register_adult);
        accept.setOnClickListener(v -> {
            tryRegister();
        });

        //Handler for clicking on the inactive zone of the window jak odkometnuję to trochę upierdliwe, ale w sumie ciekaawe

//        popupView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                //Close the window when clicked
//                popupWindow.dismiss();
//                return true;
//            }
//        });
    }

    private void tryRegister() {
        if (ifAllRequireFieldAreFill()) {
            try {
                Integer.parseInt(childAge.getText().toString());
                if (password.getText().toString().equals(confirmPassword.getText().toString())) {
                    registerPostHandler();
                }
                else{
                    new AlertDialog.Builder(view.getContext())
                            .setTitle("Error")
                            .setMessage("passwords aren't the same")
                            .setNegativeButton(android.R.string.ok, null)
                            .show();
                }
            } catch (NumberFormatException e) {
                new AlertDialog.Builder(view.getContext())
                        .setTitle("Error")
                        .setMessage("age is not a number")
                        .setNegativeButton(android.R.string.ok, null)
                        .show();
            }

        } else {
            new AlertDialog.Builder(view.getContext())
                    .setTitle("Warning")
                    .setMessage("Please fill all require fields")
                    .setNegativeButton(android.R.string.ok, null)
                    .show();
        }
    }

    private boolean ifAllRequireFieldAreFill() {
        return !(TextUtils.isEmpty(childAge.getText().toString()) && TextUtils.isEmpty(name.getText().toString()) && TextUtils.isEmpty(login.getText().toString())
                && TextUtils.isEmpty(password.getText().toString()) && TextUtils.isEmpty(confirmPassword.getText().toString()));
    }

    private void registerPostHandler() {
        String messageToEncrypt = name.getText().toString() + ";" + login.getText().toString()
                + ";" + password.getText().toString() + ";" + childAge.getText().toString()
                + ";" + seekBarValue.toString();

        try {

            Call<Void> call = userService.registerChild(messageToEncrypt);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {

                        new AlertDialog.Builder(view.getContext())
                                .setTitle("Register")
                                .setMessage("child add")
                                .setNegativeButton(android.R.string.ok, null)
                                .show();

                    } else {
                        Toast.makeText(view.getContext(), "Maybe another login", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(view.getContext(), "error 2", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Toast.makeText(view.getContext(), "error 3", Toast.LENGTH_SHORT).show();
        }

        popupWindow.dismiss();
    }

}