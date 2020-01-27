package com.example.exchangetoys;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exchangetoys.DTOs.ToyServiceData.RentalDTO;
import com.example.exchangetoys.DTOs.ToyServiceData.Toy;
import com.example.exchangetoys.Services.ServiceGenerator;
import com.example.exchangetoys.Services.ToyService;
import com.example.exchangetoys.Tools.ImageUtils;
import com.example.exchangetoys.Tools.UploadImage;
import com.example.exchangetoys.Tools.UploadedPhotoURL;
import com.example.exchangetoys.recycleAdapters.ImageAdapter;
import com.example.exchangetoys.recycleAdapters.ImageArrayAdapter;
import com.google.android.gms.maps.LocationSource;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class RentalToyActivity extends Activity implements LocationSource.OnLocationChangedListener {

    static final int REQUEST_TAKE_PHOTO = 1;
    private static final int REQ_PERMISSION = 0;
    private RecyclerView photos;
    private Button makePhoto, confirm;
    private ArrayList<ImageAdapter> itemList;
    private File pictureImagePath = null;
    private UploadedPhotoURL uploadedPhotoURLs;
    private Spinner toyToRent;
    private Button returnDataButton;
    private EditText borrowerEmail, borrowerPassword;
    private int mYear, mMonth, mDay;
    private TextView dataText;

    private ArrayList<Toy> myToysData;

    public static Date getDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rental_activity);

        myToysData = new ArrayList<>();
        makePhoto = findViewById(R.id.make_photo_button);
        confirm = findViewById(R.id.confirm_rental);
        toyToRent = findViewById(R.id.toy_to_rent);
        setUpToyToRent();
        returnDataButton = findViewById(R.id.choose_data_button);
        dataText = findViewById(R.id.choose_data_text);
        borrowerEmail = findViewById(R.id.borrower_email);
        borrowerPassword = findViewById(R.id.borrower_password);
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        dataText.setText(date);
        returnDataButton.setOnClickListener(v -> {
            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (view, year, monthOfYear, dayOfMonth) -> dataText.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth), mYear, mMonth, mDay);
            datePickerDialog.show();
        });
        makePhoto.setOnClickListener(v -> {
            dispatchTakePictureIntent();

        });
        confirm.setOnClickListener(v -> {
            confirmFunction();
        });
        itemList = new ArrayList<>();
        ImageArrayAdapter itemArrayAdapter = new ImageArrayAdapter(itemList, R.layout.fragment_photo);
        photos = findViewById(R.id.uploaded_photos);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(RentalToyActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        photos.setLayoutManager(linearLayoutManager);
        photos.setItemAnimator(new DefaultItemAnimator());
        photos.addItemDecoration(new DividerItemDecoration(RentalToyActivity.this, DividerItemDecoration.HORIZONTAL));
        photos.setAdapter(itemArrayAdapter);


        UploadedPhotoURL.init();


    }

    private void setUpToyToRent() {
        ToyService toyService = ServiceGenerator.createAuthorizedService(ToyService.class);
        Call<List<Toy>> call = toyService.getYourToysAdvert();
        call.enqueue(new Callback<List<Toy>>() {
            @Override
            public void onResponse(Call<List<Toy>> call, Response<List<Toy>> response) {
                myToysData.clear();
                for (Toy t : response.body()) {
                    myToysData.add(t);
                }
                String[] array = new String[myToysData.size()];

                for (int i = 0; i < myToysData.size(); i++) {
                    array[i] = myToysData.get(i).getToy_name()+" "+myToysData.get(i).getToy_id();
                }
                ArrayAdapter<String> adapter = new ArrayAdapter(RentalToyActivity.this, android.R.layout.simple_spinner_item, array);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                toyToRent.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Toy>> call, Throwable t) {

            }
        });
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
                pictureImagePath = photoFile;
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);

            }
        }
    }

    //    //to get image from camera
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            File imgFile = pictureImagePath;
            if (imgFile.exists()) {
                try {
                    Bitmap myBitmap = ImageUtils.getInstant().getCompressedBitmap(imgFile.getAbsolutePath());
                    itemList.add(new ImageAdapter(myBitmap));
                    ViewGroup.LayoutParams layoutParams = photos.getLayoutParams();
                    layoutParams.height = 300;
                    photos.setLayoutParams(layoutParams);
                    try {

                        UploadImage.execute(myBitmap, this);
                        UploadedPhotoURL.IMAGE_COUNT_TO_UPLOAD++;//to synchronise

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }catch (Exception e){

                }

            }
        }
    }

    private void confirmFunction() {
        if (UploadedPhotoURL.IMAGE_COUNT_TO_UPLOAD == 0 || UploadedPhotoURL.ALL_IMAGE_UPLOADED) {
            ArrayList<String> photoURLS = UploadedPhotoURL.getUrls();
            ToyService toyService = ServiceGenerator.createAuthorizedService(ToyService.class);
            RentalDTO rentalDTO = new RentalDTO();
            rentalDTO.setPhotos(photoURLS);
            String id=null;
            try{
                 id= toyToRent.getSelectedItem().toString().split(" ")[toyToRent.getSelectedItem().toString().split(" ").length-1];

            rentalDTO.setToyIdTo(Long.valueOf(id));
            rentalDTO.setFutureHolderEmail(borrowerEmail.getText().toString());
            rentalDTO.setFutureHolderPassword(borrowerPassword.getText().toString());
           // String [] d = dataText.getText().toString().split("-");
            //int year=Integer.parseInt(d[0]),month=Integer.parseInt(d[1]),day=Integer.parseInt(d[2]);
         //   rentalDTO.setRentDate(getDate( year,month,day));
                rentalDTO.setSuggestedReturnDate(dataText.getText().toString());
            }catch (Exception e){

                new AlertDialog.Builder(RentalToyActivity.this)
                        .setTitle("ERROR")
                        .setMessage("Bad name of advert")
                        //  .setNegativeButton(android.R.string.ok, null)
                        .show();

            }
            System.out.println("PUPA DEBUG: "+rentalDTO.toString());
            Call<ResponseBody> call = toyService.rentToy(rentalDTO);
            call.enqueue(new Callback<ResponseBody>() {
                @Override // TODO: 27/12/2019 jakieś komunikaty czy ogłoszenie dodane
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {


                        new AlertDialog.Builder(RentalToyActivity.this)
                                .setTitle("Success")
                                .setMessage("RENTAL OK")
                              //  .setNegativeButton(android.R.string.ok, null)
                                .show();
                        finish();


                    } else
                        new AlertDialog.Builder(RentalToyActivity.this)
                                .setTitle("Failed")
                                .setMessage("BAD RENTAL")
                                //.setNegativeButton(android.R.string.ok, null)
                                .show();

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    new AlertDialog.Builder(RentalToyActivity.this)
                            .setTitle("Failure")
                            .setMessage("Check internet connection or restart application")
                           // .setNegativeButton(android.R.string.ok, null)
                            .show();
                }
            });

            UploadedPhotoURL.clear();
            //  finish();
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Wait")
                    .setMessage("We are uploading Your photos, try again in few seconds")
                    .setNegativeButton(android.R.string.ok, null)
                    .show();
        }


    }

    // Check for permission to access Location
    private boolean checkPermission() {
        Log.d(TAG, "checkPermission()");
        // Ask for permission if it wasn't granted yet
        return (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED);
    }

    // Asks for permission
    private void askPermission() {
        Log.d(TAG, "askPermission()");
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                REQ_PERMISSION
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult()");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQ_PERMISSION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted
                    if (checkPermission()) {
                    }
                    // googleMap.setMyLocationEnabled(true);

                } else {
                    // Permission denied

                }
                break;
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        //  LatLng myLocation = new LatLng( location.getLatitude(),  location.getLongitude());


    }
}
