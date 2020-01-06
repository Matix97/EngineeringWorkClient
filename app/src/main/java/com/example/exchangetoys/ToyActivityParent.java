package com.example.exchangetoys;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exchangetoys.DTOs.ToyServiceData.Toy;
import com.example.exchangetoys.ui.fragment.ImageArrayAdapter;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class ToyActivityParent extends Activity {
    private RecyclerView images;
    private TextView name, description;
    private Button contactByEmail, contactByPhone;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toy_activity_parent);
        Bundle bundle = getIntent().getExtras();
        Toy toy = bundle.getParcelable("toy");
        //image=findViewById(R.id.toy_activity_image);
        name = findViewById(R.id.toy_activity_name);
        description = findViewById(R.id.toy_activity_description);
        name.setText(toy.getToy_name());
        description.setText(toy.getToy_description());
        contactByPhone = findViewById(R.id.contactByPhone);
        if(toy.getToy_owner_phone_number()==null) contactByPhone.setEnabled(false);
        contactByEmail = findViewById(R.id.contactByEmial);
        contactByPhone.setOnClickListener(v -> phoneHandler(toy.getToy_owner_phone_number()));
        contactByEmail.setOnClickListener(v -> emailHandler(toy.getToy_owner_id(),creatBodyMail(toy),creatSubject(toy)));

        if (toy.getToy_photos() != null && toy.getToy_photos() != "") {
            String[] urls = toy.getToy_photos().split(";");
            ArrayList<String> list = new ArrayList<>();
            for (String q : urls) list.add(q);
            ImageArrayAdapter imageArrayAdapter = new ImageArrayAdapter(list);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            images = findViewById(R.id.toy_activity_image);
            images.setLayoutManager(linearLayoutManager);
            images.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
            images.setItemAnimator(new DefaultItemAnimator());
            images.setAdapter(imageArrayAdapter);
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;

        ViewGroup.LayoutParams layoutParams = images.getLayoutParams();
        layoutParams.height = height / 2 - 200;
        images.setLayoutParams(layoutParams);


    }
    private String creatBodyMail(Toy toy){
        StringBuilder s = new StringBuilder("Witam, \nzwracam się z uprzejmym zapytaniem dotyczącym ogłoszenia \"");
        s.append(toy.getToy_name());
        s.append("\"\n<wpisz swoje pytania>");
        s.append("\nSErdecznie pozdrawaiam\n ");


        return s.toString();
    }
    private String creatSubject(Toy toy){
        StringBuilder s = new StringBuilder("Toy's app: ");
        s.append(toy.getToy_name());
        return s.toString();
    }

    private void emailHandler(String ownerEmail, String emailMessage,String emailTitle) {

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + ownerEmail));
    //    emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{ownerEmail});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, emailTitle);
        emailIntent.putExtra(Intent.EXTRA_TEXT, emailMessage);
//emailIntent.putExtra(Intent.EXTRA_HTML_TEXT, body); //If you are using HTML in your body text

        startActivity(Intent.createChooser(emailIntent, "Send Email"));

    }


    private void phoneHandler(String phone) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                askPermission();

            }
            else{
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                if(phone !=null)
                    callIntent.setData(Uri.parse("tel:"+phone));//change the number
                startActivity(callIntent);}
        }

    }
    private static final int REQ_PERMISSION = 0;
    // Check for permission to access Location
    private boolean checkPermission() {

        return (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                == PackageManager.PERMISSION_GRANTED);
    }

    // Asks for permission
    private void askPermission() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.CALL_PHONE},
                REQ_PERMISSION
        );
    }
    public void onRequestPermissionsResult(int requestCode, String[] permissions,       int[] grantResults){
        Log.d(TAG, "onRequestPermissionsResult()");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQ_PERMISSION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted
                    if (checkPermission()){}



                } else {
                    // Permission denied

                }
                break;
            }
        }
    }
}
