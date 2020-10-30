package com.google.android.gms.internal;

import android.os.SystemClock;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class zzs {
    public static boolean DEBUG = Log.isLoggable(TAG, 2);
    public static String TAG = "Volley";

    static class zza {
        public static final boolean a = zzs.DEBUG;
        private final List<C0027zza> b = new ArrayList();
        private boolean c = false;

        /* renamed from: com.google.android.gms.internal.zzs$zza$zza reason: collision with other inner class name */
        static class C0027zza {
            public final String a;
            public final long b;
            public final long c;

            public C0027zza(String str, long j, long j2) {
                this.a = str;
                this.b = j;
                this.c = j2;
            }
        }

        zza() {
        }

        private long a() {
            if (this.b.size() == 0) {
                return 0;
            }
            return ((C0027zza) this.b.get(this.b.size() - 1)).c - ((C0027zza) this.b.get(0)).c;
        }

        public synchronized void a(String str) {
            this.c = true;
            long a2 = a();
            if (a2 > 0) {
                long j = ((C0027zza) this.b.get(0)).c;
                zzs.zzb("(%-4d ms) %s", Long.valueOf(a2), str);
                for (C0027zza zza : this.b) {
                    long j2 = zza.c;
                    zzs.zzb("(+%-4d) [%2d] %s", Long.valueOf(j2 - j), Long.valueOf(zza.b), zza.a);
                    j = j2;
                }
            }
        }

        public synchronized void a(String str, long j) {
            if (this.c) {
                throw new IllegalStateException("Marker added to finished log");
            }
            List<C0027zza> list = this.b;
            C0027zza zza = new C0027zza(str, j, SystemClock.elapsedRealtime());
            list.add(zza);
        }

        /* access modifiers changed from: protected */
        public void finalize() {
            if (!this.c) {
                a("Request on the loose");
                zzs.zzc("Marker log finalized without finish() - uncaught exit point for request", new Object[0]);
            }
        }
    }

    private static String a(String str, Object... objArr) {
        if (objArr != null) {
            str = String.format(Locale.US, str, objArr);
        }
        StackTraceElement[] stackTrace = new Throwable().fillInStackTrace().getStackTrace();
        String str2 = "<unknown>";
        int i = 2;
        while (true) {
            if (i >= stackTrace.length) {
                break;
            } else if (!stackTrace[i].getClass().equals(zzs.class)) {
                String className = stackTrace[i].getClassName();
                String substring = className.substring(className.lastIndexOf(46) + 1);
                String substring2 = substring.substring(substring.lastIndexOf(36) + 1);
                String valueOf = String.valueOf(stackTrace[i].getMethodName());
                StringBuilder sb = new StringBuilder(String.valueOf(substring2).length() + 1 + String.valueOf(valueOf).length());
                sb.append(substring2);
                sb.append(".");
                sb.append(valueOf);
                str2 = sb.toString();
                break;
            } else {
                i++;
            }
        }
        return String.format(Locale.US, "[%d] %s: %s", new Object[]{Long.valueOf(Thread.currentThread().getId()), str2, str});
    }

    public static void zza(String str, Object... objArr) {
        if (DEBUG) {
            Log.v(TAG, a(str, objArr));
        }
    }

    public static void zza(Throwable th, String str, Object... objArr) {
        Log.e(TAG, a(str, objArr), th);
    }

    public static void zzb(String str, Object... objArr) {
        Log.d(TAG, a(str, objArr));
    }

    public static void zzc(String str, Object... objArr) {
        Log.e(TAG, a(str, objArr));
    }
}
