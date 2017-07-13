package com.dyhdyh.support.glide.gif.resource;

import com.bumptech.glide.load.engine.Resource;

import pl.droidsonroids.gif.GifDrawable;

/**
 * author  dengyuhan
 * created 2017/7/13 14:58
 */
public class GifDrawableResource implements Resource<GifDrawable> {
    private GifDrawable drawable;

    public GifDrawableResource(GifDrawable gifDrawable) {
        this.drawable = gifDrawable;
    }

    @Override
    public GifDrawable get() {
        return drawable;
    }

    @Override
    public int getSize() {
        return (int) drawable.getInputSourceByteCount();
    }

    @Override
    public void recycle() {
        drawable.stop();
        drawable.recycle();
    }

}
