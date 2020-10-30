package com.google.android.gms.common.internal;

import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public abstract class DowngradeableSafeParcel extends AbstractSafeParcelable implements ReflectedParcelable {
    private static final Object a = new Object();
    private static ClassLoader b;
    private static Integer c;
    private boolean d = false;

    private static boolean a(Class<?> cls) {
        try {
            return SafeParcelable.NULL.equals(cls.getField("NULL").get(null));
        } catch (IllegalAccessException | NoSuchFieldException unused) {
            return false;
        }
    }

    protected static ClassLoader zzaup() {
        synchronized (a) {
        }
        return null;
    }

    protected static Integer zzauq() {
        synchronized (a) {
        }
        return null;
    }

    protected static boolean zzhs(String str) {
        ClassLoader zzaup = zzaup();
        if (zzaup == null) {
            return true;
        }
        try {
            return a(zzaup.loadClass(str));
        } catch (Exception unused) {
            return false;
        }
    }

    /* access modifiers changed from: protected */
    public boolean zzaur() {
        return false;
    }
}
