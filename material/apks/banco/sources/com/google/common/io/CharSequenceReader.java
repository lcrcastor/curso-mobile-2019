package com.google.common.io;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import java.io.IOException;
import java.io.Reader;
import java.nio.CharBuffer;

@GwtIncompatible
final class CharSequenceReader extends Reader {
    private CharSequence a;
    private int b;
    private int c;

    public boolean markSupported() {
        return true;
    }

    public CharSequenceReader(CharSequence charSequence) {
        this.a = (CharSequence) Preconditions.checkNotNull(charSequence);
    }

    private void a() {
        if (this.a == null) {
            throw new IOException("reader closed");
        }
    }

    private boolean b() {
        return c() > 0;
    }

    private int c() {
        return this.a.length() - this.b;
    }

    public synchronized int read(CharBuffer charBuffer) {
        Preconditions.checkNotNull(charBuffer);
        a();
        if (!b()) {
            return -1;
        }
        int min = Math.min(charBuffer.remaining(), c());
        for (int i = 0; i < min; i++) {
            CharSequence charSequence = this.a;
            int i2 = this.b;
            this.b = i2 + 1;
            charBuffer.put(charSequence.charAt(i2));
        }
        return min;
    }

    public synchronized int read() {
        char c2;
        a();
        if (b()) {
            CharSequence charSequence = this.a;
            int i = this.b;
            this.b = i + 1;
            c2 = charSequence.charAt(i);
        } else {
            c2 = 65535;
        }
        return c2;
    }

    public synchronized int read(char[] cArr, int i, int i2) {
        Preconditions.checkPositionIndexes(i, i + i2, cArr.length);
        a();
        if (!b()) {
            return -1;
        }
        int min = Math.min(i2, c());
        for (int i3 = 0; i3 < min; i3++) {
            int i4 = i + i3;
            CharSequence charSequence = this.a;
            int i5 = this.b;
            this.b = i5 + 1;
            cArr[i4] = charSequence.charAt(i5);
        }
        return min;
    }

    public synchronized long skip(long j) {
        int min;
        Preconditions.checkArgument(j >= 0, "n (%s) may not be negative", j);
        a();
        min = (int) Math.min((long) c(), j);
        this.b += min;
        return (long) min;
    }

    public synchronized boolean ready() {
        a();
        return true;
    }

    public synchronized void mark(int i) {
        Preconditions.checkArgument(i >= 0, "readAheadLimit (%s) may not be negative", i);
        a();
        this.c = this.b;
    }

    public synchronized void reset() {
        a();
        this.b = this.c;
    }

    public synchronized void close() {
        this.a = null;
    }
}
