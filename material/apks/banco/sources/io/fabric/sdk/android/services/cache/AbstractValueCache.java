package io.fabric.sdk.android.services.cache;

import android.content.Context;

public abstract class AbstractValueCache<T> implements ValueCache<T> {
    private final ValueCache<T> a;

    /* access modifiers changed from: protected */
    public abstract void cacheValue(Context context, T t);

    /* access modifiers changed from: protected */
    public abstract void doInvalidate(Context context);

    /* access modifiers changed from: protected */
    public abstract T getCached(Context context);

    public AbstractValueCache() {
        this(null);
    }

    public AbstractValueCache(ValueCache<T> valueCache) {
        this.a = valueCache;
    }

    public final synchronized T get(Context context, ValueLoader<T> valueLoader) {
        T cached;
        cached = getCached(context);
        if (cached == null) {
            cached = this.a != null ? this.a.get(context, valueLoader) : valueLoader.load(context);
            a(context, cached);
        }
        return cached;
    }

    public final synchronized void invalidate(Context context) {
        doInvalidate(context);
    }

    private void a(Context context, T t) {
        if (t == null) {
            throw new NullPointerException();
        }
        cacheValue(context, t);
    }
}
