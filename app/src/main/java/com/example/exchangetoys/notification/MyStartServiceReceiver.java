package com.example.exchangetoys.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class MyStartServiceReceiver extends BroadcastReceiver {

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("MAT: "+"MyStartServiceReceiver - onREceived");
        Util.scheduleJob(context);
    }
}
