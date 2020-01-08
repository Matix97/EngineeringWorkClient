package com.example.exchangetoys;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.exchangetoys.DTOs.ToyServiceData.SuggestedToy;
import com.example.exchangetoys.Services.ServiceGenerator;
import com.example.exchangetoys.Services.UserService;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationService extends Service {

    private static String token;
    private  String CHANNEL_ID = "2";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

//    @Override
//    public void onTaskRemoved(Intent rootIntent) {
//        Intent intent = new Intent("com.android.ServiceStopped");
//        sendBroadcast(intent);
//    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //   startForeground(CHANNEL_ID,"jh");
        Bundle b = intent.getExtras();
        token = (String) b.get("token");

        //startForeground(CHANNEL_ID,builder);

        TimerTask doAsynchronousTask;
        //     final Handler handler = new Handler();
        Timer timer = new Timer();

        doAsynchronousTask = new TimerTask() {

            @Override
            public void run() {
                System.out.println("--------------------------------TIMER---------------------------" + token);
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
                                    Intent i = new Intent(NotificationService.this, ChildSettings.class);
                                    i.putExtra("child",t.getChild());
                                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                                    PendingIntent pendingIntent = PendingIntent.getActivity(NotificationService.this, 0, i, 0);
                                    System.out.println("MY_TAG: Notification: "+i.getExtras().toString());
                                    System.out.println("MY_TAG: Notification: "+t.getChild());
                                    Integer integer =Integer.parseInt(CHANNEL_ID);
                                    integer++;
                                    CHANNEL_ID = String.valueOf(integer);
                                    System.out.println("MY_TAG: Notification: "+ CHANNEL_ID);
                                    NotificationCompat.Builder builder = new NotificationCompat.Builder(NotificationService.this, CHANNEL_ID)
                                            .setSmallIcon(R.drawable.add_icon)
                                            .setContentTitle(t.getChild().getChild_name())
                                            .setContentText("I want "+t.getToy().getToy_name())
                                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                            // Set the intent that will fire when the user taps the notification
                                            .setContentIntent(pendingIntent)
                                            .setAutoCancel(true);

                                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(NotificationService.this);

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
//                handler.post(new Runnable() {
//                    public void run() {
//                        System.out.println("--------------------------------TIMER---------------------------");
//
//                    }
//                });

            }

        };

        timer.schedule(doAsynchronousTask, 0, 100000);// execute in every 100 s
        return Service.START_STICKY;
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
