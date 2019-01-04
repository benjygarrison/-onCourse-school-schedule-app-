package com.bengarrison.oncourse;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class AssessmentGoalAlarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationCompat.Builder assessmentNotificationBuilder = new NotificationCompat.Builder(context)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Heads up!")
                .setContentText("You have an upcoming assessment goal date!");

        NotificationManager assessmentNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        assessmentNotificationManager.notify(1, assessmentNotificationBuilder.build());

    }
}
