package cz.msebera.android.httpclient.impl.io;

import cz.msebera.android.httpclient.annotation.NotThreadSafe;
import cz.msebera.android.httpclient.io.BufferInfo;
import cz.msebera.android.httpclient.io.SessionInputBuffer;
import cz.msebera.android.httpclient.util.Args;
import java.io.InputStream;

@NotThreadSafe
public class IdentityInputStream extends InputStream {
    private final SessionInputBuffer a;
    private boolean b = false;

    public IdentityInputStream(SessionInputBuffer sessionInputBuffer) {
        this.a = (SessionInputBuffer) Args.notNull(sessionInputBuffer, "Session input buffer");
    }

    public int available() {
        if (this.a instanceof BufferInfo) {
            return ((BufferInfo) this.a).length();
        }
        return 0;
    }

    public void close() {
        this.b = true;
    }

    public int read() {
        if (this.b) {
            return -1;
        }
        return this.a.read();
    }

    public int read(byte[] bArr, int i, int i2) {
        if (this.b) {
            return -1;
        }
        return this.a.read(bArr, i, i2);
    }
}
