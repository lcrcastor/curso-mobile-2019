package com.google.android.gms.analytics.internal;

import android.content.pm.ApplicationInfo;
import android.text.TextUtils;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.util.zzt;
import java.util.HashSet;
import java.util.Set;

public class zzr {
    private final zzf a;
    private volatile Boolean b;
    private String c;
    private Set<Integer> d;

    protected zzr(zzf zzf) {
        zzac.zzy(zzf);
        this.a = zzf;
    }

    public boolean zzact() {
        return false;
    }

    public boolean zzacu() {
        if (this.b == null) {
            synchronized (this) {
                if (this.b == null) {
                    ApplicationInfo applicationInfo = this.a.getContext().getApplicationInfo();
                    String zzaxy = zzt.zzaxy();
                    if (applicationInfo != null) {
                        String str = applicationInfo.processName;
                        this.b = Boolean.valueOf(str != null && str.equals(zzaxy));
                    }
                    if ((this.b == null || !this.b.booleanValue()) && "com.google.android.gms.analytics".equals(zzaxy)) {
                        this.b = Boolean.TRUE;
                    }
                    if (this.b == null) {
                        this.b = Boolean.TRUE;
                        this.a.zzaao().zzet("My process not in the list of running processes");
                    }
                }
            }
        }
        return this.b.booleanValue();
    }

    public boolean zzacv() {
        return ((Boolean) zzy.cf.get()).booleanValue();
    }

    public int zzacw() {
        return ((Integer) zzy.cy.get()).intValue();
    }

    public int zzacx() {
        return ((Integer) zzy.cC.get()).intValue();
    }

    public int zzacy() {
        return ((Integer) zzy.cD.get()).intValue();
    }

    public int zzacz() {
        return ((Integer) zzy.cE.get()).intValue();
    }

    public long zzada() {
        return ((Long) zzy.cn.get()).longValue();
    }

    public long zzadb() {
        return ((Long) zzy.cm.get()).longValue();
    }

    public long zzadc() {
        return ((Long) zzy.cq.get()).longValue();
    }

    public long zzadd() {
        return ((Long) zzy.cr.get()).longValue();
    }

    public int zzade() {
        return ((Integer) zzy.cs.get()).intValue();
    }

    public int zzadf() {
        return ((Integer) zzy.ct.get()).intValue();
    }

    public long zzadg() {
        return (long) ((Integer) zzy.cG.get()).intValue();
    }

    public String zzadh() {
        return (String) zzy.cv.get();
    }

    public String zzadi() {
        return (String) zzy.cu.get();
    }

    public String zzadj() {
        return (String) zzy.cw.get();
    }

    public String zzadk() {
        return (String) zzy.cx.get();
    }

    public zzm zzadl() {
        return zzm.zzey((String) zzy.f273cz.get());
    }

    public zzo zzadm() {
        return zzo.zzez((String) zzy.cA.get());
    }

    public Set<Integer> zzadn() {
        String str = (String) zzy.cF.get();
        if (this.d == null || this.c == null || !this.c.equals(str)) {
            String[] split = TextUtils.split(str, ",");
            HashSet hashSet = new HashSet();
            for (String parseInt : split) {
                try {
                    hashSet.add(Integer.valueOf(Integer.parseInt(parseInt)));
                } catch (NumberFormatException unused) {
                }
            }
            this.c = str;
            this.d = hashSet;
        }
        return this.d;
    }

    public long zzado() {
        return ((Long) zzy.cO.get()).longValue();
    }

    public long zzadp() {
        return ((Long) zzy.cP.get()).longValue();
    }

    public long zzadq() {
        return ((Long) zzy.cS.get()).longValue();
    }

    public int zzadr() {
        return ((Integer) zzy.cj.get()).intValue();
    }

    public int zzads() {
        return ((Integer) zzy.cl.get()).intValue();
    }

    public String zzadt() {
        return "google_analytics_v4.db";
    }

    public String zzadu() {
        return "google_analytics2_v4.db";
    }

    public long zzadv() {
        return 86400000;
    }

    public int zzadw() {
        return ((Integer) zzy.cI.get()).intValue();
    }

    public int zzadx() {
        return ((Integer) zzy.cJ.get()).intValue();
    }

    public long zzady() {
        return ((Long) zzy.cK.get()).longValue();
    }

    public long zzadz() {
        return ((Long) zzy.cT.get()).longValue();
    }
}
