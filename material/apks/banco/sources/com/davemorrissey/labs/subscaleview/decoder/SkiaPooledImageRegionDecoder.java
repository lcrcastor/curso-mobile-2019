package com.davemorrissey.labs.subscaleview.decoder;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory.Options;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build.VERSION;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import java.io.File;
import java.io.FileFilter;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.regex.Pattern;

public class SkiaPooledImageRegionDecoder implements ImageRegionDecoder {
    private static final String ASSET_PREFIX = "file:///android_asset/";
    private static final String FILE_PREFIX = "file://";
    private static final String RESOURCE_PREFIX = "android.resource://";
    private static final String TAG = "SkiaPooledImageRegionDecoder";
    private static boolean debug = false;
    private final Config bitmapConfig;
    private Context context;
    private final ReadWriteLock decoderLock;
    /* access modifiers changed from: private */
    public DecoderPool decoderPool;
    /* access modifiers changed from: private */
    public long fileLength;
    private final Point imageDimensions;
    private final AtomicBoolean lazyInited;
    private Uri uri;

    static class DecoderPool {
        private final Semaphore available;
        private final Map<BitmapRegionDecoder, Boolean> decoders;

        private DecoderPool() {
            this.available = new Semaphore(0, true);
            this.decoders = new ConcurrentHashMap();
        }

        /* access modifiers changed from: private */
        public synchronized boolean isEmpty() {
            return this.decoders.isEmpty();
        }

        /* access modifiers changed from: private */
        public synchronized int size() {
            return this.decoders.size();
        }

        /* access modifiers changed from: private */
        public BitmapRegionDecoder acquire() {
            this.available.acquireUninterruptibly();
            return getNextAvailable();
        }

        /* access modifiers changed from: private */
        public void release(BitmapRegionDecoder bitmapRegionDecoder) {
            if (markAsUnused(bitmapRegionDecoder)) {
                this.available.release();
            }
        }

        /* access modifiers changed from: private */
        public synchronized void add(BitmapRegionDecoder bitmapRegionDecoder) {
            this.decoders.put(bitmapRegionDecoder, Boolean.valueOf(false));
            this.available.release();
        }

        /* access modifiers changed from: private */
        public synchronized void recycle() {
            while (!this.decoders.isEmpty()) {
                BitmapRegionDecoder acquire = acquire();
                acquire.recycle();
                this.decoders.remove(acquire);
            }
        }

        private synchronized BitmapRegionDecoder getNextAvailable() {
            for (Entry entry : this.decoders.entrySet()) {
                if (!((Boolean) entry.getValue()).booleanValue()) {
                    entry.setValue(Boolean.valueOf(true));
                    return (BitmapRegionDecoder) entry.getKey();
                }
            }
            return null;
        }

        private synchronized boolean markAsUnused(BitmapRegionDecoder bitmapRegionDecoder) {
            for (Entry entry : this.decoders.entrySet()) {
                if (bitmapRegionDecoder == entry.getKey()) {
                    if (!((Boolean) entry.getValue()).booleanValue()) {
                        return false;
                    }
                    entry.setValue(Boolean.valueOf(false));
                    return true;
                }
            }
            return false;
        }
    }

    @Keep
    public SkiaPooledImageRegionDecoder() {
        this(null);
    }

    public SkiaPooledImageRegionDecoder(@Nullable Config config) {
        this.decoderPool = new DecoderPool();
        this.decoderLock = new ReentrantReadWriteLock(true);
        this.fileLength = Long.MAX_VALUE;
        this.imageDimensions = new Point(0, 0);
        this.lazyInited = new AtomicBoolean(false);
        Config preferredBitmapConfig = SubsamplingScaleImageView.getPreferredBitmapConfig();
        if (config != null) {
            this.bitmapConfig = config;
        } else if (preferredBitmapConfig != null) {
            this.bitmapConfig = preferredBitmapConfig;
        } else {
            this.bitmapConfig = Config.RGB_565;
        }
    }

    @Keep
    public static void setDebug(boolean z) {
        debug = z;
    }

    @NonNull
    public Point init(Context context2, @NonNull Uri uri2) {
        this.context = context2;
        this.uri = uri2;
        initialiseDecoder();
        return this.imageDimensions;
    }

    private void lazyInit() {
        if (this.lazyInited.compareAndSet(false, true) && this.fileLength < Long.MAX_VALUE) {
            debug("Starting lazy init of additional decoders");
            new Thread() {
                public void run() {
                    while (SkiaPooledImageRegionDecoder.this.decoderPool != null && SkiaPooledImageRegionDecoder.this.allowAdditionalDecoder(SkiaPooledImageRegionDecoder.this.decoderPool.size(), SkiaPooledImageRegionDecoder.this.fileLength)) {
                        try {
                            if (SkiaPooledImageRegionDecoder.this.decoderPool != null) {
                                long currentTimeMillis = System.currentTimeMillis();
                                SkiaPooledImageRegionDecoder.this.debug("Starting decoder");
                                SkiaPooledImageRegionDecoder.this.initialiseDecoder();
                                long currentTimeMillis2 = System.currentTimeMillis();
                                SkiaPooledImageRegionDecoder skiaPooledImageRegionDecoder = SkiaPooledImageRegionDecoder.this;
                                StringBuilder sb = new StringBuilder();
                                sb.append("Started decoder, took ");
                                sb.append(currentTimeMillis2 - currentTimeMillis);
                                sb.append("ms");
                                skiaPooledImageRegionDecoder.debug(sb.toString());
                            }
                        } catch (Exception e) {
                            SkiaPooledImageRegionDecoder skiaPooledImageRegionDecoder2 = SkiaPooledImageRegionDecoder.this;
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append("Failed to start decoder: ");
                            sb2.append(e.getMessage());
                            skiaPooledImageRegionDecoder2.debug(sb2.toString());
                        }
                    }
                }
            }.start();
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x0153 A[SYNTHETIC, Splitter:B:70:0x0153] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void initialiseDecoder() {
        /*
            r10 = this;
            android.net.Uri r0 = r10.uri
            java.lang.String r0 = r0.toString()
            java.lang.String r1 = "android.resource://"
            boolean r1 = r0.startsWith(r1)
            r2 = 1
            r3 = 0
            r4 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
            if (r1 == 0) goto L_0x0099
            android.net.Uri r0 = r10.uri
            java.lang.String r0 = r0.getAuthority()
            android.content.Context r1 = r10.context
            java.lang.String r1 = r1.getPackageName()
            boolean r1 = r1.equals(r0)
            if (r1 == 0) goto L_0x002e
            android.content.Context r1 = r10.context
            android.content.res.Resources r1 = r1.getResources()
            goto L_0x0038
        L_0x002e:
            android.content.Context r1 = r10.context
            android.content.pm.PackageManager r1 = r1.getPackageManager()
            android.content.res.Resources r1 = r1.getResourcesForApplication(r0)
        L_0x0038:
            android.net.Uri r6 = r10.uri
            java.util.List r6 = r6.getPathSegments()
            int r7 = r6.size()
            r8 = 2
            if (r7 != r8) goto L_0x0060
            java.lang.Object r8 = r6.get(r3)
            java.lang.String r8 = (java.lang.String) r8
            java.lang.String r9 = "drawable"
            boolean r8 = r8.equals(r9)
            if (r8 == 0) goto L_0x0060
            java.lang.Object r2 = r6.get(r2)
            java.lang.String r2 = (java.lang.String) r2
            java.lang.String r6 = "drawable"
            int r0 = r1.getIdentifier(r2, r6, r0)
            goto L_0x007a
        L_0x0060:
            if (r7 != r2) goto L_0x0079
            java.lang.Object r0 = r6.get(r3)
            java.lang.CharSequence r0 = (java.lang.CharSequence) r0
            boolean r0 = android.text.TextUtils.isDigitsOnly(r0)
            if (r0 == 0) goto L_0x0079
            java.lang.Object r0 = r6.get(r3)     // Catch:{ NumberFormatException -> 0x0079 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ NumberFormatException -> 0x0079 }
            int r0 = java.lang.Integer.parseInt(r0)     // Catch:{ NumberFormatException -> 0x0079 }
            goto L_0x007a
        L_0x0079:
            r0 = 0
        L_0x007a:
            android.content.Context r1 = r10.context     // Catch:{ Exception -> 0x0089 }
            android.content.res.Resources r1 = r1.getResources()     // Catch:{ Exception -> 0x0089 }
            android.content.res.AssetFileDescriptor r1 = r1.openRawResourceFd(r0)     // Catch:{ Exception -> 0x0089 }
            long r1 = r1.getLength()     // Catch:{ Exception -> 0x0089 }
            r4 = r1
        L_0x0089:
            android.content.Context r1 = r10.context
            android.content.res.Resources r1 = r1.getResources()
            java.io.InputStream r0 = r1.openRawResource(r0)
            android.graphics.BitmapRegionDecoder r0 = android.graphics.BitmapRegionDecoder.newInstance(r0, r3)
            goto L_0x0116
        L_0x0099:
            java.lang.String r1 = "file:///android_asset/"
            boolean r1 = r0.startsWith(r1)
            if (r1 == 0) goto L_0x00c9
            java.lang.String r1 = "file:///android_asset/"
            int r1 = r1.length()
            java.lang.String r0 = r0.substring(r1)
            android.content.Context r1 = r10.context     // Catch:{ Exception -> 0x00ba }
            android.content.res.AssetManager r1 = r1.getAssets()     // Catch:{ Exception -> 0x00ba }
            android.content.res.AssetFileDescriptor r1 = r1.openFd(r0)     // Catch:{ Exception -> 0x00ba }
            long r6 = r1.getLength()     // Catch:{ Exception -> 0x00ba }
            r4 = r6
        L_0x00ba:
            android.content.Context r1 = r10.context
            android.content.res.AssetManager r1 = r1.getAssets()
            java.io.InputStream r0 = r1.open(r0, r2)
            android.graphics.BitmapRegionDecoder r0 = android.graphics.BitmapRegionDecoder.newInstance(r0, r3)
            goto L_0x0116
        L_0x00c9:
            java.lang.String r1 = "file://"
            boolean r1 = r0.startsWith(r1)
            if (r1 == 0) goto L_0x00f1
            java.lang.String r1 = "file://"
            int r1 = r1.length()
            java.lang.String r1 = r0.substring(r1)
            android.graphics.BitmapRegionDecoder r1 = android.graphics.BitmapRegionDecoder.newInstance(r1, r3)
            java.io.File r2 = new java.io.File     // Catch:{ Exception -> 0x00ef }
            r2.<init>(r0)     // Catch:{ Exception -> 0x00ef }
            boolean r0 = r2.exists()     // Catch:{ Exception -> 0x00ef }
            if (r0 == 0) goto L_0x00ef
            long r2 = r2.length()     // Catch:{ Exception -> 0x00ef }
            r4 = r2
        L_0x00ef:
            r0 = r1
            goto L_0x0116
        L_0x00f1:
            r0 = 0
            android.content.Context r1 = r10.context     // Catch:{ all -> 0x014e }
            android.content.ContentResolver r1 = r1.getContentResolver()     // Catch:{ all -> 0x014e }
            android.net.Uri r2 = r10.uri     // Catch:{ all -> 0x014e }
            java.io.InputStream r2 = r1.openInputStream(r2)     // Catch:{ all -> 0x014e }
            android.graphics.BitmapRegionDecoder r0 = android.graphics.BitmapRegionDecoder.newInstance(r2, r3)     // Catch:{ all -> 0x014c }
            android.net.Uri r3 = r10.uri     // Catch:{ Exception -> 0x0111 }
            java.lang.String r6 = "r"
            android.content.res.AssetFileDescriptor r1 = r1.openAssetFileDescriptor(r3, r6)     // Catch:{ Exception -> 0x0111 }
            if (r1 == 0) goto L_0x0111
            long r6 = r1.getLength()     // Catch:{ Exception -> 0x0111 }
            r4 = r6
        L_0x0111:
            if (r2 == 0) goto L_0x0116
            r2.close()     // Catch:{ Exception -> 0x0116 }
        L_0x0116:
            r10.fileLength = r4
            android.graphics.Point r1 = r10.imageDimensions
            int r2 = r0.getWidth()
            int r3 = r0.getHeight()
            r1.set(r2, r3)
            java.util.concurrent.locks.ReadWriteLock r1 = r10.decoderLock
            java.util.concurrent.locks.Lock r1 = r1.writeLock()
            r1.lock()
            com.davemorrissey.labs.subscaleview.decoder.SkiaPooledImageRegionDecoder$DecoderPool r1 = r10.decoderPool     // Catch:{ all -> 0x0141 }
            if (r1 == 0) goto L_0x0137
            com.davemorrissey.labs.subscaleview.decoder.SkiaPooledImageRegionDecoder$DecoderPool r1 = r10.decoderPool     // Catch:{ all -> 0x0141 }
            r1.add(r0)     // Catch:{ all -> 0x0141 }
        L_0x0137:
            java.util.concurrent.locks.ReadWriteLock r0 = r10.decoderLock
            java.util.concurrent.locks.Lock r0 = r0.writeLock()
            r0.unlock()
            return
        L_0x0141:
            r0 = move-exception
            java.util.concurrent.locks.ReadWriteLock r1 = r10.decoderLock
            java.util.concurrent.locks.Lock r1 = r1.writeLock()
            r1.unlock()
            throw r0
        L_0x014c:
            r0 = move-exception
            goto L_0x0151
        L_0x014e:
            r1 = move-exception
            r2 = r0
            r0 = r1
        L_0x0151:
            if (r2 == 0) goto L_0x0156
            r2.close()     // Catch:{ Exception -> 0x0156 }
        L_0x0156:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.davemorrissey.labs.subscaleview.decoder.SkiaPooledImageRegionDecoder.initialiseDecoder():void");
    }

    @NonNull
    public Bitmap decodeRegion(@NonNull Rect rect, int i) {
        BitmapRegionDecoder access$700;
        StringBuilder sb = new StringBuilder();
        sb.append("Decode region ");
        sb.append(rect);
        sb.append(" on thread ");
        sb.append(Thread.currentThread().getName());
        debug(sb.toString());
        if (rect.width() < this.imageDimensions.x || rect.height() < this.imageDimensions.y) {
            lazyInit();
        }
        this.decoderLock.readLock().lock();
        try {
            if (this.decoderPool != null) {
                access$700 = this.decoderPool.acquire();
                if (access$700 != null) {
                    if (!access$700.isRecycled()) {
                        Options options = new Options();
                        options.inSampleSize = i;
                        options.inPreferredConfig = this.bitmapConfig;
                        Bitmap decodeRegion = access$700.decodeRegion(rect, options);
                        if (decodeRegion == null) {
                            throw new RuntimeException("Skia image decoder returned null bitmap - image format may not be supported");
                        }
                        if (access$700 != null) {
                            this.decoderPool.release(access$700);
                        }
                        this.decoderLock.readLock().unlock();
                        return decodeRegion;
                    }
                }
                if (access$700 != null) {
                    this.decoderPool.release(access$700);
                }
            }
            throw new IllegalStateException("Cannot decode region after decoder has been recycled");
        } catch (Throwable th) {
            this.decoderLock.readLock().unlock();
            throw th;
        }
    }

    public synchronized boolean isReady() {
        return this.decoderPool != null && !this.decoderPool.isEmpty();
    }

    public synchronized void recycle() {
        this.decoderLock.writeLock().lock();
        try {
            if (this.decoderPool != null) {
                this.decoderPool.recycle();
                this.decoderPool = null;
                this.context = null;
                this.uri = null;
            }
        } finally {
            this.decoderLock.writeLock().unlock();
        }
    }

    /* access modifiers changed from: protected */
    public boolean allowAdditionalDecoder(int i, long j) {
        if (i >= 4) {
            debug("No additional decoders allowed, reached hard limit (4)");
            return false;
        }
        long j2 = ((long) i) * j;
        if (j2 > 20971520) {
            debug("No additional encoders allowed, reached hard memory limit (20Mb)");
            return false;
        } else if (i >= getNumberOfCores()) {
            StringBuilder sb = new StringBuilder();
            sb.append("No additional encoders allowed, limited by CPU cores (");
            sb.append(getNumberOfCores());
            sb.append(")");
            debug(sb.toString());
            return false;
        } else if (isLowMemory()) {
            debug("No additional encoders allowed, memory is low");
            return false;
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Additional decoder allowed, current count is ");
            sb2.append(i);
            sb2.append(", estimated native memory ");
            sb2.append(j2 / PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED);
            sb2.append("Mb");
            debug(sb2.toString());
            return true;
        }
    }

    private int getNumberOfCores() {
        if (VERSION.SDK_INT >= 17) {
            return Runtime.getRuntime().availableProcessors();
        }
        return getNumCoresOldPhones();
    }

    private int getNumCoresOldPhones() {
        try {
            return new File("/sys/devices/system/cpu/").listFiles(new FileFilter() {
                public boolean accept(File file) {
                    return Pattern.matches("cpu[0-9]+", file.getName());
                }
            }).length;
        } catch (Exception unused) {
            return 1;
        }
    }

    private boolean isLowMemory() {
        ActivityManager activityManager = (ActivityManager) this.context.getSystemService("activity");
        if (activityManager == null) {
            return true;
        }
        MemoryInfo memoryInfo = new MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        return memoryInfo.lowMemory;
    }

    /* access modifiers changed from: private */
    public void debug(String str) {
        if (debug) {
            Log.d(TAG, str);
        }
    }
}
