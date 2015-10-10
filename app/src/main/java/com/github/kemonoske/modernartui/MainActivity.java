package com.github.kemonoske.modernartui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.SeekBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final int X_CELL_COUNT = 5;
    private static final int Y_CELL_COUNT = 10;

    private Context mContext;
    private TilesView mTilesGridLayout;
    private SeekBar mSeekBar;
    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        FrameLayout tilesContainer = (FrameLayout) findViewById(R.id.tileContainer);
        mSeekBar = (SeekBar) findViewById(R.id.seekBar);
        mTilesGridLayout = new TilesView(mContext, 2, X_CELL_COUNT, Y_CELL_COUNT);
        tilesContainer.addView(mTilesGridLayout, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        mTilesGridLayout.setTiles(createTiles());

        mSeekBar.setOnSeekBarChangeListener(mSeekBarChangeListener);

    }

    private List<Tile> createTiles() {
        List<Tile> tiles = new ArrayList<>();
        for (int i = 0; i < Y_CELL_COUNT; i++) {
            int colsFilled;
            tiles.add(createTile(colsFilled = random.nextInt(X_CELL_COUNT - 1) + 1));
            tiles.add(createTile(X_CELL_COUNT - colsFilled));
        }
        tiles.get(random.nextInt(tiles.size() - 1)).setColor(Color.WHITE);
        return tiles;
    }

    private Tile createTile(int cols) {
        int progress = mSeekBar.getProgress() + 128;
        int color = Color.argb(255, random.nextInt(progress), random.nextInt(progress), random.nextInt(progress));
        return new Tile(mContext, 1, cols, color);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_more_information) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.dialog_title)
                    .setMessage(R.string.dialog_message)
                    .setPositiveButton(R.string.dialog_visit_moma, mVisitMomaBtnListener)
                    .setNegativeButton(R.string.dialog_not_now, null)
                    .create().show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private DialogInterface.OnClickListener mVisitMomaBtnListener =  new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            String url = "http://moma.org";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }
    };

    private SeekBar.OnSeekBarChangeListener mSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            List<Tile> tiles = mTilesGridLayout.getTiles();
            for (Tile tile : tiles) {
                if (Color.WHITE != tile.getColor()) {
                    progress = progress + 128;
                    tile.setColor(Color.argb(255, random.nextInt(progress), random.nextInt(progress), random.nextInt(progress)));
                }
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            ScaleAnimation fadeOut = new ScaleAnimation(1f, 0.8f, 1f, 0.8f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            fadeOut.setDuration(100);
            fadeOut.setFillAfter(true);
            mTilesGridLayout.startAnimation(fadeOut);
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

            ScaleAnimation fadeIn = new ScaleAnimation(0.8f, 1.0f, 0.8f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            fadeIn.setDuration(100);
            fadeIn.setFillAfter(true);
            mTilesGridLayout.startAnimation(fadeIn);
        }
    };
}
