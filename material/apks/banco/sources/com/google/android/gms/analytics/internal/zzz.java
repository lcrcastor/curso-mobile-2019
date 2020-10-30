package com.google.android.gms.analytics.internal;

public class zzz extends zzq<zzaa> {

    static class zza implements com.google.android.gms.analytics.internal.zzq.zza<zzaa> {
        private final zzf a;
        private final zzaa b = new zzaa();

        public zza(zzf zzf) {
            this.a = zzf;
        }

        /* renamed from: a */
        public zzaa zzacs() {
            return this.b;
        }

        public void zzd(String str, int i) {
            if ("ga_dispatchPeriod".equals(str)) {
                this.b.cZ = i;
            } else {
                this.a.zzaao().zzd("Int xml configuration name not recognized", str);
            }
        }

        public void zzg(String str, boolean z) {
            if ("ga_dryRun".equals(str)) {
                this.b.da = z ? 1 : 0;
                return;
            }
            this.a.zzaao().zzd("Bool xml configuration name not recognized", str);
        }

        public void zzp(String str, String str2) {
        }

        public void zzq(String str, String str2) {
            if ("ga_appName".equals(str)) {
                this.b.F = str2;
            } else if ("ga_appVersion".equals(str)) {
                this.b.G = str2;
            } else if ("ga_logLevel".equals(str)) {
                this.b.cY = str2;
            } else {
                this.a.zzaao().zzd("String xml configuration name not recognized", str);
            }
        }
    }

    public zzz(zzf zzf) {
        super(zzf, new zza(zzf));
    }
}
