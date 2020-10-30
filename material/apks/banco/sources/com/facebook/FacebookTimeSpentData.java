package com.facebook;

import android.os.Bundle;
import com.facebook.internal.Logger;
import java.io.Serializable;

class FacebookTimeSpentData implements Serializable {
    private static final String a = AppEventsLogger.class.getCanonicalName();
    private static final long[] b = {300000, 900000, 1800000, 3600000, 21600000, 43200000, 86400000, 172800000, 259200000, 604800000, 1209600000, 1814400000, 2419200000L, 5184000000L, 7776000000L, 10368000000L, 12960000000L, 15552000000L, 31536000000L};
    private static final long serialVersionUID = 1;
    private boolean c;
    private boolean d;
    private long e;
    private long f;
    private long g;
    private long h;
    private int i;
    private String j;

    static class SerializationProxyV2 implements Serializable {
        private static final long serialVersionUID = 6;
        private final long a;
        private final long b;
        private final long c;
        private final int d;
        private final String e;

        SerializationProxyV2(long j, long j2, long j3, int i, String str) {
            this.a = j;
            this.b = j2;
            this.c = j3;
            this.d = i;
            this.e = str;
        }

        private Object readResolve() {
            FacebookTimeSpentData facebookTimeSpentData = new FacebookTimeSpentData(this.a, this.b, this.c, this.d, this.e);
            return facebookTimeSpentData;
        }
    }

    FacebookTimeSpentData() {
        a();
    }

    private FacebookTimeSpentData(long j2, long j3, long j4, int i2, String str) {
        a();
        this.f = j2;
        this.g = j3;
        this.h = j4;
        this.i = i2;
        this.j = str;
    }

    private Object writeReplace() {
        SerializationProxyV2 serializationProxyV2 = new SerializationProxyV2(this.f, this.g, this.h, this.i, this.j);
        return serializationProxyV2;
    }

    /* access modifiers changed from: 0000 */
    public void a(AppEventsLogger appEventsLogger, long j2) {
        if (!this.d) {
            Logger.log(LoggingBehavior.APP_EVENTS, a, "Suspend for inactive app");
            return;
        }
        long j3 = j2 - this.f;
        long j4 = 0;
        if (j3 < 0) {
            Logger.log(LoggingBehavior.APP_EVENTS, a, "Clock skew detected");
        } else {
            j4 = j3;
        }
        this.h += j4;
        this.g = j2;
        this.d = false;
    }

    /* access modifiers changed from: 0000 */
    public void a(AppEventsLogger appEventsLogger, long j2, String str) {
        if (c() || j2 - this.e > 300000) {
            Bundle bundle = new Bundle();
            bundle.putString(AppEventsConstants.EVENT_PARAM_SOURCE_APPLICATION, str);
            appEventsLogger.logEvent(AppEventsConstants.EVENT_NAME_ACTIVATED_APP, bundle);
            this.e = j2;
        }
        if (this.d) {
            Logger.log(LoggingBehavior.APP_EVENTS, a, "Resume for active app");
            return;
        }
        long j3 = 0;
        long j4 = b() ? j2 - this.g : 0;
        if (j4 < 0) {
            Logger.log(LoggingBehavior.APP_EVENTS, a, "Clock skew detected");
        } else {
            j3 = j4;
        }
        if (j3 > 60000) {
            b(appEventsLogger, j3);
        } else if (j3 > 1000) {
            this.i++;
        }
        if (this.i == 0) {
            this.j = str;
        }
        this.f = j2;
        this.d = true;
    }

    private void b(AppEventsLogger appEventsLogger, long j2) {
        Bundle bundle = new Bundle();
        bundle.putInt(AppEventsConstants.EVENT_NAME_SESSION_INTERRUPTIONS, this.i);
        bundle.putString(AppEventsConstants.EVENT_NAME_TIME_BETWEEN_SESSIONS, String.format("session_quanta_%d", new Object[]{Integer.valueOf(a(j2))}));
        bundle.putString(AppEventsConstants.EVENT_PARAM_SOURCE_APPLICATION, this.j);
        appEventsLogger.logEvent(AppEventsConstants.EVENT_NAME_DEACTIVATED_APP, (double) (this.h / 1000), bundle);
        a();
    }

    private static int a(long j2) {
        int i2 = 0;
        while (i2 < b.length && b[i2] < j2) {
            i2++;
        }
        return i2;
    }

    private void a() {
        this.d = false;
        this.f = -1;
        this.g = -1;
        this.i = 0;
        this.h = 0;
    }

    private boolean b() {
        return this.g != -1;
    }

    private boolean c() {
        boolean z = !this.c;
        this.c = true;
        return z;
    }
}
