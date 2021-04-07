package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        System.out.println("InstallApkViaPackageInstaller - " + intent.getIntExtra("android.content.pm.extra.STATUS", 999));
        // context.startActivity(intent.getParcelableExtra(Intent.EXTRA_INTENT));
    }
}
