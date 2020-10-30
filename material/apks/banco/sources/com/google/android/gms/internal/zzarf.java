package com.google.android.gms.internal;

import com.google.android.gms.internal.zzare;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class zzarf<M extends zzare<M>, T> {
    protected final Class<T> bhd;
    protected final boolean bqw;
    public final int tag;
    protected final int type;

    private zzarf(int i, Class<T> cls, int i2, boolean z) {
        this.type = i;
        this.bhd = cls;
        this.tag = i2;
        this.bqw = z;
    }

    private T b(List<zzarm> list) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            zzarm zzarm = (zzarm) list.get(i);
            if (zzarm.b.length != 0) {
                zza(zzarm, arrayList);
            }
        }
        int size = arrayList.size();
        if (size == 0) {
            return null;
        }
        T cast = this.bhd.cast(Array.newInstance(this.bhd.getComponentType(), size));
        for (int i2 = 0; i2 < size; i2++) {
            Array.set(cast, i2, arrayList.get(i2));
        }
        return cast;
    }

    private T c(List<zzarm> list) {
        if (list.isEmpty()) {
            return null;
        }
        return this.bhd.cast(zzck(zzarc.zzbd(((zzarm) list.get(list.size() - 1)).b)));
    }

    public static <M extends zzare<M>, T extends zzark> zzarf<M, T> zza(int i, Class<T> cls, long j) {
        return new zzarf<>(i, cls, (int) j, false);
    }

    /* access modifiers changed from: 0000 */
    public int a(Object obj) {
        return this.bqw ? zzcv(obj) : zzcw(obj);
    }

    /* access modifiers changed from: 0000 */
    public final T a(List<zzarm> list) {
        if (list == null) {
            return null;
        }
        return this.bqw ? b(list) : c(list);
    }

    /* access modifiers changed from: 0000 */
    public void a(Object obj, zzard zzard) {
        if (this.bqw) {
            zzc(obj, zzard);
        } else {
            zzb(obj, zzard);
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzarf)) {
            return false;
        }
        zzarf zzarf = (zzarf) obj;
        return this.type == zzarf.type && this.bhd == zzarf.bhd && this.tag == zzarf.tag && this.bqw == zzarf.bqw;
    }

    public int hashCode() {
        return ((((((this.type + 1147) * 31) + this.bhd.hashCode()) * 31) + this.tag) * 31) + (this.bqw ? 1 : 0);
    }

    /* access modifiers changed from: protected */
    public void zza(zzarm zzarm, List<Object> list) {
        list.add(zzck(zzarc.zzbd(zzarm.b)));
    }

    /* access modifiers changed from: protected */
    public void zzb(Object obj, zzard zzard) {
        try {
            zzard.zzahm(this.tag);
            switch (this.type) {
                case 10:
                    zzark zzark = (zzark) obj;
                    int zzahu = zzarn.zzahu(this.tag);
                    zzard.zzb(zzark);
                    zzard.zzai(zzahu, 4);
                    return;
                case 11:
                    zzard.zzc((zzark) obj);
                    return;
                default:
                    int i = this.type;
                    StringBuilder sb = new StringBuilder(24);
                    sb.append("Unknown type ");
                    sb.append(i);
                    throw new IllegalArgumentException(sb.toString());
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    /* access modifiers changed from: protected */
    public void zzc(Object obj, zzard zzard) {
        int length = Array.getLength(obj);
        for (int i = 0; i < length; i++) {
            Object obj2 = Array.get(obj, i);
            if (obj2 != null) {
                zzb(obj2, zzard);
            }
        }
    }

    /* access modifiers changed from: protected */
    public Object zzck(zzarc zzarc) {
        Class<T> componentType = this.bqw ? this.bhd.getComponentType() : this.bhd;
        try {
            switch (this.type) {
                case 10:
                    zzark zzark = (zzark) componentType.newInstance();
                    zzarc.zza(zzark, zzarn.zzahu(this.tag));
                    return zzark;
                case 11:
                    zzark zzark2 = (zzark) componentType.newInstance();
                    zzarc.zza(zzark2);
                    return zzark2;
                default:
                    int i = this.type;
                    StringBuilder sb = new StringBuilder(24);
                    sb.append("Unknown type ");
                    sb.append(i);
                    throw new IllegalArgumentException(sb.toString());
            }
        } catch (InstantiationException e) {
            String valueOf = String.valueOf(componentType);
            StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf).length() + 33);
            sb2.append("Error creating instance of class ");
            sb2.append(valueOf);
            throw new IllegalArgumentException(sb2.toString(), e);
        } catch (IllegalAccessException e2) {
            String valueOf2 = String.valueOf(componentType);
            StringBuilder sb3 = new StringBuilder(String.valueOf(valueOf2).length() + 33);
            sb3.append("Error creating instance of class ");
            sb3.append(valueOf2);
            throw new IllegalArgumentException(sb3.toString(), e2);
        } catch (IOException e3) {
            throw new IllegalArgumentException("Error reading extension field", e3);
        }
    }

    /* access modifiers changed from: protected */
    public int zzcv(Object obj) {
        int length = Array.getLength(obj);
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            if (Array.get(obj, i2) != null) {
                i += zzcw(Array.get(obj, i2));
            }
        }
        return i;
    }

    /* access modifiers changed from: protected */
    public int zzcw(Object obj) {
        int zzahu = zzarn.zzahu(this.tag);
        switch (this.type) {
            case 10:
                return zzard.zzb(zzahu, (zzark) obj);
            case 11:
                return zzard.zzc(zzahu, (zzark) obj);
            default:
                int i = this.type;
                StringBuilder sb = new StringBuilder(24);
                sb.append("Unknown type ");
                sb.append(i);
                throw new IllegalArgumentException(sb.toString());
        }
    }
}
