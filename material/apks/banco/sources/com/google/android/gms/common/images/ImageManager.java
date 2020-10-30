package com.google.android.gms.common.images;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.ResultReceiver;
import android.os.SystemClock;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;
import com.google.android.gms.common.annotation.KeepName;
import com.google.android.gms.common.util.zzs;
import com.google.android.gms.internal.zzrv;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class ImageManager {
    /* access modifiers changed from: private */
    public static final Object a = new Object();
    /* access modifiers changed from: private */
    public static HashSet<Uri> b = new HashSet<>();
    private static ImageManager c;
    private static ImageManager d;
    /* access modifiers changed from: private */
    public final Context e;
    /* access modifiers changed from: private */
    public final Handler f = new Handler(Looper.getMainLooper());
    /* access modifiers changed from: private */
    public final ExecutorService g = Executors.newFixedThreadPool(4);
    /* access modifiers changed from: private */
    public final zzb h;
    /* access modifiers changed from: private */
    public final zzrv i;
    /* access modifiers changed from: private */
    public final Map<zza, ImageReceiver> j;
    /* access modifiers changed from: private */
    public final Map<Uri, ImageReceiver> k;
    /* access modifiers changed from: private */
    public final Map<Uri, Long> l;

    @KeepName
    final class ImageReceiver extends ResultReceiver {
        private final Uri b;
        /* access modifiers changed from: private */
        public final ArrayList<zza> c = new ArrayList<>();

        ImageReceiver(Uri uri) {
            super(new Handler(Looper.getMainLooper()));
            this.b = uri;
        }

        public void a() {
            Intent intent = new Intent("com.google.android.gms.common.images.LOAD_IMAGE");
            intent.putExtra("com.google.android.gms.extras.uri", this.b);
            intent.putExtra("com.google.android.gms.extras.resultReceiver", this);
            intent.putExtra("com.google.android.gms.extras.priority", 3);
            ImageManager.this.e.sendBroadcast(intent);
        }

        public void a(zza zza) {
            com.google.android.gms.common.internal.zzc.zzhq("ImageReceiver.addImageRequest() must be called in the main thread");
            this.c.add(zza);
        }

        public void b(zza zza) {
            com.google.android.gms.common.internal.zzc.zzhq("ImageReceiver.removeImageRequest() must be called in the main thread");
            this.c.remove(zza);
        }

        public void onReceiveResult(int i, Bundle bundle) {
            ImageManager.this.g.execute(new zzc(this.b, (ParcelFileDescriptor) bundle.getParcelable("com.google.android.gms.extra.fileDescriptor")));
        }
    }

    public interface OnImageLoadedListener {
        void onImageLoaded(Uri uri, Drawable drawable, boolean z);
    }

    @TargetApi(11)
    static final class zza {
        static int a(ActivityManager activityManager) {
            return activityManager.getLargeMemoryClass();
        }
    }

    static final class zzb extends LruCache<C0003zza, Bitmap> {
        public zzb(Context context) {
            super(a(context));
        }

        @TargetApi(11)
        private static int a(Context context) {
            ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
            return (int) (((float) (((!((context.getApplicationInfo().flags & 1048576) != 0) || !zzs.zzaxk()) ? activityManager.getMemoryClass() : zza.a(activityManager)) * 1048576)) * 0.33f);
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public int sizeOf(C0003zza zza, Bitmap bitmap) {
            return bitmap.getHeight() * bitmap.getRowBytes();
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public void entryRemoved(boolean z, C0003zza zza, Bitmap bitmap, Bitmap bitmap2) {
            super.entryRemoved(z, zza, bitmap, bitmap2);
        }
    }

    final class zzc implements Runnable {
        private final Uri b;
        private final ParcelFileDescriptor c;

        public zzc(Uri uri, ParcelFileDescriptor parcelFileDescriptor) {
            this.b = uri;
            this.c = parcelFileDescriptor;
        }

        public void run() {
            boolean z;
            Bitmap bitmap;
            com.google.android.gms.common.internal.zzc.zzhr("LoadBitmapFromDiskRunnable can't be executed in the main thread");
            boolean z2 = false;
            Bitmap bitmap2 = null;
            if (this.c != null) {
                try {
                    bitmap2 = BitmapFactory.decodeFileDescriptor(this.c.getFileDescriptor());
                } catch (OutOfMemoryError e) {
                    String valueOf = String.valueOf(this.b);
                    StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 34);
                    sb.append("OOM while loading bitmap for uri: ");
                    sb.append(valueOf);
                    Log.e("ImageManager", sb.toString(), e);
                    z2 = true;
                }
                try {
                    this.c.close();
                } catch (IOException e2) {
                    Log.e("ImageManager", "closed failed", e2);
                }
                z = z2;
                bitmap = bitmap2;
            } else {
                bitmap = null;
                z = false;
            }
            CountDownLatch countDownLatch = new CountDownLatch(1);
            Handler g = ImageManager.this.f;
            zzf zzf = new zzf(this.b, bitmap, z, countDownLatch);
            g.post(zzf);
            try {
                countDownLatch.await();
            } catch (InterruptedException unused) {
                String valueOf2 = String.valueOf(this.b);
                StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf2).length() + 32);
                sb2.append("Latch interrupted while posting ");
                sb2.append(valueOf2);
                Log.w("ImageManager", sb2.toString());
            }
        }
    }

    final class zzd implements Runnable {
        private final zza b;

        public zzd(zza zza) {
            this.b = zza;
        }

        public void run() {
            com.google.android.gms.common.internal.zzc.zzhq("LoadImageRunnable must be executed on the main thread");
            ImageReceiver imageReceiver = (ImageReceiver) ImageManager.this.j.get(this.b);
            if (imageReceiver != null) {
                ImageManager.this.j.remove(this.b);
                imageReceiver.b(this.b);
            }
            C0003zza zza = this.b.a;
            if (zza.a == null) {
                this.b.a(ImageManager.this.e, ImageManager.this.i, true);
                return;
            }
            Bitmap a2 = ImageManager.this.a(zza);
            if (a2 != null) {
                this.b.a(ImageManager.this.e, a2, true);
                return;
            }
            Long l = (Long) ImageManager.this.l.get(zza.a);
            if (l != null) {
                if (SystemClock.elapsedRealtime() - l.longValue() < 3600000) {
                    this.b.a(ImageManager.this.e, ImageManager.this.i, true);
                    return;
                }
                ImageManager.this.l.remove(zza.a);
            }
            this.b.a(ImageManager.this.e, ImageManager.this.i);
            ImageReceiver imageReceiver2 = (ImageReceiver) ImageManager.this.k.get(zza.a);
            if (imageReceiver2 == null) {
                imageReceiver2 = new ImageReceiver(zza.a);
                ImageManager.this.k.put(zza.a, imageReceiver2);
            }
            imageReceiver2.a(this.b);
            if (!(this.b instanceof com.google.android.gms.common.images.zza.zzc)) {
                ImageManager.this.j.put(this.b, imageReceiver2);
            }
            synchronized (ImageManager.a) {
                if (!ImageManager.b.contains(zza.a)) {
                    ImageManager.b.add(zza.a);
                    imageReceiver2.a();
                }
            }
        }
    }

    @TargetApi(14)
    static final class zze implements ComponentCallbacks2 {
        private final zzb a;

        public zze(zzb zzb) {
            this.a = zzb;
        }

        public void onConfigurationChanged(Configuration configuration) {
        }

        public void onLowMemory() {
            this.a.evictAll();
        }

        public void onTrimMemory(int i) {
            if (i >= 60) {
                this.a.evictAll();
                return;
            }
            if (i >= 20) {
                this.a.trimToSize(this.a.size() / 2);
            }
        }
    }

    final class zzf implements Runnable {
        private final Uri b;
        private final Bitmap c;
        private final CountDownLatch d;
        private boolean e;

        public zzf(Uri uri, Bitmap bitmap, boolean z, CountDownLatch countDownLatch) {
            this.b = uri;
            this.c = bitmap;
            this.e = z;
            this.d = countDownLatch;
        }

        private void a(ImageReceiver imageReceiver, boolean z) {
            ArrayList a2 = imageReceiver.c;
            int size = a2.size();
            for (int i = 0; i < size; i++) {
                zza zza = (zza) a2.get(i);
                if (z) {
                    zza.a(ImageManager.this.e, this.c, false);
                } else {
                    ImageManager.this.l.put(this.b, Long.valueOf(SystemClock.elapsedRealtime()));
                    zza.a(ImageManager.this.e, ImageManager.this.i, false);
                }
                if (!(zza instanceof com.google.android.gms.common.images.zza.zzc)) {
                    ImageManager.this.j.remove(zza);
                }
            }
        }

        public void run() {
            com.google.android.gms.common.internal.zzc.zzhq("OnBitmapLoadedRunnable must be executed in the main thread");
            boolean z = this.c != null;
            if (ImageManager.this.h != null) {
                if (this.e) {
                    ImageManager.this.h.evictAll();
                    System.gc();
                    this.e = false;
                    ImageManager.this.f.post(this);
                    return;
                } else if (z) {
                    ImageManager.this.h.put(new C0003zza(this.b), this.c);
                }
            }
            ImageReceiver imageReceiver = (ImageReceiver) ImageManager.this.k.remove(this.b);
            if (imageReceiver != null) {
                a(imageReceiver, z);
            }
            this.d.countDown();
            synchronized (ImageManager.a) {
                ImageManager.b.remove(this.b);
            }
        }
    }

    private ImageManager(Context context, boolean z) {
        this.e = context.getApplicationContext();
        if (z) {
            this.h = new zzb(this.e);
            if (zzs.zzaxn()) {
                c();
            }
        } else {
            this.h = null;
        }
        this.i = new zzrv();
        this.j = new HashMap();
        this.k = new HashMap();
        this.l = new HashMap();
    }

    /* access modifiers changed from: private */
    public Bitmap a(C0003zza zza2) {
        if (this.h == null) {
            return null;
        }
        return (Bitmap) this.h.get(zza2);
    }

    @TargetApi(14)
    private void c() {
        this.e.registerComponentCallbacks(new zze(this.h));
    }

    public static ImageManager create(Context context) {
        return zzg(context, false);
    }

    public static ImageManager zzg(Context context, boolean z) {
        if (z) {
            if (d == null) {
                d = new ImageManager(context, true);
            }
            return d;
        }
        if (c == null) {
            c = new ImageManager(context, false);
        }
        return c;
    }

    public void loadImage(ImageView imageView, int i2) {
        zza(new com.google.android.gms.common.images.zza.zzb(imageView, i2));
    }

    public void loadImage(ImageView imageView, Uri uri) {
        zza(new com.google.android.gms.common.images.zza.zzb(imageView, uri));
    }

    public void loadImage(ImageView imageView, Uri uri, int i2) {
        com.google.android.gms.common.images.zza.zzb zzb2 = new com.google.android.gms.common.images.zza.zzb(imageView, uri);
        zzb2.zzgh(i2);
        zza(zzb2);
    }

    public void loadImage(OnImageLoadedListener onImageLoadedListener, Uri uri) {
        zza(new com.google.android.gms.common.images.zza.zzc(onImageLoadedListener, uri));
    }

    public void loadImage(OnImageLoadedListener onImageLoadedListener, Uri uri, int i2) {
        com.google.android.gms.common.images.zza.zzc zzc2 = new com.google.android.gms.common.images.zza.zzc(onImageLoadedListener, uri);
        zzc2.zzgh(i2);
        zza(zzc2);
    }

    public void zza(zza zza2) {
        com.google.android.gms.common.internal.zzc.zzhq("ImageManager.loadImage() must be called in the main thread");
        new zzd(zza2).run();
    }
}
