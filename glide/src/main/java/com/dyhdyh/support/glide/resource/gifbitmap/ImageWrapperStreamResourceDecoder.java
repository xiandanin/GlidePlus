package com.dyhdyh.support.glide.resource.gifbitmap;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.model.ImageVideoWrapper;
import com.bumptech.glide.load.resource.bitmap.FileDescriptorBitmapDecoder;
import com.bumptech.glide.load.resource.bitmap.ImageVideoBitmapDecoder;
import com.bumptech.glide.load.resource.bitmap.StreamBitmapDecoder;
import com.dyhdyh.support.glide.gif.resource.GifResourceDecoder;

import java.io.IOException;
import java.io.InputStream;

/**
 * A com.bumptech.glide.load.ResourceDecoder that can decode an
 * {@link ImageWrapper} from {@link InputStream} data.
 */
public class ImageWrapperStreamResourceDecoder implements ResourceDecoder<InputStream, ImageWrapper> {
    private final ResourceDecoder<ImageVideoWrapper, ImageWrapper> gifBitmapDecoder;

    public ImageWrapperStreamResourceDecoder(Context context) {
        StreamBitmapDecoder streamBitmapDecoder = new StreamBitmapDecoder(context);
        FileDescriptorBitmapDecoder fileDescriptorBitmapDecoder = new FileDescriptorBitmapDecoder(context);
        ImageVideoBitmapDecoder imageVideoBitmapDecoder = new ImageVideoBitmapDecoder(streamBitmapDecoder, fileDescriptorBitmapDecoder);
        GifResourceDecoder gifResourceDecoder = new GifResourceDecoder();
        BitmapPool bitmapPool = Glide.get(context).getBitmapPool();
        this.gifBitmapDecoder = new ImageWrapperResourceDecoder(imageVideoBitmapDecoder, gifResourceDecoder, bitmapPool);
    }

    public ImageWrapperStreamResourceDecoder(
            ResourceDecoder<ImageVideoWrapper, ImageWrapper> gifBitmapDecoder) {
        this.gifBitmapDecoder = gifBitmapDecoder;
    }

    @Override
    public Resource<ImageWrapper> decode(InputStream source, int width, int height) throws IOException {
        return gifBitmapDecoder.decode(new ImageVideoWrapper(source, null), width, height);
    }

    @Override
    public String getId() {
        return gifBitmapDecoder.getId();
    }
}
