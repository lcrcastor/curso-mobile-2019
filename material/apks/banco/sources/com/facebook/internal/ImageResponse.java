package com.facebook.internal;

import android.graphics.Bitmap;

public class ImageResponse {
    private ImageRequest a;
    private Exception b;
    private boolean c;
    private Bitmap d;

    ImageResponse(ImageRequest imageRequest, Exception exc, boolean z, Bitmap bitmap) {
        this.a = imageRequest;
        this.b = exc;
        this.d = bitmap;
        this.c = z;
    }

    public ImageRequest getRequest() {
        return this.a;
    }

    public Exception getError() {
        return this.b;
    }

    public Bitmap getBitmap() {
        return this.d;
    }

    public boolean isCachedRedirect() {
        return this.c;
    }
}
