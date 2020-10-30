package com.android.volley.toolbox;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.widget.ImageView.ScaleType;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Request.Priority;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyLog;

public class ImageRequest extends Request<Bitmap> {
    private static final Object f = new Object();
    private final Listener<Bitmap> a;
    private final Config b;
    private final int c;
    private final int d;
    private ScaleType e;

    public ImageRequest(String str, Listener<Bitmap> listener, int i, int i2, ScaleType scaleType, Config config, ErrorListener errorListener) {
        super(0, str, errorListener);
        setRetryPolicy(new DefaultRetryPolicy(1000, 2, 2.0f));
        this.a = listener;
        this.b = config;
        this.c = i;
        this.d = i2;
        this.e = scaleType;
    }

    @Deprecated
    public ImageRequest(String str, Listener<Bitmap> listener, int i, int i2, Config config, ErrorListener errorListener) {
        this(str, listener, i, i2, ScaleType.CENTER_INSIDE, config, errorListener);
    }

    public Priority getPriority() {
        return Priority.LOW;
    }

    private static int a(int i, int i2, int i3, int i4, ScaleType scaleType) {
        if (i == 0 && i2 == 0) {
            return i3;
        }
        if (scaleType == ScaleType.FIT_XY) {
            return i == 0 ? i3 : i;
        }
        if (i == 0) {
            return (int) (((double) i3) * (((double) i2) / ((double) i4)));
        } else if (i2 == 0) {
            return i;
        } else {
            double d2 = ((double) i4) / ((double) i3);
            if (scaleType == ScaleType.CENTER_CROP) {
                double d3 = (double) i2;
                if (((double) i) * d2 < d3) {
                    i = (int) (d3 / d2);
                }
                return i;
            }
            double d4 = (double) i2;
            if (((double) i) * d2 > d4) {
                i = (int) (d4 / d2);
            }
            return i;
        }
    }

    /* access modifiers changed from: protected */
    public Response<Bitmap> parseNetworkResponse(NetworkResponse networkResponse) {
        Response<Bitmap> a2;
        synchronized (f) {
            try {
                a2 = a(networkResponse);
            } catch (OutOfMemoryError e2) {
                VolleyLog.e("Caught OOM for %d byte image, url=%s", Integer.valueOf(networkResponse.data.length), getUrl());
                return Response.error(new ParseError((Throwable) e2));
            } catch (Throwable th) {
                throw th;
            }
        }
        return a2;
    }

    private Response<Bitmap> a(NetworkResponse networkResponse) {
        Bitmap bitmap;
        byte[] bArr = networkResponse.data;
        Options options = new Options();
        if (this.c == 0 && this.d == 0) {
            options.inPreferredConfig = this.b;
            bitmap = BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options);
        } else {
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options);
            int i = options.outWidth;
            int i2 = options.outHeight;
            int a2 = a(this.c, this.d, i, i2, this.e);
            int a3 = a(this.d, this.c, i2, i, this.e);
            options.inJustDecodeBounds = false;
            options.inSampleSize = a(i, i2, a2, a3);
            bitmap = BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options);
            if (bitmap != null && (bitmap.getWidth() > a2 || bitmap.getHeight() > a3)) {
                Bitmap createScaledBitmap = Bitmap.createScaledBitmap(bitmap, a2, a3, true);
                bitmap.recycle();
                bitmap = createScaledBitmap;
            }
        }
        if (bitmap == null) {
            return Response.error(new ParseError(networkResponse));
        }
        return Response.success(bitmap, HttpHeaderParser.parseCacheHeaders(networkResponse));
    }

    /* access modifiers changed from: protected */
    public void deliverResponse(Bitmap bitmap) {
        this.a.onResponse(bitmap);
    }

    static int a(int i, int i2, int i3, int i4) {
        double min = Math.min(((double) i) / ((double) i3), ((double) i2) / ((double) i4));
        float f2 = 1.0f;
        while (true) {
            float f3 = 2.0f * f2;
            if (((double) f3) > min) {
                return (int) f2;
            }
            f2 = f3;
        }
    }
}
