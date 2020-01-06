package com.example.exchangetoys;

import android.util.Log;

import androidx.annotation.NonNull;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;

public class MySyncJob extends Job {
    private final String CHANNEL_ID ="2";
    public static final String TAG = "my_job_tag";

    @Override
    @NonNull
    protected Result onRunJob(Params params) {
        //
        // run your job here
        //
        //
        Log.d(TAG, "-------------TEST-------------" );
        System.out.println("-----------------------JAK-----------------");
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(StartActiv, CHANNEL_ID)
//                .setSmallIcon(R.drawable.add_icon)
//                .setContentTitle("testtROLE")
//                .setContentText("TOKEN")
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                // Set the intent that will fire when the user taps the notification
//             //   .setContentIntent(pendingIntent)
//                //.setAutoCancel(true);
//                ;
        return Result.SUCCESS;
    }

    public static void scheduleJob() {
        new JobRequest.Builder(MySyncJob.TAG)
                .setExecutionWindow(30_000L, 40_000L) //Every 30 seconds for 40 seconds
                .build()
                .schedule();
    }
}