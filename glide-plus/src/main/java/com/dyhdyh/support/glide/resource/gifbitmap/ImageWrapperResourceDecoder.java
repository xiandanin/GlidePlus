package com.dyhdyh.support.glide.resource.gifbitmap;

import android.graphics.Bitmap;

import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.model.ImageVideoWrapper;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;
import com.bumptech.glide.load.resource.bitmap.ImageHeaderParser;
import com.bumptech.glide.load.resource.bitmap.RecyclableBufferedInputStream;
import com.bumptech.glide.util.ByteArrayPool;

import java.io.IOException;
import java.io.InputStream;

import pl.droidsonroids.gif.GifDrawable;

/**
 * An com.bumptech.glide.load.ResourceDecoder that can decode either an {@link Bitmap} or an pl.droidsonroids.gif.GifDrawable
 * from an {@link InputStream} or a {@link android.os.ParcelFileDescriptor ParcelFileDescriptor}.
 */
public class ImageWrapperResourceDecoder implements ResourceDecoder<ImageVideoWrapper, ImageWrapper> {
    private static final ImageTypeParser DEFAULT_PARSER = new ImageTypeParser();
    private static final BufferedStreamFactory DEFAULT_STREAM_FACTORY = new BufferedStreamFactory();
    // 2048 is rather arbitrary, for most well formatted image types we only need 32 bytes.
    // Visible for testing.
    static final int MARK_LIMIT_BYTES = 2048;

    private final ResourceDecoder<ImageVideoWrapper, Bitmap> bitmapDecoder;
    private final ResourceDecoder<InputStream, GifDrawable> gifDecoder;
    private final BitmapPool bitmapPool;
    private final ImageTypeParser parser;
    private final BufferedStreamFactory streamFactory;
    private String id;

    public ImageWrapperResourceDecoder(ResourceDecoder<ImageVideoWrapper, Bitmap> bitmapDecoder,
                                       ResourceDecoder<InputStream, GifDrawable> gifDecoder, BitmapPool bitmapPool) {
        this(bitmapDecoder, gifDecoder, bitmapPool, DEFAULT_PARSER, DEFAULT_STREAM_FACTORY);
    }

    // Visible for testing.
    ImageWrapperResourceDecoder(ResourceDecoder<ImageVideoWrapper, Bitmap> bitmapDecoder,
                                ResourceDecoder<InputStream, GifDrawable> gifDecoder, BitmapPool bitmapPool, ImageTypeParser parser,
                                BufferedStreamFactory streamFactory) {
        this.bitmapDecoder = bitmapDecoder;
        this.gifDecoder = gifDecoder;
        this.bitmapPool = bitmapPool;
        this.parser = parser;
        this.streamFactory = streamFactory;
    }

    @SuppressWarnings("resource")
    // @see ResourceDecoder.decode
    @Override
    public Resource<ImageWrapper> decode(ImageVideoWrapper source, int width, int height) throws IOException {
        ByteArrayPool pool = ByteArrayPool.get();
        byte[] tempBytes = pool.getBytes();

        ImageWrapper wrapper = null;
        try {
            wrapper = decode(source, width, height, tempBytes);
        } finally {
            pool.releaseBytes(tempBytes);
        }
        return wrapper != null ? new ImageWrapperResource(wrapper) : null;
    }

    private ImageWrapper decode(ImageVideoWrapper source, int width, int height, byte[] bytes) throws IOException {
        final ImageWrapper result;
        if (source.getStream() != null) {
            result = decodeStream(source, width, height, bytes);
        } else {
            result = decodeBitmapWrapper(source, width, height);
        }
        return result;
    }

    private ImageWrapper decodeStream(ImageVideoWrapper source, int width, int height, byte[] bytes)
            throws IOException {
        InputStream bis = streamFactory.build(source.getStream(), bytes);
        bis.mark(MARK_LIMIT_BYTES);
        ImageHeaderParser.ImageType type = parser.parse(bis);
        bis.reset();

        ImageWrapper result = null;
        if (type == ImageHeaderParser.ImageType.GIF) {
            result = decodeGifWrapper(bis, width, height);
        }
        // Decoding the gif may fail even if the type matches.
        if (result == null) {
            // We can only reset the buffered InputStream, so to start from the beginning of the stream, we need to
            // pass in a new source containing the buffered stream rather than the original stream.
            ImageVideoWrapper forBitmapDecoder = new ImageVideoWrapper(bis, source.getFileDescriptor());
            result = decodeBitmapWrapper(forBitmapDecoder, width, height);
        }
        return result;
    }

    private ImageWrapper decodeGifWrapper(InputStream bis, int width, int height) throws IOException {
        ImageWrapper result = null;
        Resource<GifDrawable> gifResource = gifDecoder.decode(bis, width, height);
        if (gifResource != null) {
            GifDrawable drawable = gifResource.get();
            // We can more efficiently hold Bitmaps in memory, so for static GIFs, try to return Bitmaps
            // instead. Returning a Bitmap incurs the cost of allocating the GifDrawable as well as the normal
            // Bitmap allocation, but since we can encode the Bitmap out as a JPEG, future decodes will be
            // efficient.
            if (drawable.getNumberOfFrames() > 1) {
                result = new ImageWrapper(null /*bitmapResource*/, gifResource);
            } else {
                Resource<Bitmap> bitmapResource = new BitmapResource(drawable.getCurrentFrame(), bitmapPool);
                result = new ImageWrapper(bitmapResource, null /*gifResource*/);
            }
        }
        return result;
    }

    private ImageWrapper decodeBitmapWrapper(ImageVideoWrapper toDecode, int width, int height) throws IOException {
        ImageWrapper result = null;

        Resource<Bitmap> bitmapResource = bitmapDecoder.decode(toDecode, width, height);
        if (bitmapResource != null) {
            result = new ImageWrapper(bitmapResource, null);
        }

        return result;
    }

    @Override
    public String getId() {
        if (id == null) {
            id = gifDecoder.getId() + bitmapDecoder.getId();
        }
        return id;
    }

    // Visible for testing.
    static class BufferedStreamFactory {
        public InputStream build(InputStream is, byte[] buffer) {
            return new RecyclableBufferedInputStream(is, buffer);
        }
    }

    // Visible for testing.
    static class ImageTypeParser {
        public ImageHeaderParser.ImageType parse(InputStream is) throws IOException {
            return new ImageHeaderParser(is).getType();
        }
    }
}
