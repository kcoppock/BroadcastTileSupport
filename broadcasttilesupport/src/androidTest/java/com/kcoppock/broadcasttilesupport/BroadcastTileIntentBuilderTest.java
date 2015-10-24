package com.kcoppock.broadcasttilesupport;

import android.app.PendingIntent;
import android.content.Intent;
import android.test.AndroidTestCase;

public class BroadcastTileIntentBuilderTest extends AndroidTestCase {
    public void testConstructionWithValidAction() {
        BroadcastTileIntentBuilder builder;

        try {
            builder = new BroadcastTileIntentBuilder(getContext(), "123.abc");
        } catch (IllegalArgumentException unexpected) {
            fail("123.abc is a valid action.");
            return;
        }

        assertEquals("123.abc", builder.build().getAction());
    }

    public void testConstructionWithInvalidAction() {
        try {
            new BroadcastTileIntentBuilder(getContext(), "android.intent.ACTION_VIEW");
            fail("Underscores are not supported for broadcast tiles.");
        } catch (IllegalArgumentException expected) {
            // Expected
        }
    }

    public void testSetLabel() {
        Intent intent = new BroadcastTileIntentBuilder(getContext(), "123")
                .setLabel("Testing")
                .build();

        assertEquals("setLabel() should set the 'label' extra",
                "Testing", intent.getStringExtra("label"));

        intent = new BroadcastTileIntentBuilder(getContext(), "123")
                .setLabel(null)
                .build();

        assertNull("A null label should be null in the bundle",
                intent.getStringExtra("label"));
    }

    public void testSetContentDescription() {
        Intent intent = new BroadcastTileIntentBuilder(getContext(), "123")
                .setContentDescription("Testing")
                .build();

        assertEquals("setContentDescription() should set the 'contentDescription' extra",
                "Testing", intent.getStringExtra("contentDescription"));

        intent = new BroadcastTileIntentBuilder(getContext(), "123")
                .setContentDescription(null)
                .build();

        assertNull("A null content description should be put into the bundle as null",
                intent.getStringExtra("contentDescription"));
    }

    public void testSetIconResource() {
        Intent intent = new BroadcastTileIntentBuilder(getContext(), "123")
                .setIconResource(android.R.drawable.btn_star)
                .build();

        assertEquals("setIconResource() should capture the calling package name",
                getContext().getPackageName(), intent.getStringExtra("iconPackage"));
        assertEquals("setIconResource() should capture the resource ID",
                android.R.drawable.btn_star, intent.getIntExtra("iconId", 0));
    }

    public void testSetOnClickPendingIntent() {
        Intent viewIntent = new Intent(Intent.ACTION_VIEW);
        PendingIntent onClick = PendingIntent.getBroadcast(getContext(), 0,
                viewIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        Intent intent = new BroadcastTileIntentBuilder(getContext(), "123")
                .setOnClickBroadcast(viewIntent)
                .setOnClickPendingIntent(onClick)
                .build();

        assertEquals("setOnClickPendingIntent() should assign the 'onClick' extra",
                onClick, intent.getParcelableExtra("onClick"));

        assertNull("Setting the PendingIntent should overwrite the broadcast",
                intent.getStringExtra("onClickUri"));
    }

    public void testSetOnClickBroadcast() {
        Intent broadcast = new Intent(Intent.ACTION_VIEW);

        PendingIntent toOverwrite = PendingIntent.getBroadcast(getContext(), 0,
                broadcast, PendingIntent.FLAG_CANCEL_CURRENT);

        Intent intent = new BroadcastTileIntentBuilder(getContext(), "123")
                .setOnClickPendingIntent(toOverwrite)
                .setOnClickBroadcast(broadcast)
                .build();

        assertEquals("setOnClickBroadcast() should assign the 'onClickUri' extra with the " +
                        "String value of the passed intent (using URI_INTENT_SCHEME)",
                broadcast.toUri(Intent.URI_INTENT_SCHEME), intent.getStringExtra("onClickUri"));

        assertNull("Setting the broadcast should overwrite the PendingIntent",
                intent.getParcelableExtra("onClick"));
    }

    public void testSetOnLongClickPendingIntent() {
        Intent viewAction = new Intent(Intent.ACTION_VIEW);

        PendingIntent onLongClick = PendingIntent.getBroadcast(getContext(), 0,
                viewAction, PendingIntent.FLAG_CANCEL_CURRENT);

        Intent intent = new BroadcastTileIntentBuilder(getContext(), "123")
                .setOnLongClickBroadcast(viewAction)
                .setOnLongClickPendingIntent(onLongClick)
                .build();

        assertEquals("setOnLongClickPendingIntent() should assign the 'onLongClick' extra",
                onLongClick, intent.getParcelableExtra("onLongClick"));

        assertNull("Setting the PendingIntent should overwrite the broadcast",
                intent.getStringExtra("onLongClickUri"));
    }

    public void testSetOnLongClickBroadcast() {
        Intent broadcast = new Intent(Intent.ACTION_VIEW);

        PendingIntent toOverwrite = PendingIntent.getBroadcast(getContext(), 0,
                broadcast, PendingIntent.FLAG_CANCEL_CURRENT);

        Intent intent = new BroadcastTileIntentBuilder(getContext(), "123")
                .setOnLongClickPendingIntent(toOverwrite)
                .setOnLongClickBroadcast(broadcast)
                .build();

        assertEquals("setOnLongClickBroadcast() should assign the 'onLongClickUri' extra with the" +
                        " String value of the passed intent (using URI_INTENT_SCHEME)",
                broadcast.toUri(Intent.URI_INTENT_SCHEME), intent.getStringExtra("onLongClickUri"));

        assertNull("Setting the broadcast should overwrite the PendingIntent",
                intent.getParcelableExtra("onLongClick"));
    }

    public void testSetVisible() {
        Intent intent = new BroadcastTileIntentBuilder(getContext(), "123")
                .setVisible(true)
                .build();

        assertEquals("setVisibility() should add the 'visible' extra",
                true, intent.getBooleanExtra("visible", false));

        intent = new BroadcastTileIntentBuilder(getContext(), "123")
                .setVisible(false)
                .build();

        assertEquals("setVisibility() should add the 'visible' extra",
                false, intent.getBooleanExtra("visible", true));
    }
}