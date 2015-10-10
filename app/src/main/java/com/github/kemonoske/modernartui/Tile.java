package com.github.kemonoske.modernartui;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by Bostanica Ion on 10/2/2015.
 */
public class Tile extends LinearLayout {

    private int rows;
    private int cols;

    private int color;

    public Tile(Context context, int rows, int cols, int color) {
        super(context);
        this.rows = rows;
        this.cols = cols;
        this.color = color;
        setBackgroundColor(color);
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
        setBackgroundColor(color);
    }
}
