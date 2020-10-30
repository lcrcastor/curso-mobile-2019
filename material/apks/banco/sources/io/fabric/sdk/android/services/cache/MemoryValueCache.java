package io.fabric.sdk.android.services.cache;

import android.content.Context;

public class MemoryValueCache<T> extends AbstractValueCache<T> {
    private T a;

    public MemoryValueCache() {
        this(null);
    }

    public MemoryValueCache(ValueCache<T> valueCache) {
        super(valueCache);
    }

    /* access modifiers changed from: protected */
    public void doInvalidate(Context context) {
        this.a = null;
    }

    /* access modifiers changed from: protected */
    public T getCached(Context context) {
        return this.a;
    }

    /* access modifiers changed from: protected */
    public void cacheValue(Context context, T t) {
        this.a = t;
    }
}
