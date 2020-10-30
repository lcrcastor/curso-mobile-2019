package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Logger;
import com.google.android.gms.analytics.Tracker;

public class zzdj {
    private GoogleAnalytics a;
    private Context b;
    private Tracker c;

    static class zza implements Logger {
        zza() {
        }

        private static int a(int i) {
            switch (i) {
                case 2:
                    return 0;
                case 3:
                case 4:
                    return 1;
                case 5:
                    return 2;
                default:
                    return 3;
            }
        }

        public void error(Exception exc) {
            zzbo.zzb("", exc);
        }

        public void error(String str) {
            zzbo.e(str);
        }

        public int getLogLevel() {
            return a(zzbo.getLogLevel());
        }

        public void info(String str) {
            zzbo.zzde(str);
        }

        public void setLogLevel(int i) {
            zzbo.zzdf("GA uses GTM logger. Please use TagManager.setLogLevel(int) instead.");
        }

        public void verbose(String str) {
            zzbo.v(str);
        }

        public void warn(String str) {
            zzbo.zzdf(str);
        }
    }

    public zzdj(Context context) {
        this.b = context;
    }

    private synchronized void a(String str) {
        if (this.a == null) {
            this.a = GoogleAnalytics.getInstance(this.b);
            this.a.setLogger(new zza());
            this.c = this.a.newTracker(str);
        }
    }

    public Tracker zzpv(String str) {
        a(str);
        return this.c;
    }
}
