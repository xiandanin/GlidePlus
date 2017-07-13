package com.dyhdyh.support.glide.transformations;

import android.graphics.Bitmap;

import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.dyhdyh.support.glide.gif.transform.OverrideSizeTransform;
import com.dyhdyh.support.glide.resource.gifbitmap.ImageWrapper;
import com.dyhdyh.support.glide.resource.gifbitmap.ImageWrapperResource;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.transforms.Transform;

/**
 * author  dengyuhan
 * created 2017/7/13 14:38
 */
public abstract class ImageWrapperTransformation implements Transformation<ImageWrapper> {
    private Transform mGifTransform;
    private Transformation mBitmapTransformation;

    public ImageWrapperTransformation(Transform gifTransform, Transformation bitmapTransformation) {
        this.mGifTransform = gifTransform;
        this.mBitmapTransformation = bitmapTransformation;
    }

    @Override
    public Resource<ImageWrapper> transform(Resource<ImageWrapper> resource, int outWidth, int outHeight) {
        ImageWrapper gifBitmapWrapper = resource.get();
        Resource<Bitmap> bitmapResource = gifBitmapWrapper.getBitmapResource();
        if (bitmapResource != null) {
            Resource<Bitmap> transform = mBitmapTransformation.transform(bitmapResource, outWidth, outHeight);
            return new ImageWrapperResource(new ImageWrapper(transform, gifBitmapWrapper.getGifResource()));
        }

        Resource<GifDrawable> gifResource = gifBitmapWrapper.getGifResource();
        if (gifResource != null) {
            if (mGifTransform instanceof OverrideSizeTransform) {
                ((OverrideSizeTransform) mGifTransform).setDrawSize(outWidth, outHeight);
            }
            gifResource.get().setTransform(mGifTransform);
        }
        return resource;
    }

    @Override
    public String getId() {
        return getClass().getName();
    }
}
