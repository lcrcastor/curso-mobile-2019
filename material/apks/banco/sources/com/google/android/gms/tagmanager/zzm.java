package com.google.android.gms.tagmanager;

import android.os.Build.VERSION;

class zzm<K, V> {
    final zza<K, V> a = new zza<K, V>() {
        public int sizeOf(K k, V v) {
            return 1;
        }
    };

    public interface zza<K, V> {
        int sizeOf(K k, V v);
    }

    /* access modifiers changed from: 0000 */
    public int a() {
        return VERSION.SDK_INT;
    }

    public zzl<K, V> a(int i, zza<K, V> zza2) {
        if (i > 0) {
            return a() < 12 ? new zzde(i, zza2) : new zzbi(i, zza2);
        }
        throw new IllegalArgumentException("maxSize <= 0");
    }
}
