package com.appsflyer;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class h implements SensorEventListener {
    private final int a;
    @NonNull
    private final String b;
    @NonNull
    private final String c;
    private final float[][] d = new float[2][];
    private final long[] e = new long[2];
    private final int f;
    private double g;
    private long h;

    public final void onAccuracyChanged(Sensor sensor, int i) {
    }

    private h(int i, @Nullable String str, @Nullable String str2) {
        this.a = i;
        if (str == null) {
            str = "";
        }
        this.b = str;
        if (str2 == null) {
            str2 = "";
        }
        this.c = str2;
        this.f = ((((i + 31) * 31) + this.b.hashCode()) * 31) + this.c.hashCode();
    }

    static h a(Sensor sensor) {
        return new h(sensor.getType(), sensor.getName(), sensor.getVendor());
    }

    private static double a(@NonNull float[] fArr, @NonNull float[] fArr2) {
        double d2 = 0.0d;
        for (int i = 0; i < Math.min(fArr.length, fArr2.length); i++) {
            d2 += StrictMath.pow((double) (fArr[i] - fArr2[i]), 2.0d);
        }
        return Math.sqrt(d2);
    }

    @NonNull
    private static List<Float> a(@NonNull float[] fArr) {
        ArrayList arrayList = new ArrayList(fArr.length);
        for (float valueOf : fArr) {
            arrayList.add(Float.valueOf(valueOf));
        }
        return arrayList;
    }

    public final void onSensorChanged(SensorEvent sensorEvent) {
        if (!(sensorEvent == null || sensorEvent.values == null)) {
            Sensor sensor = sensorEvent.sensor;
            if ((sensor == null || sensor.getName() == null || sensor.getVendor() == null) ? false : true) {
                int type = sensorEvent.sensor.getType();
                String name = sensorEvent.sensor.getName();
                String vendor = sensorEvent.sensor.getVendor();
                long j = sensorEvent.timestamp;
                float[] fArr = sensorEvent.values;
                if (a(type, name, vendor)) {
                    long currentTimeMillis = System.currentTimeMillis();
                    float[] fArr2 = this.d[0];
                    if (fArr2 == null) {
                        this.d[0] = Arrays.copyOf(fArr, fArr.length);
                        this.e[0] = currentTimeMillis;
                        return;
                    }
                    float[] fArr3 = this.d[1];
                    if (fArr3 == null) {
                        float[] copyOf = Arrays.copyOf(fArr, fArr.length);
                        this.d[1] = copyOf;
                        this.e[1] = currentTimeMillis;
                        this.g = a(fArr2, copyOf);
                    } else if (50000000 <= j - this.h) {
                        this.h = j;
                        if (Arrays.equals(fArr3, fArr)) {
                            this.e[1] = currentTimeMillis;
                            return;
                        }
                        double a2 = a(fArr2, fArr);
                        if (a2 > this.g) {
                            this.d[1] = Arrays.copyOf(fArr, fArr.length);
                            this.e[1] = currentTimeMillis;
                            this.g = a2;
                        }
                    }
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final void a(@NonNull Map<h, Map<String, Object>> map) {
        a(map, true);
    }

    public final void b(Map<h, Map<String, Object>> map) {
        a(map, false);
    }

    private boolean a(int i, @NonNull String str, @NonNull String str2) {
        return this.a == i && this.b.equals(str) && this.c.equals(str2);
    }

    @NonNull
    private Map<String, Object> a() {
        HashMap hashMap = new HashMap(7);
        hashMap.put("sT", Integer.valueOf(this.a));
        hashMap.put("sN", this.b);
        hashMap.put("sV", this.c);
        float[] fArr = this.d[0];
        if (fArr != null) {
            hashMap.put("sVS", a(fArr));
        }
        float[] fArr2 = this.d[1];
        if (fArr2 != null) {
            hashMap.put("sVE", a(fArr2));
        }
        return hashMap;
    }

    private void b() {
        for (int i = 0; i < 2; i++) {
            this.d[i] = null;
        }
        for (int i2 = 0; i2 < 2; i2++) {
            this.e[i2] = 0;
        }
        this.g = 0.0d;
        this.h = 0;
    }

    public final int hashCode() {
        return this.f;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof h)) {
            return false;
        }
        h hVar = (h) obj;
        return a(hVar.a, hVar.b, hVar.c);
    }

    private void a(@NonNull Map<h, Map<String, Object>> map, boolean z) {
        boolean z2 = false;
        if (this.d[0] != null) {
            z2 = true;
        }
        if (z2) {
            map.put(this, a());
            if (z) {
                b();
            }
        } else if (!map.containsKey(this)) {
            map.put(this, a());
        }
    }
}
