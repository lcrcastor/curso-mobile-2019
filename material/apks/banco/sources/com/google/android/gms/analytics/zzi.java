package com.google.android.gms.analytics;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Process;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import com.google.android.gms.analytics.internal.zzao;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.internal.zzmi;
import com.google.android.gms.internal.zzmn;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public final class zzi {
    private static volatile zzi a;
    private final Context b;
    /* access modifiers changed from: private */
    public final List<zzj> c = new CopyOnWriteArrayList();
    private final zzd d = new zzd();
    private final zza e = new zza();
    private volatile zzmi f;
    /* access modifiers changed from: private */
    public UncaughtExceptionHandler g;

    class zza extends ThreadPoolExecutor {
        public zza() {
            super(1, 1, 1, TimeUnit.MINUTES, new LinkedBlockingQueue());
            setThreadFactory(new zzb());
            allowCoreThreadTimeOut(true);
        }

        /* access modifiers changed from: protected */
        public <T> RunnableFuture<T> newTaskFor(Runnable runnable, T t) {
            return new FutureTask<T>(runnable, t) {
                /* access modifiers changed from: protected */
                public void setException(Throwable th) {
                    UncaughtExceptionHandler b = zzi.this.g;
                    if (b != null) {
                        b.uncaughtException(Thread.currentThread(), th);
                    } else if (Log.isLoggable("GAv4", 6)) {
                        String valueOf = String.valueOf(th);
                        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 37);
                        sb.append("MeasurementExecutor: job failed with ");
                        sb.append(valueOf);
                        Log.e("GAv4", sb.toString());
                    }
                    super.setException(th);
                }
            };
        }
    }

    static class zzb implements ThreadFactory {
        private static final AtomicInteger a = new AtomicInteger();

        private zzb() {
        }

        public Thread newThread(Runnable runnable) {
            int incrementAndGet = a.incrementAndGet();
            StringBuilder sb = new StringBuilder(23);
            sb.append("measurement-");
            sb.append(incrementAndGet);
            return new zzc(runnable, sb.toString());
        }
    }

    static class zzc extends Thread {
        zzc(Runnable runnable, String str) {
            super(runnable, str);
        }

        public void run() {
            Process.setThreadPriority(10);
            super.run();
        }
    }

    zzi(Context context) {
        Context applicationContext = context.getApplicationContext();
        zzac.zzy(applicationContext);
        this.b = applicationContext;
    }

    /* access modifiers changed from: private */
    public void b(zze zze) {
        zzac.zzhr("deliver should be called from worker thread");
        zzac.zzb(zze.zzyb(), (Object) "Measurement must be submitted");
        List<zzk> zzxy = zze.zzxy();
        if (!zzxy.isEmpty()) {
            HashSet hashSet = new HashSet();
            for (zzk zzk : zzxy) {
                Uri zzxl = zzk.zzxl();
                if (!hashSet.contains(zzxl)) {
                    hashSet.add(zzxl);
                    zzk.zzb(zze);
                }
            }
        }
    }

    public static zzi zzay(Context context) {
        zzac.zzy(context);
        if (a == null) {
            synchronized (zzi.class) {
                if (a == null) {
                    a = new zzi(context);
                }
            }
        }
        return a;
    }

    public static void zzyl() {
        if (!(Thread.currentThread() instanceof zzc)) {
            throw new IllegalStateException("Call expected from worker thread");
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(zze zze) {
        if (zze.d()) {
            throw new IllegalStateException("Measurement prototype can't be submitted");
        } else if (zze.zzyb()) {
            throw new IllegalStateException("Measurement can only be submitted once");
        } else {
            final zze zzxw = zze.zzxw();
            zzxw.a();
            this.e.execute(new Runnable() {
                public void run() {
                    zzxw.b().zza(zzxw);
                    for (zzj zza : zzi.this.c) {
                        zza.zza(zzxw);
                    }
                    zzi.this.b(zzxw);
                }
            });
        }
    }

    public Context getContext() {
        return this.b;
    }

    public void zza(UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.g = uncaughtExceptionHandler;
    }

    public <V> Future<V> zzc(Callable<V> callable) {
        zzac.zzy(callable);
        if (!(Thread.currentThread() instanceof zzc)) {
            return this.e.submit(callable);
        }
        FutureTask futureTask = new FutureTask(callable);
        futureTask.run();
        return futureTask;
    }

    public void zzg(Runnable runnable) {
        zzac.zzy(runnable);
        this.e.submit(runnable);
    }

    public zzmi zzyj() {
        if (this.f == null) {
            synchronized (this) {
                if (this.f == null) {
                    zzmi zzmi = new zzmi();
                    PackageManager packageManager = this.b.getPackageManager();
                    String packageName = this.b.getPackageName();
                    zzmi.setAppId(packageName);
                    zzmi.setAppInstallerId(packageManager.getInstallerPackageName(packageName));
                    String str = null;
                    try {
                        PackageInfo packageInfo = packageManager.getPackageInfo(this.b.getPackageName(), 0);
                        if (packageInfo != null) {
                            CharSequence applicationLabel = packageManager.getApplicationLabel(packageInfo.applicationInfo);
                            if (!TextUtils.isEmpty(applicationLabel)) {
                                packageName = applicationLabel.toString();
                            }
                            str = packageInfo.versionName;
                        }
                    } catch (NameNotFoundException unused) {
                        String str2 = "GAv4";
                        String str3 = "Error retrieving package info: appName set to ";
                        String valueOf = String.valueOf(packageName);
                        Log.e(str2, valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3));
                    }
                    zzmi.setAppName(packageName);
                    zzmi.setAppVersion(str);
                    this.f = zzmi;
                }
            }
        }
        return this.f;
    }

    public zzmn zzyk() {
        DisplayMetrics displayMetrics = this.b.getResources().getDisplayMetrics();
        zzmn zzmn = new zzmn();
        zzmn.setLanguage(zzao.zza(Locale.getDefault()));
        zzmn.zzbw(displayMetrics.widthPixels);
        zzmn.zzbx(displayMetrics.heightPixels);
        return zzmn;
    }
}
