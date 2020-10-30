package com.google.common.io;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import java.io.InputStream;
import java.util.Iterator;
import javax.annotation.Nullable;

@GwtIncompatible
final class MultiInputStream extends InputStream {
    private Iterator<? extends ByteSource> a;
    private InputStream b;

    public boolean markSupported() {
        return false;
    }

    public MultiInputStream(Iterator<? extends ByteSource> it) {
        this.a = (Iterator) Preconditions.checkNotNull(it);
        a();
    }

    public void close() {
        if (this.b != null) {
            try {
                this.b.close();
            } finally {
                this.b = null;
            }
        }
    }

    private void a() {
        close();
        if (this.a.hasNext()) {
            this.b = ((ByteSource) this.a.next()).openStream();
        }
    }

    public int available() {
        if (this.b == null) {
            return 0;
        }
        return this.b.available();
    }

    public int read() {
        if (this.b == null) {
            return -1;
        }
        int read = this.b.read();
        if (read != -1) {
            return read;
        }
        a();
        return read();
    }

    public int read(@Nullable byte[] bArr, int i, int i2) {
        if (this.b == null) {
            return -1;
        }
        int read = this.b.read(bArr, i, i2);
        if (read != -1) {
            return read;
        }
        a();
        return read(bArr, i, i2);
    }

    public long skip(long j) {
        if (this.b == null || j <= 0) {
            return 0;
        }
        long skip = this.b.skip(j);
        if (skip != 0) {
            return skip;
        }
        if (read() == -1) {
            return 0;
        }
        return this.b.skip(j - 1) + 1;
    }
}
