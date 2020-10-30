package com.google.android.gms.analytics.internal;

import android.app.Activity;
import java.util.HashMap;
import java.util.Map;

public class zzan implements zzp {
    public double dJ = -1.0d;
    public int dK = -1;
    public int dL = -1;
    public int dM = -1;
    public int dN = -1;
    public Map<String, String> dO = new HashMap();
    public String zzcyj;

    public int getSessionTimeout() {
        return this.dK;
    }

    public String getTrackingId() {
        return this.zzcyj;
    }

    public boolean zzafs() {
        return this.zzcyj != null;
    }

    public boolean zzaft() {
        return this.dJ >= 0.0d;
    }

    public double zzafu() {
        return this.dJ;
    }

    public boolean zzafv() {
        return this.dK >= 0;
    }

    public boolean zzafw() {
        return this.dL != -1;
    }

    public boolean zzafx() {
        return this.dL == 1;
    }

    public boolean zzafy() {
        return this.dM != -1;
    }

    public boolean zzafz() {
        return this.dM == 1;
    }

    public boolean zzaga() {
        return this.dN == 1;
    }

    public String zzfe(String str) {
        String str2 = (String) this.dO.get(str);
        return str2 != null ? str2 : str;
    }

    public String zzr(Activity activity) {
        return zzfe(activity.getClass().getCanonicalName());
    }
}
