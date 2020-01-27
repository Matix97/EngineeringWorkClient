package com.example.exchangetoys;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.exchangetoys.Services.ServiceGenerator;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ParentMainActivity extends AppCompatActivity {
   // public static final String MESSAGE_STATUS = "message_status";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ServiceGenerator.role="adult";
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        Bundle bundle = getIntent().getExtras();
        ServiceGenerator.bearerToken = bundle.getString("token");
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

//        final WorkManager mWorkManager = WorkManager.getInstance();
//       // final OneTimeWorkRequest mRequest = new OneTimeWorkRequest.Builder(NotificationWorker.class).build();
//        final PeriodicWorkRequest mRequest = new PeriodicWorkRequest.Builder(NotificationWorker.class, 60*24, TimeUnit.HOURS).build();
//        mWorkManager.enqueue(mRequest);
//        mWorkManager.getWorkInfoByIdLiveData(mRequest.getId()).observe(this, workInfo -> {
//            if (workInfo != null) {
//                WorkInfo.State state = workInfo.getState();
//                new AlertDialog.Builder(ParentMainActivity.this)
//                        .setTitle("WORKER")
//                        .setMessage(state.toString())
//                        .setNegativeButton(android.R.string.ok, null)
//                        .show();
//
//            }
//        });



    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

}
