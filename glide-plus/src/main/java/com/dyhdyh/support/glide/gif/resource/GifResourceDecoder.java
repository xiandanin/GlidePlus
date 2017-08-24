package com.dyhdyh.support.glide.gif.resource;

import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;
import com.dyhdyh.support.glide.resource.decoder.StreamByteArrayResourceDecoder;

import java.io.IOException;
import java.io.InputStream;

import pl.droidsonroids.gif.GifDrawable;

/**
 * author  dengyuhan
 * created 2017/7/13 12:07
 */
public class GifResourceDecoder implements ResourceDecoder<InputStream, GifDrawable> {

    private StreamByteArrayResourceDecoder mByteDecoder = new StreamByteArrayResourceDecoder();

    @Override
    public Resource<GifDrawable> decode(InputStream source, int width, int height) throws IOException {
        byte[] bytes = mByteDecoder.decode(source, width, height).get();
        GifDrawable gifDrawable = new GifDrawable(bytes);
        gifDrawable.setLoopCount(0);
        return new GifDrawableResource(gifDrawable, bytes);
    }

    @Override
    public String getId() {
        return getClass().getName();
    }
}
