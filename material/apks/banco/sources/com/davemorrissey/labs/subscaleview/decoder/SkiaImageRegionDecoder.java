package com.davemorrissey.labs.subscaleview.decoder;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory.Options;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SkiaImageRegionDecoder implements ImageRegionDecoder {
    private static final String ASSET_PREFIX = "file:///android_asset/";
    private static final String FILE_PREFIX = "file://";
    private static final String RESOURCE_PREFIX = "android.resource://";
    private final Config bitmapConfig;
    private BitmapRegionDecoder decoder;
    private final ReadWriteLock decoderLock;

    @Keep
    public SkiaImageRegionDecoder() {
        this(null);
    }

    public SkiaImageRegionDecoder(@Nullable Config config) {
        this.decoderLock = new ReentrantReadWriteLock(true);
        Config preferredBitmapConfig = SubsamplingScaleImageView.getPreferredBitmapConfig();
        if (config != null) {
            this.bitmapConfig = config;
        } else if (preferredBitmapConfig != null) {
            this.bitmapConfig = preferredBitmapConfig;
        } else {
            this.bitmapConfig = Config.RGB_565;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:40:0x00de A[SYNTHETIC, Splitter:B:40:0x00de] */
    @android.support.annotation.NonNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.graphics.Point init(android.content.Context r8, @android.support.annotation.NonNull android.net.Uri r9) {
        /*
            r7 = this;
            java.lang.String r0 = r9.toString()
            java.lang.String r1 = "android.resource://"
            boolean r1 = r0.startsWith(r1)
            r2 = 1
            r3 = 0
            if (r1 == 0) goto L_0x0078
            java.lang.String r0 = r9.getAuthority()
            java.lang.String r1 = r8.getPackageName()
            boolean r1 = r1.equals(r0)
            if (r1 == 0) goto L_0x0021
            android.content.res.Resources r1 = r8.getResources()
            goto L_0x0029
        L_0x0021:
            android.content.pm.PackageManager r1 = r8.getPackageManager()
            android.content.res.Resources r1 = r1.getResourcesForApplication(r0)
        L_0x0029:
            java.util.List r9 = r9.getPathSegments()
            int r4 = r9.size()
            r5 = 2
            if (r4 != r5) goto L_0x004f
            java.lang.Object r5 = r9.get(r3)
            java.lang.String r5 = (java.lang.String) r5
            java.lang.String r6 = "drawable"
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L_0x004f
            java.lang.Object r9 = r9.get(r2)
            java.lang.String r9 = (java.lang.String) r9
            java.lang.String r2 = "drawable"
            int r9 = r1.getIdentifier(r9, r2, r0)
            goto L_0x0069
        L_0x004f:
            if (r4 != r2) goto L_0x0068
            java.lang.Object r0 = r9.get(r3)
            java.lang.CharSequence r0 = (java.lang.CharSequence) r0
            boolean r0 = android.text.TextUtils.isDigitsOnly(r0)
            if (r0 == 0) goto L_0x0068
            java.lang.Object r9 = r9.get(r3)     // Catch:{ NumberFormatException -> 0x0068 }
            java.lang.String r9 = (java.lang.String) r9     // Catch:{ NumberFormatException -> 0x0068 }
            int r9 = java.lang.Integer.parseInt(r9)     // Catch:{ NumberFormatException -> 0x0068 }
            goto L_0x0069
        L_0x0068:
            r9 = 0
        L_0x0069:
            android.content.res.Resources r8 = r8.getResources()
            java.io.InputStream r8 = r8.openRawResource(r9)
            android.graphics.BitmapRegionDecoder r8 = android.graphics.BitmapRegionDecoder.newInstance(r8, r3)
            r7.decoder = r8
            goto L_0x00c6
        L_0x0078:
            java.lang.String r1 = "file:///android_asset/"
            boolean r1 = r0.startsWith(r1)
            if (r1 == 0) goto L_0x0099
            java.lang.String r9 = "file:///android_asset/"
            int r9 = r9.length()
            java.lang.String r9 = r0.substring(r9)
            android.content.res.AssetManager r8 = r8.getAssets()
            java.io.InputStream r8 = r8.open(r9, r2)
            android.graphics.BitmapRegionDecoder r8 = android.graphics.BitmapRegionDecoder.newInstance(r8, r3)
            r7.decoder = r8
            goto L_0x00c6
        L_0x0099:
            java.lang.String r1 = "file://"
            boolean r1 = r0.startsWith(r1)
            if (r1 == 0) goto L_0x00b2
            java.lang.String r8 = "file://"
            int r8 = r8.length()
            java.lang.String r8 = r0.substring(r8)
            android.graphics.BitmapRegionDecoder r8 = android.graphics.BitmapRegionDecoder.newInstance(r8, r3)
            r7.decoder = r8
            goto L_0x00c6
        L_0x00b2:
            r0 = 0
            android.content.ContentResolver r8 = r8.getContentResolver()     // Catch:{ all -> 0x00da }
            java.io.InputStream r8 = r8.openInputStream(r9)     // Catch:{ all -> 0x00da }
            android.graphics.BitmapRegionDecoder r9 = android.graphics.BitmapRegionDecoder.newInstance(r8, r3)     // Catch:{ all -> 0x00d8 }
            r7.decoder = r9     // Catch:{ all -> 0x00d8 }
            if (r8 == 0) goto L_0x00c6
            r8.close()     // Catch:{ Exception -> 0x00c6 }
        L_0x00c6:
            android.graphics.Point r8 = new android.graphics.Point
            android.graphics.BitmapRegionDecoder r9 = r7.decoder
            int r9 = r9.getWidth()
            android.graphics.BitmapRegionDecoder r0 = r7.decoder
            int r0 = r0.getHeight()
            r8.<init>(r9, r0)
            return r8
        L_0x00d8:
            r9 = move-exception
            goto L_0x00dc
        L_0x00da:
            r9 = move-exception
            r8 = r0
        L_0x00dc:
            if (r8 == 0) goto L_0x00e1
            r8.close()     // Catch:{ Exception -> 0x00e1 }
        L_0x00e1:
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.davemorrissey.labs.subscaleview.decoder.SkiaImageRegionDecoder.init(android.content.Context, android.net.Uri):android.graphics.Point");
    }

    @NonNull
    public Bitmap decodeRegion(@NonNull Rect rect, int i) {
        getDecodeLock().lock();
        try {
            if (this.decoder == null || this.decoder.isRecycled()) {
                throw new IllegalStateException("Cannot decode region after decoder has been recycled");
            }
            Options options = new Options();
            options.inSampleSize = i;
            options.inPreferredConfig = this.bitmapConfig;
            Bitmap decodeRegion = this.decoder.decodeRegion(rect, options);
            if (decodeRegion != null) {
                return decodeRegion;
            }
            throw new RuntimeException("Skia image decoder returned null bitmap - image format may not be supported");
        } finally {
            getDecodeLock().unlock();
        }
    }

    public synchronized boolean isReady() {
        return this.decoder != null && !this.decoder.isRecycled();
    }

    public synchronized void recycle() {
        this.decoderLock.writeLock().lock();
        try {
            this.decoder.recycle();
            this.decoder = null;
        } finally {
            this.decoderLock.writeLock().unlock();
        }
    }

    private Lock getDecodeLock() {
        if (VERSION.SDK_INT < 21) {
            return this.decoderLock.writeLock();
        }
        return this.decoderLock.readLock();
    }
}
