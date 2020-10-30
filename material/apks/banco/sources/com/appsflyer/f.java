package com.appsflyer;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class f {
    private static final BitSet g = new BitSet(6);
    private static final Handler h = new Handler(Looper.getMainLooper());
    private static volatile f i;
    final Handler a;
    final Object b = new Object();
    boolean c;
    final Runnable d = new AnonymousClass5();
    final Runnable e = new Runnable() {
        public final void run() {
            synchronized (f.this.b) {
                f.this.a();
                f.this.a.postDelayed(f.this.d, 500);
                f.this.c = true;
            }
        }
    };
    final Runnable f = new Runnable() {
        public final void run() {
            synchronized (f.this.b) {
                if (f.this.c) {
                    f.this.a.removeCallbacks(f.this.e);
                    f.this.a.removeCallbacks(f.this.d);
                    f.this.b();
                    f.this.c = false;
                }
            }
        }
    };
    private final Map<h, h> j = new HashMap(g.size());
    private final Map<h, Map<String, Object>> k = new HashMap(g.size());
    private final SensorManager l;
    private boolean m;

    /* renamed from: com.appsflyer.f$5 reason: invalid class name */
    class AnonymousClass5 implements Runnable {
        private static String b;
        private static String c;

        AnonymousClass5() {
        }

        public final void run() {
            synchronized (f.this.b) {
                f.this.b();
                f.this.a.postDelayed(f.this.e, 1800000);
            }
        }

        AnonymousClass5() {
        }

        static void a(String str) {
            b = str;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < str.length(); i++) {
                if (i == 0 || i == str.length() - 1) {
                    sb.append(str.charAt(i));
                } else {
                    sb.append("*");
                }
            }
            c = sb.toString();
        }

        static void b(String str) {
            if (b == null) {
                a(AppsFlyerProperties.getInstance().getString(AppsFlyerProperties.AF_KEY));
            }
            if (b != null && str.contains(b)) {
                AFLogger.afInfoLog(str.replace(b, c));
            }
        }
    }

    static {
        g.set(1);
        g.set(2);
        g.set(4);
    }

    private f(@NonNull SensorManager sensorManager, Handler handler) {
        this.l = sensorManager;
        this.a = handler;
    }

    static f a(Context context) {
        return a((SensorManager) context.getApplicationContext().getSystemService("sensor"), h);
    }

    private static f a(SensorManager sensorManager, Handler handler) {
        if (i == null) {
            synchronized (f.class) {
                if (i == null) {
                    i = new f(sensorManager, handler);
                }
            }
        }
        return i;
    }

    /* access modifiers changed from: 0000 */
    public final void a() {
        try {
            for (Sensor sensor : this.l.getSensorList(-1)) {
                int type = sensor.getType();
                if (type >= 0 && g.get(type)) {
                    h a2 = h.a(sensor);
                    if (!this.j.containsKey(a2)) {
                        this.j.put(a2, a2);
                    }
                    this.l.registerListener((SensorEventListener) this.j.get(a2), sensor, 0);
                }
            }
        } catch (Throwable unused) {
        }
        this.m = true;
    }

    /* access modifiers changed from: 0000 */
    public final void b() {
        try {
            if (!this.j.isEmpty()) {
                for (h hVar : this.j.values()) {
                    this.l.unregisterListener(hVar);
                    hVar.a(this.k);
                }
            }
        } catch (Throwable unused) {
        }
        this.m = false;
    }

    /* access modifiers changed from: 0000 */
    @NonNull
    public final List<Map<String, Object>> c() {
        synchronized (this.b) {
            if (!this.j.isEmpty() && this.m) {
                for (h b2 : this.j.values()) {
                    b2.b(this.k);
                }
            }
            if (this.k.isEmpty()) {
                List<Map<String, Object>> emptyList = Collections.emptyList();
                return emptyList;
            }
            ArrayList arrayList = new ArrayList(this.k.values());
            return arrayList;
        }
    }
}
