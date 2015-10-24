package com.kcoppock.broadcasttile;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.kcoppock.broadcasttilesupport.BroadcastTileIntentBuilder;

final class CustomTileHelper {
    /**
     * This is the identifier of the custom Broadcast Tile. Whatever action you configured the tile
     * for must be used when configuring the tile. For Broadcast tiles, only alphanumeric characters
     * (and periods) are allowed. Keep in mind that this excludes underscores.
     */
    private static final String BROADCAST_TILE_IDENTIFIER = "com.kcoppock.CUSTOMTILE";

    /**
     * Keeps track of the last known state of the Quick Settings custom tile. There doesn't seem to
     * be a way to query the state of the tile.
     */
    private static final String PREF_TILE_SHOWN = "com.kcoppock.CUSTOMTILE_SHOWN";

    private final Context mContext;
    private final TilePreferenceHelper mTilePreferenceHelper;

    CustomTileHelper(@NonNull Context context) {
        mContext = context.getApplicationContext();
        mTilePreferenceHelper = new TilePreferenceHelper(mContext);
    }

    void showTile() {
        mTilePreferenceHelper.setBoolean(PREF_TILE_SHOWN, true);

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
                mContext, 0, longClickBroadcast, PendingIntent.FLAG_CANCEL_CURRENT);

        // Send the update event to the Broadcast Tile. Custom tiles are hidden by default until
        // enabled with this broadcast Intent.
        mContext.sendBroadcast(new BroadcastTileIntentBuilder(mContext, BROADCAST_TILE_IDENTIFIER)
                .setVisible(true)
                .setLabel(mContext.getString(R.string.broadcast_tile_label))
                .setIconResource(R.drawable.ic_broadcast_tile)
                .setOnClickBroadcast(toastIntent)
                .setOnLongClickPendingIntent(onLongClickPendingIntent)
                .build());
    }

    void hideTile() {
        mTilePreferenceHelper.setBoolean(PREF_TILE_SHOWN, false);

        mContext.sendBroadcast(new BroadcastTileIntentBuilder(mContext, BROADCAST_TILE_IDENTIFIER)
                .setVisible(false)
                .build());
    }

    boolean isLastTileStateShown() {
        return mTilePreferenceHelper.getBoolean(PREF_TILE_SHOWN);
    }
}
