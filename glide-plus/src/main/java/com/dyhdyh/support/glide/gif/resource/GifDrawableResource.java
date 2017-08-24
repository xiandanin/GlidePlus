package com.dyhdyh.support.glide.gif.resource;

import com.bumptech.glide.load.engine.Resource;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.transforms.Transform;

/**
 * author  dengyuhan
 * created 2017/7/13 14:58
 */
public class GifDrawableResource implements Resource<GifDrawable> {
    private GifDrawable drawable;
    private byte[] data;
    private Transform gifTransform;

    public GifDrawableResource(GifDrawable gifDrawable, byte[] data) {
        this.drawable = gifDrawable;
        this.data = data;
    }

    @Override
    public GifDrawable get() {
        try {
            GifDrawable newDrawable = new GifDrawable(data);
            newDrawable.setLoopCount(drawable.getLoopCount());
            newDrawable.setTransform(gifTransform);
            return newDrawable;
        } catch (Exception e) {
            e.printStackTrace();
            return drawable;
        }
    }

    public void setGifTransform(Transform gifTransform) {
        this.gifTransform = gifTransform;
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
