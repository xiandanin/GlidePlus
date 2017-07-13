package com.dyhdyh.support.glide.gif.transform;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * 用于pl.droidsonroids.gif.Gifdrawable的圆形Transform
 * author  dengyuhan
 * created 2017/7/12 16:10
 */
public class GifDrawableCircleTransform extends OverrideSizeTransform {

    @Override
    public void onDraw(Canvas canvas, Paint paint, Bitmap buffer, int outWidth, int outHeight) {
        int width = buffer.getWidth();
        int height = buffer.getHeight();

        int size = Math.min(width, height);

        BitmapShader shader = new BitmapShader(buffer, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);

        float r = size / 2f;

        canvas.drawCircle(width / 2f, height / 2f, r, paint);
    }
}
