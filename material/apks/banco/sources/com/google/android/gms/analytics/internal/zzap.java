package com.google.android.gms.analytics.internal;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;

public class zzap extends zzd {
    protected String F;
    protected String G;
    protected int bX;
    protected int cZ;
    protected boolean dQ;
    protected boolean dR;
    protected boolean zzcza;

    public zzap(zzf zzf) {
        super(zzf);
    }

    private static int a(String str) {
        String lowerCase = str.toLowerCase();
        if ("verbose".equals(lowerCase)) {
            return 0;
        }
        if ("info".equals(lowerCase)) {
            return 1;
        }
        if ("warning".equals(lowerCase)) {
            return 2;
        }
        return "error".equals(lowerCase) ? 3 : -1;
    }

    /* access modifiers changed from: 0000 */
    public void a(zzaa zzaa) {
        zzep("Loading global XML config values");
        if (zzaa.zzaeg()) {
            String zzys = zzaa.zzys();
            this.F = zzys;
            zzb("XML config - app name", zzys);
        }
        if (zzaa.zzaeh()) {
            String zzyt = zzaa.zzyt();
            this.G = zzyt;
            zzb("XML config - app version", zzyt);
        }
        if (zzaa.zzaei()) {
            int a = a(zzaa.zzaej());
            if (a >= 0) {
                this.bX = a;
                zza("XML config - log level", Integer.valueOf(a));
            }
        }
        if (zzaa.zzaek()) {
            int zzael = zzaa.zzael();
            this.cZ = zzael;
            this.dQ = true;
            zzb("XML config - dispatch period (sec)", Integer.valueOf(zzael));
        }
        if (zzaa.zzaem()) {
            boolean zzaen = zzaa.zzaen();
            this.zzcza = zzaen;
            this.dR = true;
            zzb("XML config - dry run", Boolean.valueOf(zzaen));
        }
    }

    public int getLogLevel() {
        zzaax();
        return this.bX;
    }

    public boolean zzaei() {
        zzaax();
        return false;
    }

    public boolean zzaek() {
        zzaax();
        return this.dQ;
    }

    public boolean zzaem() {
        zzaax();
        return this.dR;
    }

    public boolean zzaen() {
        zzaax();
        return this.zzcza;
    }

    public int zzagb() {
        zzaax();
        return this.cZ;
    }

    /* access modifiers changed from: protected */
    public void zzagc() {
        ApplicationInfo applicationInfo;
        Context context = getContext();
        try {
            applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 129);
        } catch (NameNotFoundException e) {
            zzd("PackageManager doesn't know about the app package", e);
            applicationInfo = null;
        }
        if (applicationInfo == null) {
            zzes("Couldn't get ApplicationInfo to load global config");
            return;
        }
        Bundle bundle = applicationInfo.metaData;
        if (bundle != null) {
            int i = bundle.getInt("com.google.android.gms.analytics.globalConfigResource");
            if (i > 0) {
                zzaa zzaa = (zzaa) new zzz(zzaal()).zzcd(i);
                if (zzaa != null) {
                    a(zzaa);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void zzym() {
        zzagc();
    }

    public String zzys() {
        zzaax();
        return this.F;
    }

    public String zzyt() {
        zzaax();
        return this.G;
    }
}
