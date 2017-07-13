package com.dyhdyh.support.glide.transformations;

import android.content.Context;

import com.dyhdyh.support.glide.gif.transform.GifDrawableCircleTransform;

/**
 * author  dengyuhan
 * created 2017/7/13 14:49
 */
public class ImageWrapperCircleTransformation extends ImageWrapperTransformation {

    public ImageWrapperCircleTransformation(Context context) {
        super(new GifDrawableCircleTransform(), new CircleTransformation(context));
    }
}
