package com.example.exchangetoys.child;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exchangetoys.DTOs.ToyServiceData.FilterDTO;
import com.example.exchangetoys.DTOs.ToyServiceData.Toy;
import com.example.exchangetoys.R;
import com.example.exchangetoys.Services.ServiceGenerator;
import com.example.exchangetoys.Services.ToyService;
import com.example.exchangetoys.ui.fragment.ToyArrayAdapter;
import com.google.android.gms.maps.LocationSource;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class ChildMainActivity extends AppCompatActivity  implements LocationSource.OnLocationChangedListener{

    private static RecyclerView toys;
    private FloatingActionButton filterButton;
    private static ArrayList<Toy> download = new ArrayList<>();
    private static Location startLocation;
    private static final int REQ_PERMISSION = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_main);
        toys = findViewById(R.id.my_toys);
        filterButton = findViewById(R.id.filter_button_child);
        filterButton.setOnClickListener(v -> {
            Criteria kr = new Criteria();
            LocationManager locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
            String theBestSupplier = locationManager.getBestProvider(kr, true);

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            Location location = locationManager.getLastKnownLocation(theBestSupplier);
    startLocation=location;
            // LatLng myLocation = new LatLng( location.getLatitude(),  location.getLongitude());
            FilterActivityChild filterActivity = new FilterActivityChild();
            filterActivity.showPopupWindow(filterButton.getRootView(),location,"child");
        });
        if (checkPermission())
        {}
        else askPermission();
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////potem wywalilć
        // Initializing list view with the custom adapter
        downloadToys2(new FilterDTO(),ChildMainActivity.toys,startLocation);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }
    public static void downloadToys2(FilterDTO filterDTO, View view,Location location){

        filterDTO.setLongitude(location.getLongitude());
        filterDTO.setLatitude(location.getLatitude());

        ToyService toyService = ServiceGenerator.createAuthorizedService(ToyService.class);
        Call<List<Toy>> call = toyService.getToys(filterDTO);
        call.enqueue(new Callback<List<Toy>>() {
            @Override
            public void onResponse(Call<List<Toy>> call, Response<List<Toy>> response) {
                if (response.isSuccessful()) {//todo create list
                    download.clear();
                    for (Toy t:response.body())
                        download.add(t);
                    ToyArrayAdapter itemArrayAdapter = new ToyArrayAdapter(R.layout.toy_item, download);
                    toys.setLayoutManager(new LinearLayoutManager(view.getContext()));
                    toys.setItemAnimator(new DefaultItemAnimator());
                    toys.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));
                    toys.setAdapter(itemArrayAdapter);

                } else
                    Toast.makeText(view.getContext(), "Error in GET toys \nToken"+ServiceGenerator.bearerToken, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Toy>> call, Throwable t) {
                Toast.makeText(view.getContext(), "FAILURE Error in GET toys ", Toast.LENGTH_SHORT).show();
            }
        });


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
                    if (checkPermission()){}
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
