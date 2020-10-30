package com.google.android.gms.tagmanager;

import android.annotation.TargetApi;
import android.util.LruCache;
import com.google.android.gms.tagmanager.zzm.zza;

@TargetApi(12)
class zzbi<K, V> implements zzl<K, V> {
    private LruCache<K, V> a;

    zzbi(int i, final zza<K, V> zza) {
        this.a = new LruCache<K, V>(i) {
            /* access modifiers changed from: protected */
            public int sizeOf(K k, V v) {
                return zza.sizeOf(k, v);
            }
        };
    }

    public V a(K k) {
        return this.a.get(k);
    }

    public void a(K k, V v) {
        this.a.put(k, v);
    }
}
