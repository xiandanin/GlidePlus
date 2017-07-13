package com.dyhdyh.support.glide.gif.transform;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import pl.droidsonroids.gif.transforms.Transform;

/**
 * author  dengyuhan
 * created 2017/7/13 14:24
 */
public abstract class OverrideSizeTransform implements Transform {
    public static final int ORIGINAL_WIDTH = 0;
    public static final int ORIGINAL_HEIGHT = 0;

    private int mOutWidth = ORIGINAL_WIDTH;
    private int mOutHeight = ORIGINAL_HEIGHT;

    @Override
    public void onBoundsChange(Rect bounds) {

    }

    @Override
    public void onDraw(Canvas canvas, Paint paint, Bitmap buffer) {
        this.onDraw(canvas, paint, buffer, mOutWidth, mOutHeight);
    }

    public OverrideSizeTransform setDrawSize(int outWidth, int outHeight) {
        this.mOutWidth = outWidth;
        this.mOutHeight = outHeight;
        return this;
    }

    public abstract void onDraw(Canvas canvas, Paint paint, Bitmap buffer, int outWidth, int outHeight);

}
