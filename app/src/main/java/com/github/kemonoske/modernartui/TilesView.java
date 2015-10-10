package com.github.kemonoske.modernartui;

import android.content.Context;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;

import java.util.List;

/**
 * Created by Bostanica Ion on 10/3/2015.
 */
public class TilesView extends LinearLayout {

    private int xCellCount;
    private int yCellCount;

    private int colCount;

    private int cellWidth;
    private int cellHeight;

    private List<Tile> tiles;


    public TilesView(Context context, int colCount, int xCellCount, int yCellCount) {
        super(context);
        setOrientation(VERTICAL);
        this.xCellCount = xCellCount;
        this.yCellCount = yCellCount;
        this.colCount = colCount;
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus) {
            cellWidth = getWidth() / xCellCount;
            cellHeight = getHeight() / yCellCount;
            post(new Runnable() {
                @Override
                public void run() {
                    redraw();
                }
            });
        }
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(List<Tile> tiles) {
        this.tiles = tiles;
        redraw();
    }

    private void redraw() {
        removeAllViews();
        int rowCount = tiles.size() / colCount + tiles.size() % colCount;
        for (int i = 0; i < rowCount; i++) {
            LinearLayout row = new LinearLayout(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(params);
            row.setOrientation(HORIZONTAL);
            for (int j = 0; j < colCount; j++) {
                int index = i * colCount + j;
                if (index < tiles.size()) {
                    Tile tile = tiles.get(index);
                    row.addView(tile, cellWidth * tile.getCols(), cellHeight * tile.getRows());
                }
            }
            addView(row);
        }
    }

    public int getyCellCount() {
        return yCellCount;
    }

    public void setyCellCount(int yCellCount) {
        this.yCellCount = yCellCount;
    }

    @Override
    public void removeAllViews() {
        for (Tile tile : tiles) {
            ViewParent parent = tile.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(tile);
            }
        }
        super.removeAllViews();
    }
}
