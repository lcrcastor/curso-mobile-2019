package com.google.android.gms.internal;

import java.util.ArrayList;

public final class zzapr extends zzaot<Object> {
    public static final zzaou bmp = new zzaou() {
        public <T> zzaot<T> zza(zzaob zzaob, zzapx<T> zzapx) {
            if (zzapx.by() == Object.class) {
                return new zzapr(zzaob);
            }
            return null;
        }
    };
    private final zzaob a;

    private zzapr(zzaob zzaob) {
        this.a = zzaob;
    }

    public void zza(zzaqa zzaqa, Object obj) {
        if (obj == null) {
            zzaqa.bx();
            return;
        }
        zzaot zzk = this.a.zzk(obj.getClass());
        if (zzk instanceof zzapr) {
            zzaqa.bv();
            zzaqa.bw();
            return;
        }
        zzk.zza(zzaqa, obj);
    }

    public Object zzb(zzapy zzapy) {
        switch (zzapy.bn()) {
            case BEGIN_ARRAY:
                ArrayList arrayList = new ArrayList();
                zzapy.beginArray();
                while (zzapy.hasNext()) {
                    arrayList.add(zzb(zzapy));
                }
                zzapy.endArray();
                return arrayList;
            case BEGIN_OBJECT:
                zzapf zzapf = new zzapf();
                zzapy.beginObject();
                while (zzapy.hasNext()) {
                    zzapf.put(zzapy.nextName(), zzb(zzapy));
                }
                zzapy.endObject();
                return zzapf;
            case STRING:
                return zzapy.nextString();
            case NUMBER:
                return Double.valueOf(zzapy.nextDouble());
            case BOOLEAN:
                return Boolean.valueOf(zzapy.nextBoolean());
            case NULL:
                zzapy.nextNull();
                return null;
            default:
                throw new IllegalStateException();
        }
    }
}
