package com.kcoppock.broadcasttilesupport;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.*;

/**
 * Creates an Intent to update the state of a custom tile.
 */
public final class BroadcastTileIntentBuilder {
    private static final String EXTRA_VISIBLE = "visible";
    private static final String EXTRA_CONTENT_DESCRIPTION = "contentDescription";
    private static final String EXTRA_LABEL = "label";
    private static final String EXTRA_ICON_RESOURCE_ID = "iconId";
    private static final String EXTRA_ICON_PACKAGE = "iconPackage";
    private static final String EXTRA_ON_CLICK_PENDING_INTENT = "onClick";
    private static final String EXTRA_ON_CLICK_URI = "onClickUri";
    private static final String EXTRA_ON_LONG_CLICK_PENDING_INTENT = "onLongClick";
    private static final String EXTRA_ON_LONG_CLICK_URI = "onLongClickUri";

    private final String mAction;
    private String mLabel;
    private String mContentDescription;
    private int mIconResource;
    private String mIconPackage;
    private PendingIntent mOnClickIntent;
    private String mOnClickUriString;
    private PendingIntent mOnLongClickIntent;
    private String mOnLongClickUriString;
    private boolean mVisible;

    /**
     * Constructs a new BroadcastTileIntentBuilder which, when built, can update the properties of
     * the Broadcast Tile with the specified action. The action must contain only alphanumeric and
     * period characters (0-9, A-Z, a-z, .) as that is the only format supported by the OS.
     *
     * @throws IllegalArgumentException if the action is invalid
     */
    public BroadcastTileIntentBuilder(@NonNull Context context, @NonNull String action) {
        if (!isValidAction(action)) {
            throw new IllegalArgumentException(
                    "Action can only contain alphanumeric characters and periods.");
        }
        mIconPackage = context.getPackageName();
        mAction = action;
    }

    /**
     * Sets the label that is displayed on the Quick Settings tile
     */
    @NonNull
    public BroadcastTileIntentBuilder setLabel(@Nullable String label) {
        mLabel = label;
        return this;
    }

    /**
     * Sets a content description for the Quick Settings tile
     */
    @NonNull
    public BroadcastTileIntentBuilder setContentDescription(@Nullable String contentDescription) {
        mContentDescription = contentDescription;
        return this;
    }

    /**
     * Set the resource ID of the icon to use for the Quick Settings tile. The icon should be no
     * taller than 28dp, but may be wider.
     */
    @NonNull
    public BroadcastTileIntentBuilder setIconResource(@DrawableRes int bitmapResource) {
        mIconResource = bitmapResource;
        return this;
    }

    /**
     * Sets the desired action of the Quick Settings tile for a click event using a PendingIntent.
     * Use this for handling private internal broadcasts.
     *
     * @param pi a PendingIntent that will be executed on a click event
     */
    @NonNull
    public BroadcastTileIntentBuilder setOnClickPendingIntent(@Nullable PendingIntent pi) {
        mOnClickIntent = pi;
        mOnClickUriString = null;
        return this;
    }

    /**
     * Sets the desired action of the Quick Settings tile for a click event using an Intent.
     * This will be sent as a broadcast by the system when the tile is long-clicked, so the receiver
     * of the event must be exported to receive this broadcast.
     * <p/>
     * This can also broadcast an Intent to other applications which expose a public API via
     * broadcast Intents.
     */
    @NonNull
    public BroadcastTileIntentBuilder setOnClickBroadcast(@Nullable Intent intent) {
        mOnClickUriString = uriStringFromIntent(intent);
        mOnClickIntent = null;
        return this;
    }

    /**
     * Sets the desired action of the Quick Settings tile for a long-click event using a
     * PendingIntent. Use this for handling private internal broadcasts.
     *
     * @param pi a PendingIntent that will be executed on a long-click event
     */
    @NonNull
    public BroadcastTileIntentBuilder setOnLongClickPendingIntent(@Nullable PendingIntent pi) {
        mOnLongClickIntent = pi;
        mOnLongClickUriString = null;
        return this;
    }

    /**
     * Sets the desired action of the Quick Settings tile for a long-click event using an Intent.
     * This will be sent as a broadcast by the system when the tile is long-clicked, so the receiver
     * of the event must be exported to receive this broadcast.
     * <p/>
     * This can also broadcast an Intent to other applications which expose a public API via
     * broadcast Intents.
     */
    @NonNull
    public BroadcastTileIntentBuilder setOnLongClickBroadcast(@Nullable Intent intent) {
        mOnLongClickUriString = uriStringFromIntent(intent);
        mOnLongClickIntent = null;
        return this;
    }

    /**
     * Sets whether or not the tile should be visible
     */
    @NonNull
    public BroadcastTileIntentBuilder setVisible(boolean visible) {
        mVisible = visible;
        return this;
    }

    /**
     * Creates the Intent that should be broadcast to update the state of the Quick Settings tile
     */
    @NonNull
    public Intent build() {
        final Intent intent = new Intent(mAction);
        intent.putExtra(EXTRA_VISIBLE, mVisible);
        intent.putExtra(EXTRA_CONTENT_DESCRIPTION, mContentDescription);
        intent.putExtra(EXTRA_LABEL, mLabel);
        intent.putExtra(EXTRA_ICON_RESOURCE_ID, mIconResource);
        intent.putExtra(EXTRA_ICON_PACKAGE, mIconPackage);
        intent.putExtra(EXTRA_ON_CLICK_PENDING_INTENT, mOnClickIntent);
        intent.putExtra(EXTRA_ON_CLICK_URI, mOnClickUriString);
        intent.putExtra(EXTRA_ON_LONG_CLICK_PENDING_INTENT, mOnLongClickIntent);
        intent.putExtra(EXTRA_ON_LONG_CLICK_URI, mOnLongClickUriString);
        return intent;
    }

    private boolean isValidAction(@Nullable String action) {
        return action != null && action.matches("[0-9A-Za-z.]+");
    }

    @Nullable
    private String uriStringFromIntent(@Nullable Intent source) {
        if (source != null) {
            return source.toUri(Intent.URI_INTENT_SCHEME);
        } else {
            return null;
        }
    }
}
