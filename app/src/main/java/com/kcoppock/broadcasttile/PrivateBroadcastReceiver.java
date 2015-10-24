package com.kcoppock.broadcasttile;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.*;
import android.support.v4.app.NotificationCompat;

/**
 * Receiver for click events on the custom Quick Settings tile
 */
public final class PrivateBroadcastReceiver extends BroadcastReceiver {
    /**
     * Action constant defining that a push notification should be displayed
     */
    public static final String ACTION_NOTIFICATION = "com.kcoppock.CUSTOMTILE_ACTION_NOTIFICATION";

    /**
     * Key for the integer extra value to be used for the notification ID
     */
    public static final String EXTRA_NOTIFICATION_ID = "com.kcoppock.CUSTOMTILE_NOTIFICATION_ID";

    /**
     * Key for the string extra to be displayed in the notification title
     */
    public static final String EXTRA_NOTIFICATION_TITLE = "com.kcoppock.CUSTOMTILE_NOTIFICATION_TITLE";

    /**
     * Key for the string extra to be displayed in the notification body
     */
    public static final String EXTRA_NOTIFICATION_BODY = "com.kcoppock.CUSTOMTILE_NOTIFICATION_BODY";

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();

        if (ACTION_NOTIFICATION.equals(action)) {
            final int notificationId = intent.getIntExtra(EXTRA_NOTIFICATION_ID, 0);
            final String notificationTitle = intent.getStringExtra(EXTRA_NOTIFICATION_TITLE);
            final String notificationBody = intent.getStringExtra(EXTRA_NOTIFICATION_BODY);

            Notification notification = new NotificationCompat.Builder(context)
                    .setContentTitle(notificationTitle)
                    .setContentText(notificationBody)
                    .setSmallIcon(R.drawable.ic_broadcast_tile)
                    .build();

            NotificationManager nm = (NotificationManager)
                    context.getSystemService(Context.NOTIFICATION_SERVICE);

            nm.notify(notificationId, notification);
        }
    }
}
