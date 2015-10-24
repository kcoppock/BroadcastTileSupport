package com.kcoppock.broadcasttile;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    /**
     * This is the identifier of the custom Broadcast Tile. Whatever action you configured the tile
     * for must be used when configuring the tile. For Broadcast tiles, only alphanumeric characters
     * (and periods) are allowed. Keep in mind that this excludes underscores.
     */
    private static final String BROADCAST_TILE_IDENTIFIER = "com.kcoppock.CUSTOMTILE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tile_broadcast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configureTile();
            }
        });
    }

    private void configureTile() {
        // Set up an Intent that will be broadcast by the system, and received by the exported
        // PublicBroadcastReceiver.
        final Intent toastIntent = new Intent(PublicBroadcastReceiver.ACTION_TOAST);
        toastIntent.putExtra(PublicBroadcastReceiver.EXTRA_MESSAGE, "Hello!");

        // Set up a PendingIntent that will be delivered back to the application on a long-click
        // of the custom Broadcast Tile.
        final Intent longClickBroadcast = new Intent(PrivateBroadcastReceiver.ACTION_NOTIFICATION);
        longClickBroadcast.putExtra(PrivateBroadcastReceiver.EXTRA_NOTIFICATION_ID, 2);
        longClickBroadcast.putExtra(PrivateBroadcastReceiver.EXTRA_NOTIFICATION_TITLE, "Title");
        longClickBroadcast.putExtra(PrivateBroadcastReceiver.EXTRA_NOTIFICATION_BODY, "Body.");

        final PendingIntent onLongClickPendingIntent = PendingIntent.getBroadcast(
                this, 0, longClickBroadcast, PendingIntent.FLAG_CANCEL_CURRENT);

        // Send the update event to the Broadcast Tile. Custom tiles are hidden by default until
        // enabled with this broadcast Intent.
        sendBroadcast(new BroadcastTileIntentBuilder(BROADCAST_TILE_IDENTIFIER)
                .setVisible(true)
                .setLabel(getString(R.string.broadcast_tile_label))
                .setIconResource(R.drawable.ic_broadcast_tile)
                .setOnClickBroadcast(toastIntent)
                .setOnLongClickPendingIntent(onLongClickPendingIntent)
                .build());
    }
}
