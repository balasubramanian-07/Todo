package com.balasubramanian.todo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.balasubramanian.todo.activities.MainActivity;

public class AlarmReceiverService extends Service {

    private static final String TAG = "AlarmReceiverService";

    private NotificationManager notificationManager;

    @Override
    public void onCreate() {

        super.onCreate();

        Log.i(TAG, "onCreate()");

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i("LocalService", "Received start id " + startId + ": " + intent);

        showNotification(intent);

        return START_NOT_STICKY;
    }

    private void showNotification(Intent intent) {

        String title = intent.getStringExtra("TITLE");
        String description = intent.getStringExtra("DESCRIPTION");

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(android.R.drawable.stat_notify_more)
                .setContentTitle(title)
                .setContentText(description);

        Intent resultIntent = new Intent(this, MainActivity.class);

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.setContentIntent(pendingIntent);
        Notification notification = notificationBuilder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(1001, notification);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        // No binding currently
        return null;
    }


}
