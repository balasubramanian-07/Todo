package com.balasubramanian.todo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;
import java.util.Map;

public class AlarmHelper {

    private static final String TAG = "AlarmHelper";

    public static void setSystemAlarm(Context context, Class alarmReceiver, Calendar date, Map<String, String> extras) {

        Intent intent = new Intent(context, alarmReceiver);
        for (Map.Entry<String, String> entry : extras.entrySet()) {

            intent.putExtra(entry.getKey(), entry.getValue());
        }

        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, date.getTimeInMillis(), pendingIntent);
    }
}
