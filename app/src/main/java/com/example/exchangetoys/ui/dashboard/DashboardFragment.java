package com.example.exchangetoys.ui.dashboard;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exchangetoys.FilterActivity;
import com.example.exchangetoys.R;
import com.example.exchangetoys.ui.fragment.ToyArrayAdapter;
import com.example.exchangetoys.ui.fragment.ToyModelToRecycle;
import com.google.android.gms.maps.LocationSource;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;
import static android.content.Context.LOCATION_SERVICE;

public class DashboardFragment extends Fragment  implements LocationSource.OnLocationChangedListener{


    private RecyclerView toys;
    private FloatingActionButton filterButton;
    private static final int REQ_PERMISSION = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_main_list_of_toys, container, false);

        toys = root.findViewById(R.id.my_toys);
        filterButton = root.findViewById(R.id.filter_button);
        filterButton.setOnClickListener(v -> {
            Criteria kr = new Criteria();
            LocationManager locationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);
            String theBestSupplier = locationManager.getBestProvider(kr, true);

            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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

           // LatLng myLocation = new LatLng( location.getLatitude(),  location.getLongitude());
            FilterActivity filterActivity = new FilterActivity();
            filterActivity.showPopupWindow(root,location);
        });
        if (checkPermission())
        {}
        else askPermission();
        // my_toys.getLayoutParams().height=root.getHeight()/2-50;
        //rented_toys.getLayoutParams().height=root.getHeight()/2-50;
        ///////////////////////
        // Initializing list view with the custom adapter
        ArrayList<ToyModelToRecycle> itemList = new ArrayList<>();
        // Populating list items
        for (int i = 0; i < 100; i++) {
            itemList.add(new ToyModelToRecycle("Toy " + i, "opis", R.drawable.child_button_picture));
        }
        ToyArrayAdapter itemArrayAdapter = new ToyArrayAdapter(R.layout.toy_item, itemList);

        toys.setLayoutManager(new LinearLayoutManager(root.getContext()));
        toys.setItemAnimator(new DefaultItemAnimator());
        toys.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        toys.setAdapter(itemArrayAdapter);
        return root;
    }
    // Check for permission to access Location
    private boolean checkPermission() {
        Log.d(TAG, "checkPermission()");
        // Ask for permission if it wasn't granted yet
        return (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED);
    }

    // Asks for permission
    private void askPermission() {
        Log.d(TAG, "askPermission()");
        ActivityCompat.requestPermissions(
                getActivity(),
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