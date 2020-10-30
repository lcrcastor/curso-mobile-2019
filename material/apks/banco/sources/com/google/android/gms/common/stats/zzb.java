package com.google.android.gms.common.stats;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Debug;
import android.os.Process;
import android.os.SystemClock;
import android.util.Log;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import com.google.android.gms.common.stats.zzc.zza;
import com.google.android.gms.common.util.zzd;
import com.google.android.gms.common.util.zzt;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class zzb {
    private static final Object a = new Object();
    private static zzb b;
    private static Integer h;
    private final List<String> c;
    private final List<String> d;
    private final List<String> e;
    private final List<String> f;
    private zze g;
    private zze i;

    private zzb() {
        if (b() == zzd.LOG_LEVEL_OFF) {
            this.c = Collections.EMPTY_LIST;
            this.d = Collections.EMPTY_LIST;
            this.e = Collections.EMPTY_LIST;
            this.f = Collections.EMPTY_LIST;
            return;
        }
        String str = (String) zza.Ei.get();
        this.c = str == null ? Collections.EMPTY_LIST : Arrays.asList(str.split(","));
        String str2 = (String) zza.Ej.get();
        this.d = str2 == null ? Collections.EMPTY_LIST : Arrays.asList(str2.split(","));
        String str3 = (String) zza.Ek.get();
        this.e = str3 == null ? Collections.EMPTY_LIST : Arrays.asList(str3.split(","));
        String str4 = (String) zza.El.get();
        this.f = str4 == null ? Collections.EMPTY_LIST : Arrays.asList(str4.split(","));
        this.g = new zze(1024, ((Long) zza.Em.get()).longValue());
        this.i = new zze(1024, ((Long) zza.Em.get()).longValue());
    }

    private static String a(int i2, int i3) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StringBuffer stringBuffer = new StringBuffer();
        int i4 = i3 + i2;
        while (i2 < i4) {
            stringBuffer.append(a(stackTrace, i2));
            stringBuffer.append(UtilsCuentas.SEPARAOR2);
            i2++;
        }
        return stringBuffer.toString();
    }

    private String a(ServiceConnection serviceConnection) {
        return String.valueOf((((long) Process.myPid()) << 32) | ((long) System.identityHashCode(serviceConnection)));
    }

    private static String a(StackTraceElement[] stackTraceElementArr, int i2) {
        int i3 = i2 + 4;
        if (i3 >= stackTraceElementArr.length) {
            return "<bottom of call stack>";
        }
        StackTraceElement stackTraceElement = stackTraceElementArr[i3];
        String valueOf = String.valueOf(stackTraceElement.getClassName());
        String valueOf2 = String.valueOf(stackTraceElement.getMethodName());
        int lineNumber = stackTraceElement.getLineNumber();
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 13 + String.valueOf(valueOf2).length());
        sb.append(valueOf);
        sb.append(".");
        sb.append(valueOf2);
        sb.append(":");
        sb.append(lineNumber);
        return sb.toString();
    }

    private void a(Context context, String str, int i2, String str2, String str3, String str4, String str5) {
        int i3 = i2;
        long currentTimeMillis = System.currentTimeMillis();
        String a2 = ((b() & zzd.Er) == 0 || i3 == 13) ? null : a(3, 5);
        long j = 0;
        if ((b() & zzd.Et) != 0) {
            j = Debug.getNativeHeapAllocatedSize();
        }
        long j2 = j;
        context.startService(new Intent().setComponent(zzd.En).putExtra("com.google.android.gms.common.stats.EXTRA_LOG_EVENT", (i3 == 1 || i3 == 4 || i3 == 14) ? new ConnectionEvent(currentTimeMillis, i3, null, null, null, null, a2, str, SystemClock.elapsedRealtime(), j2) : new ConnectionEvent(currentTimeMillis, i3, str2, str3, str4, str5, a2, str, SystemClock.elapsedRealtime(), j2)));
    }

    private void a(Context context, String str, String str2, Intent intent, int i2) {
        String str3;
        String str4;
        String str5;
        if (a() && this.g != null) {
            if (i2 != 4 && i2 != 1) {
                ServiceInfo b2 = b(context, intent);
                if (b2 == null) {
                    Log.w("ConnectionTracker", String.format("Client %s made an invalid request %s", new Object[]{str2, intent.toUri(0)}));
                    return;
                }
                String str6 = b2.processName;
                String str7 = b2.name;
                String zzaxx = zzt.zzaxx();
                if (a(zzaxx, str2, str6, str7)) {
                    this.g.zzif(str);
                    str4 = str6;
                    str5 = zzaxx;
                    str3 = str7;
                } else {
                    return;
                }
            } else if (this.g.zzig(str)) {
                str5 = null;
                str4 = null;
                str3 = null;
            } else {
                return;
            }
            a(context, str, i2, str5, str2, str4, str3);
        }
    }

    private boolean a() {
        return false;
    }

    private boolean a(Context context, Intent intent) {
        ComponentName component = intent.getComponent();
        if (component == null) {
            return false;
        }
        return zzd.zzx(context, component.getPackageName());
    }

    private boolean a(String str, String str2, String str3, String str4) {
        return !this.c.contains(str) && !this.d.contains(str2) && !this.e.contains(str3) && !this.f.contains(str4) && (!str3.equals(str) || (zzd.Es & b()) == 0);
    }

    private static int b() {
        if (h == null) {
            try {
                h = Integer.valueOf(zzd.zzact() ? ((Integer) zza.Eh.get()).intValue() : zzd.LOG_LEVEL_OFF);
            } catch (SecurityException unused) {
                h = Integer.valueOf(zzd.LOG_LEVEL_OFF);
            }
        }
        return h.intValue();
    }

    private static ServiceInfo b(Context context, Intent intent) {
        List<ResolveInfo> queryIntentServices = context.getPackageManager().queryIntentServices(intent, 128);
        if (queryIntentServices == null || queryIntentServices.size() == 0) {
            Log.w("ConnectionTracker", String.format("There are no handler of this intent: %s\n Stack trace: %s", new Object[]{intent.toUri(0), a(3, 20)}));
            return null;
        } else if (queryIntentServices.size() <= 1) {
            return ((ResolveInfo) queryIntentServices.get(0)).serviceInfo;
        } else {
            Log.w("ConnectionTracker", String.format("Multiple handlers found for this intent: %s\n Stack trace: %s", new Object[]{intent.toUri(0), a(3, 20)}));
            for (ResolveInfo resolveInfo : queryIntentServices) {
                Log.w("ConnectionTracker", resolveInfo.serviceInfo.name);
            }
            return null;
        }
    }

    public static zzb zzawu() {
        synchronized (a) {
            if (b == null) {
                b = new zzb();
            }
        }
        return b;
    }

    @SuppressLint({"UntrackedBindService"})
    public void zza(Context context, ServiceConnection serviceConnection) {
        context.unbindService(serviceConnection);
        a(context, a(serviceConnection), null, null, 1);
    }

    public void zza(Context context, ServiceConnection serviceConnection, String str, Intent intent) {
        a(context, a(serviceConnection), str, intent, 3);
    }

    public boolean zza(Context context, Intent intent, ServiceConnection serviceConnection, int i2) {
        return zza(context, context.getClass().getName(), intent, serviceConnection, i2);
    }

    @SuppressLint({"UntrackedBindService"})
    public boolean zza(Context context, String str, Intent intent, ServiceConnection serviceConnection, int i2) {
        if (a(context, intent)) {
            Log.w("ConnectionTracker", "Attempted to bind to a service in a STOPPED package.");
            return false;
        }
        boolean bindService = context.bindService(intent, serviceConnection, i2);
        if (bindService) {
            a(context, a(serviceConnection), str, intent, 2);
        }
        return bindService;
    }

    public void zzb(Context context, ServiceConnection serviceConnection) {
        a(context, a(serviceConnection), null, null, 4);
    }
}
