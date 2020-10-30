package com.squareup.picasso;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.StatFs;
import android.provider.Settings.System;
import android.util.Log;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.ThreadFactory;

final class Utils {
    static final StringBuilder a = new StringBuilder();

    @TargetApi(11)
    static class ActivityManagerHoneycomb {
        private ActivityManagerHoneycomb() {
        }

        static int a(ActivityManager activityManager) {
            return activityManager.getLargeMemoryClass();
        }
    }

    @TargetApi(12)
    static class BitmapHoneycombMR1 {
        private BitmapHoneycombMR1() {
        }

        static int a(Bitmap bitmap) {
            return bitmap.getByteCount();
        }
    }

    static class OkHttpLoaderCreator {
        private OkHttpLoaderCreator() {
        }

        static Downloader a(Context context) {
            return new OkHttpDownloader(context);
        }
    }

    static class PicassoThread extends Thread {
        public PicassoThread(Runnable runnable) {
            super(runnable);
        }

        public void run() {
            Process.setThreadPriority(10);
            super.run();
        }
    }

    static class PicassoThreadFactory implements ThreadFactory {
        PicassoThreadFactory() {
        }

        public Thread newThread(Runnable runnable) {
            return new PicassoThread(runnable);
        }
    }

    private Utils() {
    }

    static int a(Bitmap bitmap) {
        int i;
        if (VERSION.SDK_INT >= 12) {
            i = BitmapHoneycombMR1.a(bitmap);
        } else {
            i = bitmap.getRowBytes() * bitmap.getHeight();
        }
        if (i >= 0) {
            return i;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Negative size: ");
        sb.append(bitmap);
        throw new IllegalStateException(sb.toString());
    }

    static <T> T a(T t, String str) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(str);
    }

    static void a() {
        if (c()) {
            throw new IllegalStateException("Method call should not happen from the main thread.");
        }
    }

    static void b() {
        if (!c()) {
            throw new IllegalStateException("Method call should happen from the main thread.");
        }
    }

    static boolean c() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }

    static String a(BitmapHunter bitmapHunter) {
        return a(bitmapHunter, "");
    }

    static String a(BitmapHunter bitmapHunter, String str) {
        StringBuilder sb = new StringBuilder(str);
        Action i = bitmapHunter.i();
        if (i != null) {
            sb.append(i.b.a());
        }
        List k = bitmapHunter.k();
        if (k != null) {
            int size = k.size();
            for (int i2 = 0; i2 < size; i2++) {
                if (i2 > 0 || i != null) {
                    sb.append(", ");
                }
                sb.append(((Action) k.get(i2)).b.a());
            }
        }
        return sb.toString();
    }

    static void a(String str, String str2, String str3) {
        a(str, str2, str3, "");
    }

    static void a(String str, String str2, String str3, String str4) {
        Log.d("Picasso", String.format("%1$-11s %2$-12s %3$s %4$s", new Object[]{str, str2, str3, str4}));
    }

    static String a(Request request) {
        String a2 = a(request, a);
        a.setLength(0);
        return a2;
    }

    static String a(Request request, StringBuilder sb) {
        if (request.stableKey != null) {
            sb.ensureCapacity(request.stableKey.length() + 50);
            sb.append(request.stableKey);
        } else if (request.uri != null) {
            String uri = request.uri.toString();
            sb.ensureCapacity(uri.length() + 50);
            sb.append(uri);
        } else {
            sb.ensureCapacity(50);
            sb.append(request.resourceId);
        }
        sb.append(10);
        if (request.rotationDegrees != BitmapDescriptorFactory.HUE_RED) {
            sb.append("rotation:");
            sb.append(request.rotationDegrees);
            if (request.hasRotationPivot) {
                sb.append('@');
                sb.append(request.rotationPivotX);
                sb.append('x');
                sb.append(request.rotationPivotY);
            }
            sb.append(10);
        }
        if (request.hasSize()) {
            sb.append("resize:");
            sb.append(request.targetWidth);
            sb.append('x');
            sb.append(request.targetHeight);
            sb.append(10);
        }
        if (request.centerCrop) {
            sb.append("centerCrop");
            sb.append(10);
        } else if (request.centerInside) {
            sb.append("centerInside");
            sb.append(10);
        }
        if (request.transformations != null) {
            int size = request.transformations.size();
            for (int i = 0; i < size; i++) {
                sb.append(((Transformation) request.transformations.get(i)).key());
                sb.append(10);
            }
        }
        return sb.toString();
    }

    static void a(InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException unused) {
            }
        }
    }

    static boolean a(String str) {
        boolean z = false;
        if (str == null) {
            return false;
        }
        String[] split = str.split(UtilsCuentas.SEPARAOR2, 2);
        if ("CACHE".equals(split[0])) {
            return true;
        }
        if (split.length == 1) {
            return false;
        }
        try {
            if ("CONDITIONAL_CACHE".equals(split[0]) && Integer.parseInt(split[1]) == 304) {
                z = true;
            }
            return z;
        } catch (NumberFormatException unused) {
            return false;
        }
    }

    static Downloader a(Context context) {
        try {
            Class.forName("com.squareup.okhttp.OkHttpClient");
            return OkHttpLoaderCreator.a(context);
        } catch (ClassNotFoundException unused) {
            return new UrlConnectionDownloader(context);
        }
    }

    static File b(Context context) {
        File file = new File(context.getApplicationContext().getCacheDir(), "picasso-cache");
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    static long a(File file) {
        long j;
        try {
            StatFs statFs = new StatFs(file.getAbsolutePath());
            j = (((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize())) / 50;
        } catch (IllegalArgumentException unused) {
            j = 5242880;
        }
        return Math.max(Math.min(j, 52428800), 5242880);
    }

    static int c(Context context) {
        ActivityManager activityManager = (ActivityManager) a(context, "activity");
        boolean z = (context.getApplicationInfo().flags & 1048576) != 0;
        int memoryClass = activityManager.getMemoryClass();
        if (z && VERSION.SDK_INT >= 11) {
            memoryClass = ActivityManagerHoneycomb.a(activityManager);
        }
        return (memoryClass * 1048576) / 7;
    }

    static boolean d(Context context) {
        boolean z = false;
        try {
            if (System.getInt(context.getContentResolver(), "airplane_mode_on", 0) != 0) {
                z = true;
            }
            return z;
        } catch (NullPointerException unused) {
            return false;
        }
    }

    static <T> T a(Context context, String str) {
        return context.getSystemService(str);
    }

    static boolean b(Context context, String str) {
        return context.checkCallingOrSelfPermission(str) == 0;
    }

    static byte[] b(InputStream inputStream) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[4096];
        while (true) {
            int read = inputStream.read(bArr);
            if (-1 == read) {
                return byteArrayOutputStream.toByteArray();
            }
            byteArrayOutputStream.write(bArr, 0, read);
        }
    }

    static boolean c(InputStream inputStream) {
        byte[] bArr = new byte[12];
        if (inputStream.read(bArr, 0, 12) != 12 || !"RIFF".equals(new String(bArr, 0, 4, "US-ASCII")) || !"WEBP".equals(new String(bArr, 8, 4, "US-ASCII"))) {
            return false;
        }
        return true;
    }

    static int a(Resources resources, Request request) {
        int i;
        if (request.resourceId != 0 || request.uri == null) {
            return request.resourceId;
        }
        String authority = request.uri.getAuthority();
        if (authority == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("No package provided: ");
            sb.append(request.uri);
            throw new FileNotFoundException(sb.toString());
        }
        List pathSegments = request.uri.getPathSegments();
        if (pathSegments == null || pathSegments.isEmpty()) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("No path segments: ");
            sb2.append(request.uri);
            throw new FileNotFoundException(sb2.toString());
        }
        if (pathSegments.size() == 1) {
            try {
                i = Integer.parseInt((String) pathSegments.get(0));
            } catch (NumberFormatException unused) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append("Last path segment is not a resource ID: ");
                sb3.append(request.uri);
                throw new FileNotFoundException(sb3.toString());
            }
        } else if (pathSegments.size() == 2) {
            i = resources.getIdentifier((String) pathSegments.get(1), (String) pathSegments.get(0), authority);
        } else {
            StringBuilder sb4 = new StringBuilder();
            sb4.append("More than two path segments: ");
            sb4.append(request.uri);
            throw new FileNotFoundException(sb4.toString());
        }
        return i;
    }

    static Resources a(Context context, Request request) {
        if (request.resourceId != 0 || request.uri == null) {
            return context.getResources();
        }
        String authority = request.uri.getAuthority();
        if (authority == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("No package provided: ");
            sb.append(request.uri);
            throw new FileNotFoundException(sb.toString());
        }
        try {
            return context.getPackageManager().getResourcesForApplication(authority);
        } catch (NameNotFoundException unused) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Unable to obtain resources for package: ");
            sb2.append(request.uri);
            throw new FileNotFoundException(sb2.toString());
        }
    }

    static void a(Looper looper) {
        AnonymousClass1 r0 = new Handler(looper) {
            public void handleMessage(Message message) {
                sendMessageDelayed(obtainMessage(), 1000);
            }
        };
        r0.sendMessageDelayed(r0.obtainMessage(), 1000);
    }
}
