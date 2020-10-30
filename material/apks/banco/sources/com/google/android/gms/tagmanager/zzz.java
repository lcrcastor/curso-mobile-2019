package com.google.android.gms.tagmanager;

import android.util.Log;

public class zzz implements zzbp {
    private int a = 5;

    public void e(String str) {
        if (this.a <= 6) {
            Log.e("GoogleTagManager", str);
        }
    }

    public void setLogLevel(int i) {
        this.a = i;
    }

    public void v(String str) {
        if (this.a <= 2) {
            Log.v("GoogleTagManager", str);
        }
    }

    public void zzb(String str, Throwable th) {
        if (this.a <= 6) {
            Log.e("GoogleTagManager", str, th);
        }
    }

    public void zzd(String str, Throwable th) {
        if (this.a <= 5) {
            Log.w("GoogleTagManager", str, th);
        }
    }

    public void zzdd(String str) {
        if (this.a <= 3) {
            Log.d("GoogleTagManager", str);
        }
    }

    public void zzde(String str) {
        if (this.a <= 4) {
            Log.i("GoogleTagManager", str);
        }
    }

    public void zzdf(String str) {
        if (this.a <= 5) {
            Log.w("GoogleTagManager", str);
        }
    }
}
