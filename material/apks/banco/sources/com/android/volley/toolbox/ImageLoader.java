package com.android.volley.toolbox;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class ImageLoader {
    private final RequestQueue a;
    private int b = 100;
    private final ImageCache c;
    /* access modifiers changed from: private */
    public final HashMap<String, BatchedImageRequest> d = new HashMap<>();
    /* access modifiers changed from: private */
    public final HashMap<String, BatchedImageRequest> e = new HashMap<>();
    private final Handler f = new Handler(Looper.getMainLooper());
    /* access modifiers changed from: private */
    public Runnable g;

    class BatchedImageRequest {
        private final Request<?> b;
        /* access modifiers changed from: private */
        public Bitmap c;
        private VolleyError d;
        /* access modifiers changed from: private */
        public final LinkedList<ImageContainer> e = new LinkedList<>();

        public BatchedImageRequest(Request<?> request, ImageContainer imageContainer) {
            this.b = request;
            this.e.add(imageContainer);
        }

        public void a(VolleyError volleyError) {
            this.d = volleyError;
        }

        public VolleyError a() {
            return this.d;
        }

        public void a(ImageContainer imageContainer) {
            this.e.add(imageContainer);
        }

        public boolean b(ImageContainer imageContainer) {
            this.e.remove(imageContainer);
            if (this.e.size() != 0) {
                return false;
            }
            this.b.cancel();
            return true;
        }
    }

    public interface ImageCache {
        Bitmap getBitmap(String str);

        void putBitmap(String str, Bitmap bitmap);
    }

    public class ImageContainer {
        /* access modifiers changed from: private */
        public Bitmap b;
        /* access modifiers changed from: private */
        public final ImageListener c;
        private final String d;
        private final String e;

        public ImageContainer(Bitmap bitmap, String str, String str2, ImageListener imageListener) {
            this.b = bitmap;
            this.e = str;
            this.d = str2;
            this.c = imageListener;
        }

        public void cancelRequest() {
            if (this.c != null) {
                BatchedImageRequest batchedImageRequest = (BatchedImageRequest) ImageLoader.this.d.get(this.d);
                if (batchedImageRequest == null) {
                    BatchedImageRequest batchedImageRequest2 = (BatchedImageRequest) ImageLoader.this.e.get(this.d);
                    if (batchedImageRequest2 != null) {
                        batchedImageRequest2.b(this);
                        if (batchedImageRequest2.e.size() == 0) {
                            ImageLoader.this.e.remove(this.d);
                        }
                    }
                } else if (batchedImageRequest.b(this)) {
                    ImageLoader.this.d.remove(this.d);
                }
            }
        }

        public Bitmap getBitmap() {
            return this.b;
        }

        public String getRequestUrl() {
            return this.e;
        }
    }

    public interface ImageListener extends ErrorListener {
        void onResponse(ImageContainer imageContainer, boolean z);
    }

    public ImageLoader(RequestQueue requestQueue, ImageCache imageCache) {
        this.a = requestQueue;
        this.c = imageCache;
    }

    public static ImageListener getImageListener(final ImageView imageView, final int i, final int i2) {
        return new ImageListener() {
            public void onErrorResponse(VolleyError volleyError) {
                if (i2 != 0) {
                    imageView.setImageResource(i2);
                }
            }

            public void onResponse(ImageContainer imageContainer, boolean z) {
                if (imageContainer.getBitmap() != null) {
                    imageView.setImageBitmap(imageContainer.getBitmap());
                } else if (i != 0) {
                    imageView.setImageResource(i);
                }
            }
        };
    }

    public boolean isCached(String str, int i, int i2) {
        return isCached(str, i, i2, ScaleType.CENTER_INSIDE);
    }

    public boolean isCached(String str, int i, int i2, ScaleType scaleType) {
        a();
        return this.c.getBitmap(a(str, i, i2, scaleType)) != null;
    }

    public ImageContainer get(String str, ImageListener imageListener) {
        return get(str, imageListener, 0, 0);
    }

    public ImageContainer get(String str, ImageListener imageListener, int i, int i2) {
        return get(str, imageListener, i, i2, ScaleType.CENTER_INSIDE);
    }

    public ImageContainer get(String str, ImageListener imageListener, int i, int i2, ScaleType scaleType) {
        ImageListener imageListener2 = imageListener;
        a();
        String str2 = str;
        int i3 = i;
        int i4 = i2;
        ScaleType scaleType2 = scaleType;
        String a2 = a(str2, i3, i4, scaleType2);
        Bitmap bitmap = this.c.getBitmap(a2);
        if (bitmap != null) {
            ImageContainer imageContainer = new ImageContainer(bitmap, str2, null, null);
            imageListener2.onResponse(imageContainer, true);
            return imageContainer;
        }
        ImageContainer imageContainer2 = new ImageContainer(null, str2, a2, imageListener2);
        imageListener2.onResponse(imageContainer2, true);
        BatchedImageRequest batchedImageRequest = (BatchedImageRequest) this.d.get(a2);
        if (batchedImageRequest != null) {
            batchedImageRequest.a(imageContainer2);
            return imageContainer2;
        }
        Request makeImageRequest = makeImageRequest(str2, i3, i4, scaleType2, a2);
        this.a.add(makeImageRequest);
        this.d.put(a2, new BatchedImageRequest(makeImageRequest, imageContainer2));
        return imageContainer2;
    }

    /* access modifiers changed from: protected */
    public Request<Bitmap> makeImageRequest(String str, int i, int i2, ScaleType scaleType, final String str2) {
        ImageRequest imageRequest = new ImageRequest(str, new Listener<Bitmap>() {
            /* renamed from: a */
            public void onResponse(Bitmap bitmap) {
                ImageLoader.this.onGetImageSuccess(str2, bitmap);
            }
        }, i, i2, scaleType, Config.RGB_565, new ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                ImageLoader.this.onGetImageError(str2, volleyError);
            }
        });
        return imageRequest;
    }

    public void setBatchedResponseDelay(int i) {
        this.b = i;
    }

    /* access modifiers changed from: protected */
    public void onGetImageSuccess(String str, Bitmap bitmap) {
        this.c.putBitmap(str, bitmap);
        BatchedImageRequest batchedImageRequest = (BatchedImageRequest) this.d.remove(str);
        if (batchedImageRequest != null) {
            batchedImageRequest.c = bitmap;
            a(str, batchedImageRequest);
        }
    }

    /* access modifiers changed from: protected */
    public void onGetImageError(String str, VolleyError volleyError) {
        BatchedImageRequest batchedImageRequest = (BatchedImageRequest) this.d.remove(str);
        if (batchedImageRequest != null) {
            batchedImageRequest.a(volleyError);
            a(str, batchedImageRequest);
        }
    }

    private void a(String str, BatchedImageRequest batchedImageRequest) {
        this.e.put(str, batchedImageRequest);
        if (this.g == null) {
            this.g = new Runnable() {
                public void run() {
                    for (BatchedImageRequest batchedImageRequest : ImageLoader.this.e.values()) {
                        Iterator it = batchedImageRequest.e.iterator();
                        while (it.hasNext()) {
                            ImageContainer imageContainer = (ImageContainer) it.next();
                            if (imageContainer.c != null) {
                                if (batchedImageRequest.a() == null) {
                                    imageContainer.b = batchedImageRequest.c;
                                    imageContainer.c.onResponse(imageContainer, false);
                                } else {
                                    imageContainer.c.onErrorResponse(batchedImageRequest.a());
                                }
                            }
                        }
                    }
                    ImageLoader.this.e.clear();
                    ImageLoader.this.g = null;
                }
            };
            this.f.postDelayed(this.g, (long) this.b);
        }
    }

    private void a() {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new IllegalStateException("ImageLoader must be invoked from the main thread.");
        }
    }

    private static String a(String str, int i, int i2, ScaleType scaleType) {
        StringBuilder sb = new StringBuilder(str.length() + 12);
        sb.append("#W");
        sb.append(i);
        sb.append("#H");
        sb.append(i2);
        sb.append("#S");
        sb.append(scaleType.ordinal());
        sb.append(str);
        return sb.toString();
    }
}
