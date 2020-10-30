package com.google.android.gms.internal;

import android.os.Binder;

public abstract class zzrs<T> {
    private static final Object a = new Object();
    private static zza b = null;
    private static int c = 0;
    private static String d = "com.google.android.providers.gsf.permission.READ_GSERVICES";
    private T e = null;
    protected final String zzbaf;
    protected final T zzbag;

    interface zza {
        Boolean a(String str, Boolean bool);

        Float a(String str, Float f);

        Integer a(String str, Integer num);

        Long a(String str, Long l);

        String a(String str, String str2);
    }

    protected zzrs(String str, T t) {
        this.zzbaf = str;
        this.zzbag = t;
    }

    static /* synthetic */ zza a() {
        return null;
    }

    public static zzrs<Float> zza(String str, Float f) {
        return new zzrs<Float>(str, f) {
            /* access modifiers changed from: protected */
            /* renamed from: a */
            public Float zzhg(String str) {
                return zzrs.a().a(this.zzbaf, (Float) this.zzbag);
            }
        };
    }

    public static zzrs<Integer> zza(String str, Integer num) {
        return new zzrs<Integer>(str, num) {
            /* access modifiers changed from: protected */
            /* renamed from: a */
            public Integer zzhg(String str) {
                return zzrs.a().a(this.zzbaf, (Integer) this.zzbag);
            }
        };
    }

    public static zzrs<Long> zza(String str, Long l) {
        return new zzrs<Long>(str, l) {
            /* access modifiers changed from: protected */
            /* renamed from: a */
            public Long zzhg(String str) {
                return zzrs.a().a(this.zzbaf, (Long) this.zzbag);
            }
        };
    }

    public static zzrs<String> zzab(String str, String str2) {
        return new zzrs<String>(str, str2) {
            /* access modifiers changed from: protected */
            /* renamed from: a */
            public String zzhg(String str) {
                return zzrs.a().a(this.zzbaf, (String) this.zzbag);
            }
        };
    }

    public static zzrs<Boolean> zzm(String str, boolean z) {
        return new zzrs<Boolean>(str, Boolean.valueOf(z)) {
            /* access modifiers changed from: protected */
            /* renamed from: a */
            public Boolean zzhg(String str) {
                return zzrs.a().a(this.zzbaf, (Boolean) this.zzbag);
            }
        };
    }

    public final T get() {
        long clearCallingIdentity;
        try {
            return zzhg(this.zzbaf);
        } catch (SecurityException unused) {
            clearCallingIdentity = Binder.clearCallingIdentity();
            T zzhg = zzhg(this.zzbaf);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return zzhg;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    /* access modifiers changed from: protected */
    public abstract T zzhg(String str);
}
