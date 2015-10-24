package com.kcoppock.broadcasttile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onShowTile(View v) {
        new CustomTileHelper(v.getContext()).showTile();
    }

    public void onHideTile(View v) {
        new CustomTileHelper(v.getContext()).hideTile();
    }
}
