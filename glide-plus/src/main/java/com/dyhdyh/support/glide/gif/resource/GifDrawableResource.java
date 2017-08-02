package com.dyhdyh.support.glide.gif.resource;

import com.bumptech.glide.load.engine.Resource;

import pl.droidsonroids.gif.GifDrawable;

/**
 * author  dengyuhan
 * created 2017/7/13 14:58
 */
public class GifDrawableResource implements Resource<GifDrawable> {
    private GifDrawable drawable;
    private byte[] data;

    public GifDrawableResource(GifDrawable gifDrawable, byte[] data) {
        this.drawable = gifDrawable;
        this.data = data;
    }

    @Override
    public GifDrawable get() {
        try {
            return new GifDrawable(data);
        } catch (Exception e) {
            e.printStackTrace();
            return drawable;
        }
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
