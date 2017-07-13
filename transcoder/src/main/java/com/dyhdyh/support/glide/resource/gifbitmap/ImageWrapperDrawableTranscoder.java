package com.dyhdyh.support.glide.resource.gifbitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.load.resource.transcode.GlideBitmapDrawableTranscoder;
import com.bumptech.glide.load.resource.transcode.ResourceTranscoder;

/**
 * An {@link ResourceTranscoder} that can transcode either an
 * {@link Bitmap} or an {@link pl.droidsonroids.gif.GifDrawable} into an
 * {@link android.graphics.drawable.Drawable}.
 */
public class ImageWrapperDrawableTranscoder implements ResourceTranscoder<ImageWrapper, Drawable> {
    private final ResourceTranscoder<Bitmap, GlideBitmapDrawable> bitmapDrawableResourceTranscoder;

    public ImageWrapperDrawableTranscoder(Context context) {
        this(new GlideBitmapDrawableTranscoder(context));
    }

    public ImageWrapperDrawableTranscoder(
            ResourceTranscoder<Bitmap, GlideBitmapDrawable> bitmapDrawableResourceTranscoder) {
        this.bitmapDrawableResourceTranscoder = bitmapDrawableResourceTranscoder;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Resource<Drawable> transcode(Resource<ImageWrapper> toTranscode) {
        ImageWrapper gifBitmap = toTranscode.get();
        Resource<Bitmap> bitmapResource = gifBitmap.getBitmapResource();

        final Resource<? extends Drawable> result;
        if (bitmapResource != null) {
            result = bitmapDrawableResourceTranscoder.transcode(bitmapResource);
        } else {
            result = gifBitmap.getGifResource();
        }
        // This is unchecked but always safe, anything that extends a Drawable can be safely cast to a Drawable.
        return (Resource<Drawable>) result;
    }

    @Override
    public String getId() {
        return getClass().getName();
    }
}
