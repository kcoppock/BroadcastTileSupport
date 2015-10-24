# BroadcastTileSupport
If you've unlocked the System UI Tuner on one of your devices running Android Marshmallow (6.0), you've probably noticed the "Broadcast Tile" option, and wondered what it's useful for. I did some digging through the source, and found the general gist of the tile. It's used for registering a custom tile that can have its icon and label configured, and also send either PendingIntent or Intent broadcasts upon a click or a long-click of the tile.

## Configuring a custom tile

1. Enable the System UI Tuner. If you haven't done it yet, press and hold the Settings icon in the notification shade for about eight seconds. The icon should change to show a wrench next to the gear. You'll then see a "System UI Tuner" option at the end of Settings.

2. Add a Broadcast Tile through the System UI Tuner. If you're using the sample app provided in this repo, set the action to "com.kcoppock.CUSTOMTILE". You won't see the tile in your notification shade after you leave settings -- that's because it's not visible by default.

3. Run the sample app and select "Show Tile". 

4. You now have a custom Quick Settings tile! The sample app configures it to show a Toast on a click event (dismiss the shade after you click, since the toast is under the shade), and to show a simple notification on a long click.

## Using the library

Everything you need to configure your own custom tile can be provided by the BroadcastTileIntentBuilder class. Simply construct a new instance of the builder, using the action used when you added the tile through System Ui Tuner (note: this action must consist only of alphanumeric characters and periods) and configure any other behavior as desired. For example:

```java
Intent tileConfigurationIntent = new BroadcastTileIntentBuilder(context, "com.kcoppock.CUSTOMTILE")
        .setLabel("My Tile")
        .setIcon(R.drawable.my_tile_icon)
        .setOnClickBroadcast(new Intent("my.custom.action"))
        .build();
        
context.sendBroadcast(tileConfigurationIntent);
```
