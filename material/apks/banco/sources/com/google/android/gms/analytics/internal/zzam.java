package com.google.android.gms.analytics.internal;

public class zzam extends zzq<zzan> {

    static class zza extends zzc implements com.google.android.gms.analytics.internal.zzq.zza<zzan> {
        private final zzan a = new zzan();

        public zza(zzf zzf) {
            super(zzf);
        }

        /* renamed from: a */
        public zzan zzacs() {
            return this.a;
        }

        public void zzd(String str, int i) {
            if ("ga_sessionTimeout".equals(str)) {
                this.a.dK = i;
            } else {
                zzd("int configuration name not recognized", str);
            }
        }

        public void zzg(String str, boolean z) {
            if ("ga_autoActivityTracking".equals(str)) {
                this.a.dL = z;
            } else if ("ga_anonymizeIp".equals(str)) {
                this.a.dM = z;
            } else if ("ga_reportUncaughtExceptions".equals(str)) {
                this.a.dN = z ? 1 : 0;
            } else {
                zzd("bool configuration name not recognized", str);
            }
        }

        public void zzp(String str, String str2) {
            this.a.dO.put(str, str2);
        }

        public void zzq(String str, String str2) {
            if ("ga_trackingId".equals(str)) {
                this.a.zzcyj = str2;
            } else if ("ga_sampleFrequency".equals(str)) {
                try {
                    this.a.dJ = Double.parseDouble(str2);
                } catch (NumberFormatException e) {
                    zzc("Error parsing ga_sampleFrequency value", str2, e);
                }
            } else {
                zzd("string configuration name not recognized", str);
            }
        }
    }

    public zzam(zzf zzf) {
        super(zzf, new zza(zzf));
    }
}
