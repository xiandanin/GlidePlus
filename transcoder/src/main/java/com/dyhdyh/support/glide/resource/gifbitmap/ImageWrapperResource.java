package com.dyhdyh.support.glide.resource.gifbitmap;

import android.graphics.Bitmap;

import com.bumptech.glide.load.engine.Resource;

import pl.droidsonroids.gif.GifDrawable;

/**
 * A resource that wraps an {@link ImageWrapper}.
 */
public class ImageWrapperResource implements Resource<ImageWrapper> {
    private final ImageWrapper data;

    public ImageWrapperResource(ImageWrapper data) {
        if (data == null) {
            throw new NullPointerException("Data must not be null");
        }
        this.data = data;
    }

    @Override
    public ImageWrapper get() {
        return data;
    }

    @Override
    public int getSize() {
        return data.getSize();
    }

    @Override
    public void recycle() {
        Resource<Bitmap> bitmapResource = data.getBitmapResource();
        if (bitmapResource != null) {
            bitmapResource.recycle();
        }
        Resource<GifDrawable> gifDataResource = data.getGifResource();
        if (gifDataResource != null) {
            gifDataResource.recycle();
        }
    }
}
