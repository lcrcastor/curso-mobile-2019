package com.google.common.io;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import java.io.Reader;
import java.util.Iterator;
import javax.annotation.Nullable;

@GwtIncompatible
class MultiReader extends Reader {
    private final Iterator<? extends CharSource> a;
    private Reader b;

    MultiReader(Iterator<? extends CharSource> it) {
        this.a = it;
        a();
    }

    private void a() {
        close();
        if (this.a.hasNext()) {
            this.b = ((CharSource) this.a.next()).openStream();
        }
    }

    public int read(@Nullable char[] cArr, int i, int i2) {
        if (this.b == null) {
            return -1;
        }
        int read = this.b.read(cArr, i, i2);
        if (read != -1) {
            return read;
        }
        a();
        return read(cArr, i, i2);
    }

    public long skip(long j) {
        Preconditions.checkArgument(j >= 0, "n is negative");
        if (j > 0) {
            while (this.b != null) {
                long skip = this.b.skip(j);
                if (skip > 0) {
                    return skip;
                }
                a();
            }
        }
        return 0;
    }

    public boolean ready() {
        return this.b != null && this.b.ready();
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
}
