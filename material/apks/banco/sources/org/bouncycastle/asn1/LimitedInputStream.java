package org.bouncycastle.asn1;

import java.io.InputStream;

abstract class LimitedInputStream extends InputStream {
    protected final InputStream a;
    private int b;

    LimitedInputStream(InputStream inputStream, int i) {
        this.a = inputStream;
        this.b = i;
    }

    /* access modifiers changed from: 0000 */
    public int a() {
        return this.b;
    }

    /* access modifiers changed from: protected */
    public void b(boolean z) {
        if (this.a instanceof IndefiniteLengthInputStream) {
            ((IndefiniteLengthInputStream) this.a).a(z);
        }
    }
}
