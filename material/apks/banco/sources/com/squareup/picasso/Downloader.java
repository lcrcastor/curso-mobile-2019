package com.squareup.picasso;

import android.graphics.Bitmap;
import android.net.Uri;
import java.io.IOException;
import java.io.InputStream;

public interface Downloader {

    public static class Response {
        final InputStream a;
        final Bitmap b;
        final boolean c;
        final long d;

        @Deprecated
        public Response(Bitmap bitmap, boolean z) {
            if (bitmap == null) {
                throw new IllegalArgumentException("Bitmap may not be null.");
            }
            this.a = null;
            this.b = bitmap;
            this.c = z;
            this.d = -1;
        }

        @Deprecated
        public Response(InputStream inputStream, boolean z) {
            this(inputStream, z, -1);
        }

        @Deprecated
        public Response(Bitmap bitmap, boolean z, long j) {
            this(bitmap, z);
        }

        public Response(InputStream inputStream, boolean z, long j) {
            if (inputStream == null) {
                throw new IllegalArgumentException("Stream may not be null.");
            }
            this.a = inputStream;
            this.b = null;
            this.c = z;
            this.d = j;
        }

        public InputStream getInputStream() {
            return this.a;
        }

        @Deprecated
        public Bitmap getBitmap() {
            return this.b;
        }

        public long getContentLength() {
            return this.d;
        }
    }

    public static class ResponseException extends IOException {
        final boolean a;
        final int b;

        public ResponseException(String str, int i, int i2) {
            super(str);
            this.a = NetworkPolicy.isOfflineOnly(i);
            this.b = i2;
        }
    }

    Response load(Uri uri, int i);

    void shutdown();
}
