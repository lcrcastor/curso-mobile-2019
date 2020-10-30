package com.google.android.gms.common.data;

import com.google.android.gms.common.internal.zzac;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class zzb<T> implements Iterator<T> {
    protected final DataBuffer<T> zH;
    protected int zI = -1;

    public zzb(DataBuffer<T> dataBuffer) {
        this.zH = (DataBuffer) zzac.zzy(dataBuffer);
    }

    public boolean hasNext() {
        return this.zI < this.zH.getCount() - 1;
    }

    public T next() {
        if (!hasNext()) {
            int i = this.zI;
            StringBuilder sb = new StringBuilder(46);
            sb.append("Cannot advance the iterator beyond ");
            sb.append(i);
            throw new NoSuchElementException(sb.toString());
        }
        DataBuffer<T> dataBuffer = this.zH;
        int i2 = this.zI + 1;
        this.zI = i2;
        return dataBuffer.get(i2);
    }

    public void remove() {
        throw new UnsupportedOperationException("Cannot remove elements from a DataBufferIterator");
    }
}
