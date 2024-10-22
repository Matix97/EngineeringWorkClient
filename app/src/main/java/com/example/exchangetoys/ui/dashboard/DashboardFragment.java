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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exchangetoys.DTOs.ToyServiceData.FilterDTO;
import com.example.exchangetoys.DTOs.ToyServiceData.Toy;
import com.example.exchangetoys.FilterActivityParent;
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
import static android.content.Context.LOCATION_SERVICE;

public class DashboardFragment extends Fragment  implements LocationSource.OnLocationChangedListener{


    public static RecyclerView toys;
    private FloatingActionButton filterButton;
    private static final int REQ_PERMISSION = 0;
    private static ArrayList<Toy> download = new ArrayList<>();

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
            FilterActivityParent filterActivity = new FilterActivityParent();
            filterActivity.showPopupWindow(root,location,"notChild");
        });
        if (checkPermission())
        {}
        else askPermission();
        // my_toys.getLayoutParams().height=root.getHeight()/2-50;
        //rented_toys.getLayoutParams().height=root.getHeight()/2-50;
        ///////////////////////
        downloadToys(new FilterDTO(),root);




        return root;
    }
    public static void downloadToys(FilterDTO filterDTO,View view){
        //only to test
////        //
//        Toy toy=new Toy();
//        toy.setToy_name("test");
//        toy.setToy_description("opisafdsfdsaf" +
//                "dsaf\n" +
//                "dsafds\n" +
//                "fasd" +
//                "f");
//        toy.setToy_photos("https://res.cloudinary.com/dxlmhjfv1/image/upload/v1578138519/s1dsa6lwuzi6ajalkadv.jpg;https://res.cloudinary.com/dxlmhjfv1/image/upload/v1578138569/kyjqlxq5v77sx3pix9l1.jpg");
//        download.add(toy);
//        ServiceGenerator.role="adult";
//        ToyArrayAdapter itemArrayAdapter = new ToyArrayAdapter(R.layout.toy_item, download);
//        toys.setLayoutManager(new LinearLayoutManager(view.getContext()));
//        toys.setItemAnimator(new DefaultItemAnimator());
//        toys.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));
//        toys.setAdapter(itemArrayAdapter);
        ///
ToyService toyService = ServiceGenerator.createAuthorizedService(ToyService.class);
Call<List<Toy>> call = toyService.getToys(filterDTO);
call.enqueue(new Callback<List<Toy>>() {
    @Override
    public void onResponse(Call<List<Toy>> call, Response<List<Toy>> response) {
        if (response.isSuccessful()) {
            download.clear();
            for (Toy t:response.body())
                download.add(t);
            ToyArrayAdapter itemArrayAdapter = new ToyArrayAdapter(R.layout.toy_item, download);
            toys.setLayoutManager(new LinearLayoutManager(view.getContext()));
            toys.setItemAnimator(new DefaultItemAnimator());
            toys.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));
            toys.setAdapter(itemArrayAdapter);

        }
        else
            Toast.makeText(view.getContext(), "Error in GET toys ", Toast.LENGTH_SHORT).show();
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