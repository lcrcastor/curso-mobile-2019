package com.google.common.io;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

@GwtIncompatible
@Beta
public final class CountingInputStream extends FilterInputStream {
    private long a;
    private long b = -1;

    public CountingInputStream(InputStream inputStream) {
        super((InputStream) Preconditions.checkNotNull(inputStream));
    }

    public long getCount() {
        return this.a;
    }

    public int read() {
        int read = this.in.read();
        if (read != -1) {
            this.a++;
        }
        return read;
    }

    public int read(byte[] bArr, int i, int i2) {
        int read = this.in.read(bArr, i, i2);
        if (read != -1) {
            this.a += (long) read;
        }
        return read;
    }

    public long skip(long j) {
        long skip = this.in.skip(j);
        this.a += skip;
        return skip;
    }

    public synchronized void mark(int i) {
        this.in.mark(i);
        this.b = this.a;
    }

    public synchronized void reset() {
        if (!this.in.markSupported()) {
            throw new IOException("Mark not supported");
        } else if (this.b == -1) {
            throw new IOException("Mark not set");
        } else {
            this.in.reset();
            this.a = this.b;
        }
    }
}
