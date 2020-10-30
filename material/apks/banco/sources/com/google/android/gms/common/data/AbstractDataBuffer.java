package com.google.android.gms.common.data;

import android.os.Bundle;
import java.util.Iterator;

public abstract class AbstractDataBuffer<T> implements DataBuffer<T> {
    protected final DataHolder xi;

    protected AbstractDataBuffer(DataHolder dataHolder) {
        this.xi = dataHolder;
        DataHolder dataHolder2 = this.xi;
    }

    @Deprecated
    public final void close() {
        release();
    }

    public abstract T get(int i);

    public int getCount() {
        if (this.xi == null) {
            return 0;
        }
        return this.xi.getCount();
    }

    @Deprecated
    public boolean isClosed() {
        return this.xi == null || this.xi.isClosed();
    }

    public Iterator<T> iterator() {
        return new zzb(this);
    }

    public void release() {
        if (this.xi != null) {
            this.xi.close();
        }
    }

    public Iterator<T> singleRefIterator() {
        return new zzg(this);
    }

    public Bundle zzasz() {
        return this.xi.zzasz();
    }
}
