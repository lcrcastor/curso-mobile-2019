package com.google.android.gms.internal;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class zzaoc {
    private zzapc a = zzapc.blF;
    private zzaor b = zzaor.DEFAULT;
    private zzaoa c = zzanz.IDENTITY;
    private final Map<Type, zzaod<?>> d = new HashMap();
    private final List<zzaou> e = new ArrayList();
    private final List<zzaou> f = new ArrayList();
    private int g = 2;
    private int h = 2;
    private boolean i = true;

    private void a(String str, int i2, int i3, List<zzaou> list) {
        zzanw zzanw;
        if (str != null && !"".equals(str.trim())) {
            zzanw = new zzanw(str);
        } else if (i2 != 2 && i3 != 2) {
            zzanw = new zzanw(i2, i3);
        } else {
            return;
        }
        list.add(zzaos.a(zzapx.zzr(Date.class), zzanw));
        list.add(zzaos.a(zzapx.zzr(Timestamp.class), zzanw));
        list.add(zzaos.a(zzapx.zzr(java.sql.Date.class), zzanw));
    }

    public zzaoc aO() {
        this.i = false;
        return this;
    }

    public zzaob aP() {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(this.e);
        Collections.reverse(arrayList);
        arrayList.addAll(this.f);
        a(null, this.g, this.h, arrayList);
        zzaob zzaob = new zzaob(this.a, this.c, this.d, false, false, false, this.i, false, false, this.b, arrayList);
        return zzaob;
    }

    public zzaoc zza(Type type, Object obj) {
        boolean z = obj instanceof zzaop;
        zzaoz.zzbs(z || (obj instanceof zzaog) || (obj instanceof zzaod) || (obj instanceof zzaot));
        if (obj instanceof zzaod) {
            this.d.put(type, (zzaod) obj);
        }
        if (z || (obj instanceof zzaog)) {
            this.e.add(zzaos.b(zzapx.zzl(type), obj));
        }
        if (obj instanceof zzaot) {
            this.e.add(zzapw.zza(zzapx.zzl(type), (zzaot) obj));
        }
        return this;
    }

    public zzaoc zza(zzanx... zzanxArr) {
        for (zzanx zza : zzanxArr) {
            this.a = this.a.zza(zza, true, true);
        }
        return this;
    }

    public zzaoc zzf(int... iArr) {
        this.a = this.a.zzg(iArr);
        return this;
    }
}
