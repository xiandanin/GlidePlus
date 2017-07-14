package com.dyhdyh.support.glide;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.model.stream.StreamStringLoader;
import com.bumptech.glide.request.animation.ViewPropertyAnimation;
import com.dyhdyh.support.glide.animator.DrawableCrossFadeAnimator;
import com.dyhdyh.support.glide.resource.gifbitmap.ImageWrapper;
import com.dyhdyh.support.glide.resource.gifbitmap.ImageWrapperDrawableTranscoder;
import com.dyhdyh.support.glide.resource.gifbitmap.ImageWrapperFileToStreamDecoder;
import com.dyhdyh.support.glide.resource.gifbitmap.ImageWrapperStreamResourceDecoder;
import com.dyhdyh.support.glide.transformations.ImageWrapperCircleTransformation;
import com.dyhdyh.support.glide.transformations.ImageWrapperTransformation;

import java.io.InputStream;

/**
 * author  dengyuhan
 * created 2017/7/13 16:17
 */
public class GlideSupport {

    private GenericRequestBuilder<String, InputStream, ImageWrapper, Drawable> mRequestBuilder;
    private Context mContext;

    public GlideSupport(Context context) {
        this.mContext = context;
    }

    public static GlideSupport with(Context context) {
        return new GlideSupport(context);
    }

    /**
     * Gif增强
     *
     * @return
     */
    public GlideSupport gifEnhancement() {
        mRequestBuilder = Glide.with(mContext)
                .using(new StreamStringLoader(mContext), InputStream.class)
                .from(String.class)
                .as(ImageWrapper.class)
                .transcode(new ImageWrapperDrawableTranscoder(mContext), Drawable.class)
                .decoder(new ImageWrapperStreamResourceDecoder(mContext))
                .cacheDecoder(new ImageWrapperFileToStreamDecoder(mContext))
                .sourceEncoder(new StreamEncoder())
                .animate(new DrawableCrossFadeAnimator())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE);
        return this;
    }


    /**
     * 淡出淡出动画
     *
     * @return
     */
    public GlideSupport crossFade() {
        mRequestBuilder.animate(new DrawableCrossFadeAnimator());
        return this;
    }

    /**
     * 圆形
     *
     * @return
     */
    public GlideSupport circle() {
        mRequestBuilder.transform(new ImageWrapperCircleTransformation(mContext));
        return this;
    }


    public GlideSupport transform(ImageWrapperTransformation transformation) {
        mRequestBuilder.transform(transformation);
        return this;
    }


    public GlideSupport animate(ViewPropertyAnimation.Animator animator) {
        mRequestBuilder.animate(animator);
        return this;
    }


    public GenericRequestBuilder<String, InputStream, ImageWrapper, Drawable> glide() {
        return mRequestBuilder;
    }
}
