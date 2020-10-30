package com.squareup.picasso;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory.Options;
import android.net.NetworkInfo;
import com.squareup.picasso.Picasso.LoadedFrom;
import java.io.InputStream;

public abstract class RequestHandler {

    public static final class Result {
        private final LoadedFrom a;
        private final Bitmap b;
        private final InputStream c;
        private final int d;

        public Result(Bitmap bitmap, LoadedFrom loadedFrom) {
            this((Bitmap) Utils.a(bitmap, "bitmap == null"), null, loadedFrom, 0);
        }

        public Result(InputStream inputStream, LoadedFrom loadedFrom) {
            this(null, (InputStream) Utils.a(inputStream, "stream == null"), loadedFrom, 0);
        }

        Result(Bitmap bitmap, InputStream inputStream, LoadedFrom loadedFrom, int i) {
            boolean z = false;
            boolean z2 = bitmap != null;
            if (inputStream != null) {
                z = true;
            }
            if (!(z ^ z2)) {
                throw new AssertionError();
            }
            this.b = bitmap;
            this.c = inputStream;
            this.a = (LoadedFrom) Utils.a(loadedFrom, "loadedFrom == null");
            this.d = i;
        }

        public Bitmap getBitmap() {
            return this.b;
        }

        public InputStream getStream() {
            return this.c;
        }

        public LoadedFrom getLoadedFrom() {
            return this.a;
        }

        /* access modifiers changed from: 0000 */
        public int a() {
            return this.d;
        }
    }

    /* access modifiers changed from: 0000 */
    public int a() {
        return 0;
    }

    /* access modifiers changed from: 0000 */
    public boolean a(boolean z, NetworkInfo networkInfo) {
        return false;
    }

    /* access modifiers changed from: 0000 */
    public boolean b() {
        return false;
    }

    public abstract boolean canHandleRequest(Request request);

    public abstract Result load(Request request, int i);

    static Options b(Request request) {
        boolean hasSize = request.hasSize();
        boolean z = request.config != null;
        Options options = null;
        if (hasSize || z) {
            options = new Options();
            options.inJustDecodeBounds = hasSize;
            if (z) {
                options.inPreferredConfig = request.config;
            }
        }
        return options;
    }

    static boolean a(Options options) {
        return options != null && options.inJustDecodeBounds;
    }

    static void a(int i, int i2, Options options, Request request) {
        a(i, i2, options.outWidth, options.outHeight, options, request);
    }

    static void a(int i, int i2, int i3, int i4, Options options, Request request) {
        int i5;
        if (i4 <= i2 && i3 <= i) {
            i5 = 1;
        } else if (i2 == 0) {
            i5 = (int) Math.floor((double) (((float) i3) / ((float) i)));
        } else if (i == 0) {
            i5 = (int) Math.floor((double) (((float) i4) / ((float) i2)));
        } else {
            int floor = (int) Math.floor((double) (((float) i4) / ((float) i2)));
            int floor2 = (int) Math.floor((double) (((float) i3) / ((float) i)));
            i5 = request.centerInside ? Math.max(floor, floor2) : Math.min(floor, floor2);
        }
        options.inSampleSize = i5;
        options.inJustDecodeBounds = false;
    }
}
