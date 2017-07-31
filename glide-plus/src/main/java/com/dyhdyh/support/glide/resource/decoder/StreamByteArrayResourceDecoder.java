package com.dyhdyh.support.glide.resource.decoder;

import android.util.Log;

import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.bytes.BytesResource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * author  dengyuhan
 * created 2017/7/13 15:02
 */
public class StreamByteArrayResourceDecoder implements ResourceDecoder<InputStream, byte[]> {
    private final String TAG = "StreamByteDecoder";

    @Override
    public Resource<byte[]> decode(InputStream source, int width, int height) throws IOException {
        return new BytesResource(inputStreamToBytes(source));
    }

    private byte[] inputStreamToBytes(InputStream is) {
        final int bufferSize = 16384;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream(bufferSize);
        try {
            int nRead;
            byte[] data = new byte[bufferSize];
            while ((nRead = is.read(data)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();
        } catch (IOException e) {
            Log.w(TAG, "Error reading data from stream", e);
        }
        //TODO the returned byte[] may be partial if an IOException was thrown from read
        return buffer.toByteArray();
    }

    @Override
    public String getId() {
        return getClass().getName();
    }
}
