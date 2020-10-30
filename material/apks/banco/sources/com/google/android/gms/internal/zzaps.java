package com.google.android.gms.internal;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public final class zzaps implements zzaou {
    private final zzapb a;
    private final zzaoa b;
    private final zzapc c;

    public static final class zza<T> extends zzaot<T> {
        private final zzapg<T> a;
        private final Map<String, zzb> b;

        private zza(zzapg<T> zzapg, Map<String, zzb> map) {
            this.a = zzapg;
            this.b = map;
        }

        public void zza(zzaqa zzaqa, T t) {
            if (t == null) {
                zzaqa.bx();
                return;
            }
            zzaqa.bv();
            try {
                for (zzb zzb : this.b.values()) {
                    if (zzb.a(t)) {
                        zzaqa.zzus(zzb.g);
                        zzb.a(zzaqa, (Object) t);
                    }
                }
                zzaqa.bw();
            } catch (IllegalAccessException unused) {
                throw new AssertionError();
            }
        }

        public T zzb(zzapy zzapy) {
            if (zzapy.bn() == zzapz.NULL) {
                zzapy.nextNull();
                return null;
            }
            T bg = this.a.bg();
            try {
                zzapy.beginObject();
                while (zzapy.hasNext()) {
                    zzb zzb = (zzb) this.b.get(zzapy.nextName());
                    if (zzb != null) {
                        if (zzb.i) {
                            zzb.a(zzapy, (Object) bg);
                        }
                    }
                    zzapy.skipValue();
                }
                zzapy.endObject();
                return bg;
            } catch (IllegalStateException e) {
                throw new zzaoq((Throwable) e);
            } catch (IllegalAccessException e2) {
                throw new AssertionError(e2);
            }
        }
    }

    static abstract class zzb {
        final String g;
        final boolean h;
        final boolean i;

        protected zzb(String str, boolean z, boolean z2) {
            this.g = str;
            this.h = z;
            this.i = z2;
        }

        /* access modifiers changed from: 0000 */
        public abstract void a(zzapy zzapy, Object obj);

        /* access modifiers changed from: 0000 */
        public abstract void a(zzaqa zzaqa, Object obj);

        /* access modifiers changed from: 0000 */
        public abstract boolean a(Object obj);
    }

    public zzaps(zzapb zzapb, zzaoa zzaoa, zzapc zzapc) {
        this.a = zzapb;
        this.b = zzaoa;
        this.c = zzapc;
    }

    /* access modifiers changed from: private */
    public zzaot<?> a(zzaob zzaob, Field field, zzapx<?> zzapx) {
        zzaov zzaov = (zzaov) field.getAnnotation(zzaov.class);
        if (zzaov != null) {
            zzaot<?> a2 = zzapn.a(this.a, zzaob, zzapx, zzaov);
            if (a2 != null) {
                return a2;
            }
        }
        return zzaob.zza(zzapx);
    }

    private zzb a(zzaob zzaob, Field field, String str, zzapx<?> zzapx, boolean z, boolean z2) {
        final boolean zzk = zzaph.zzk(zzapx.by());
        final zzaob zzaob2 = zzaob;
        final Field field2 = field;
        final zzapx<?> zzapx2 = zzapx;
        AnonymousClass1 r1 = new zzb(str, z, z2) {
            final zzaot<?> a = zzaps.this.a(zzaob2, field2, zzapx2);

            /* access modifiers changed from: 0000 */
            public void a(zzapy zzapy, Object obj) {
                Object zzb = this.a.zzb(zzapy);
                if (zzb != null || !zzk) {
                    field2.set(obj, zzb);
                }
            }

            /* access modifiers changed from: 0000 */
            public void a(zzaqa zzaqa, Object obj) {
                new zzapv(zzaob2, this.a, zzapx2.bz()).zza(zzaqa, field2.get(obj));
            }

            public boolean a(Object obj) {
                boolean z = false;
                if (!this.h) {
                    return false;
                }
                if (field2.get(obj) != obj) {
                    z = true;
                }
                return z;
            }
        };
        return r1;
    }

    static List<String> a(zzaoa zzaoa, Field field) {
        zzaow zzaow = (zzaow) field.getAnnotation(zzaow.class);
        LinkedList linkedList = new LinkedList();
        if (zzaow == null) {
            linkedList.add(zzaoa.zzc(field));
            return linkedList;
        }
        linkedList.add(zzaow.value());
        for (String add : zzaow.be()) {
            linkedList.add(add);
        }
        return linkedList;
    }

    private List<String> a(Field field) {
        return a(this.b, field);
    }

    private Map<String, zzb> a(zzaob zzaob, zzapx<?> zzapx, Class<?> cls) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        if (cls.isInterface()) {
            return linkedHashMap;
        }
        Type bz = zzapx.bz();
        zzapx<?> zzapx2 = zzapx;
        Class<?> cls2 = cls;
        while (cls2 != Object.class) {
            Field[] declaredFields = cls2.getDeclaredFields();
            int length = declaredFields.length;
            boolean z = false;
            int i = 0;
            while (i < length) {
                Field field = declaredFields[i];
                boolean zza2 = zza(field, true);
                boolean zza3 = zza(field, z);
                if (zza2 || zza3) {
                    field.setAccessible(true);
                    Type zza4 = zzapa.zza(zzapx2.bz(), cls2, field.getGenericType());
                    List a2 = a(field);
                    zzb zzb2 = null;
                    int i2 = 0;
                    while (i2 < a2.size()) {
                        String str = (String) a2.get(i2);
                        boolean z2 = i2 != 0 ? false : zza2;
                        String str2 = str;
                        zzb zzb3 = zzb2;
                        int i3 = i2;
                        List list = a2;
                        Type type = zza4;
                        Field field2 = field;
                        zzb2 = zzb3 == null ? (zzb) linkedHashMap.put(str2, a(zzaob, field, str2, zzapx.zzl(zza4), z2, zza3)) : zzb3;
                        i2 = i3 + 1;
                        zza2 = z2;
                        zza4 = type;
                        a2 = list;
                        field = field2;
                    }
                    zzb zzb4 = zzb2;
                    if (zzb4 != null) {
                        String valueOf = String.valueOf(bz);
                        String str3 = zzb4.g;
                        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 37 + String.valueOf(str3).length());
                        sb.append(valueOf);
                        sb.append(" declares multiple JSON fields named ");
                        sb.append(str3);
                        throw new IllegalArgumentException(sb.toString());
                    }
                }
                i++;
                z = false;
            }
            zzapx2 = zzapx.zzl(zzapa.zza(zzapx2.bz(), cls2, cls2.getGenericSuperclass()));
            cls2 = zzapx2.by();
        }
        return linkedHashMap;
    }

    static boolean a(Field field, boolean z, zzapc zzapc) {
        return !zzapc.zza(field.getType(), z) && !zzapc.zza(field, z);
    }

    public <T> zzaot<T> zza(zzaob zzaob, zzapx<T> zzapx) {
        Class by = zzapx.by();
        if (!Object.class.isAssignableFrom(by)) {
            return null;
        }
        return new zza(this.a.zzb(zzapx), a(zzaob, zzapx, by));
    }

    public boolean zza(Field field, boolean z) {
        return a(field, z, this.c);
    }
}
