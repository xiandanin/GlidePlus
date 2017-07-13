package com.dyhdyh.support.glide.resource.gifbitmap;

import android.content.Context;

import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;

import java.io.InputStream;

/**
 * author  dengyuhan
 * created 2017/7/13 15:21
 */
public class ImageWrapperFileToStreamDecoder extends FileToStreamDecoder<ImageWrapper> {
    public ImageWrapperFileToStreamDecoder(Context context) {
        this(new ImageWrapperStreamResourceDecoder(context));
    }

    public ImageWrapperFileToStreamDecoder(ResourceDecoder<InputStream, ImageWrapper> streamDecoder) {
        super(streamDecoder);
    }
}
