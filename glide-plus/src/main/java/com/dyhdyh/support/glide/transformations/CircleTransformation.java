package com.dyhdyh.support.glide.transformations;

import android.content.Context;

import com.dyhdyh.support.glide.gif.transform.GifDrawableCircleTransform;

/**
 * gif圆形 bitmap圆形
 * author  dengyuhan
 * created 2017/7/13 14:49
 */
public class CircleTransformation extends ImageWrapperTransformation {

    public CircleTransformation(Context context) {
        super(new GifDrawableCircleTransform(), new BitmapCircleTransformation(context));
    }
}
