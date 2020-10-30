package com.google.common.io;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.Writer;
import javax.annotation.Nullable;

@GwtIncompatible
class AppendableWriter extends Writer {
    private final Appendable a;
    private boolean b;

    AppendableWriter(Appendable appendable) {
        this.a = (Appendable) Preconditions.checkNotNull(appendable);
    }

    public void write(char[] cArr, int i, int i2) {
        a();
        this.a.append(new String(cArr, i, i2));
    }

    public void flush() {
        a();
        if (this.a instanceof Flushable) {
            ((Flushable) this.a).flush();
        }
    }

    public void close() {
        this.b = true;
        if (this.a instanceof Closeable) {
            ((Closeable) this.a).close();
        }
    }

    public void write(int i) {
        a();
        this.a.append((char) i);
    }

    public void write(@Nullable String str) {
        a();
        this.a.append(str);
    }

    public void write(@Nullable String str, int i, int i2) {
        a();
        this.a.append(str, i, i2 + i);
    }

    public Writer append(char c) {
        a();
        this.a.append(c);
        return this;
    }

    public Writer append(@Nullable CharSequence charSequence) {
        a();
        this.a.append(charSequence);
        return this;
    }

    public Writer append(@Nullable CharSequence charSequence, int i, int i2) {
        a();
        this.a.append(charSequence, i, i2);
        return this;
    }

    private void a() {
        if (this.b) {
            throw new IOException("Cannot write to a closed writer.");
        }
    }
}
