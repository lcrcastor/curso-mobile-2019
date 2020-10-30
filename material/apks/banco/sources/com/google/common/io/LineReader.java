package com.google.common.io;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Reader;
import java.nio.CharBuffer;
import java.util.LinkedList;
import java.util.Queue;

@GwtIncompatible
@Beta
public final class LineReader {
    private final Readable a;
    private final Reader b;
    private final CharBuffer c = CharStreams.a();
    private final char[] d = this.c.array();
    /* access modifiers changed from: private */
    public final Queue<String> e = new LinkedList();
    private final LineBuffer f = new LineBuffer() {
        /* access modifiers changed from: protected */
        public void a(String str, String str2) {
            LineReader.this.e.add(str);
        }
    };

    public LineReader(Readable readable) {
        this.a = (Readable) Preconditions.checkNotNull(readable);
        this.b = readable instanceof Reader ? (Reader) readable : null;
    }

    @CanIgnoreReturnValue
    public String readLine() {
        while (true) {
            if (this.e.peek() != null) {
                break;
            }
            this.c.clear();
            int read = this.b != null ? this.b.read(this.d, 0, this.d.length) : this.a.read(this.c);
            if (read == -1) {
                this.f.a();
                break;
            }
            this.f.a(this.d, 0, read);
        }
        return (String) this.e.poll();
    }
}
