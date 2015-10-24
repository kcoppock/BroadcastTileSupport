package com.kcoppock.broadcasttile;

import android.content.*;

/**
 * BroadcastReceiver for the BOOT_COMPLETED event which handles re-registering the custom tile after
 * a reboot if it was previously shown.
 */
public final class BootCompletedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            final CustomTileHelper helper = new CustomTileHelper(context);

            if (helper.isLastTileStateShown()) {
                helper.showTile();
            }
        }
    }
}
