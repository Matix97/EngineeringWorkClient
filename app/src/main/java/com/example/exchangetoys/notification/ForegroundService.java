package com.example.exchangetoys.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.exchangetoys.DTOs.ToyServiceData.SuggestedToy;
import com.example.exchangetoys.ParentMainActivity;
import com.example.exchangetoys.R;
import com.example.exchangetoys.Services.ServiceGenerator;
import com.example.exchangetoys.Services.UserService;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForegroundService extends Service {
    public static final String CHANNEL_ID = "ForegroundServiceChannel";
    private  String CHANNEL_ID2 = "2";
    public static String token;
    @Override
    public void onCreate() {
        super.onCreate();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String input = intent.getStringExtra("token");
        token=input;
        createNotificationChannel();
        Intent notificationIntent = new Intent(this, ParentMainActivity.class);
        notificationIntent.putExtra("token",input);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("ToY")
                .setContentText("Your child want new toy")
                .setSmallIcon(R.mipmap.test_round)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                        R.mipmap.test_round))
                .setTicker(input)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1, notification);
        //do heavy work on a background thread
        //stopSelf();
        TimerTask doAsynchronousTask;
        Timer timer = new Timer();
        doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                UserService userService = ServiceGenerator.createAuthorizedService(UserService.class);

                Call<List<SuggestedToy>> call = userService.getSuggestion();
                call.enqueue(new Callback<List<SuggestedToy>>() {
                    @Override
                    public void onResponse(Call<List<SuggestedToy>> call, Response<List<SuggestedToy>> response) {
                        if (response.isSuccessful()) {
                            if (response.body().size() > 0) {
                                System.out.println("--------------------------------TIMER---------------------------MAMY COS*****************" );
                                int j=0;
                                for (SuggestedToy t:response.body()   ) {
                                    System.out.println(t);
                                    Intent i = new Intent(ForegroundService.this, ParentMainActivity.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    PendingIntent pendingIntent = PendingIntent.getActivity(ForegroundService.this, 0, i, 0);
                                    System.out.println("MY_TAG: Notification: "+t.getChild());
                                    Integer integer =Integer.parseInt(CHANNEL_ID2);
                                    integer++;
                                    String CHANNEL_ID2 = String.valueOf(integer);
                                    System.out.println("MY_TAG: Notification: "+ CHANNEL_ID2);
                                    NotificationCompat.Builder builder = new NotificationCompat.Builder(ForegroundService.this, CHANNEL_ID2)
                                            .setSmallIcon(R.mipmap.test_round)
                                            .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                                                    R.mipmap.test_round))
                                            .setContentTitle(t.getChild().getChild_name())
                                            .setContentText("I want "+t.getToy().getToy_name())
                                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                            // Set the intent that will fire when the user taps the notification
                                            .setContentIntent(pendingIntent)
                                            .setAutoCancel(true);

                                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(ForegroundService.this);

// notificationId is a unique int for each notification that you must define
                                    notificationManager.notify(++j, builder.build());
                                }

                            }

                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<List<SuggestedToy>> call, Throwable t) {

                    }
                });
            }
        } ;
        timer.schedule(doAsynchronousTask, 0, 100000);// execute in every 1000 s
        return START_NOT_STICKY;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }
}