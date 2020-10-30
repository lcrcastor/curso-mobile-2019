package io.fabric.sdk.android.services.common;

import android.os.SystemClock;
import android.util.Log;

public class TimingMetric {
    private final String a;
    private final String b;
    private final boolean c;
    private long d;
    private long e;

    public TimingMetric(String str, String str2) {
        this.a = str;
        this.b = str2;
        this.c = !Log.isLoggable(str2, 2);
    }

    public synchronized void startMeasuring() {
        if (!this.c) {
            this.d = SystemClock.elapsedRealtime();
            this.e = 0;
        }
    }

    public synchronized void stopMeasuring() {
        if (!this.c) {
            if (this.e == 0) {
                this.e = SystemClock.elapsedRealtime() - this.d;
                a();
            }
        }
    }

    public long getDuration() {
        return this.e;
    }

    private void a() {
        String str = this.b;
        StringBuilder sb = new StringBuilder();
        sb.append(this.a);
        sb.append(": ");
        sb.append(this.e);
        sb.append("ms");
        Log.v(str, sb.toString());
    }
}
