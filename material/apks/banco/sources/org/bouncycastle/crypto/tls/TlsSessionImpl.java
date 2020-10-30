package org.bouncycastle.crypto.tls;

import org.bouncycastle.util.Arrays;

class TlsSessionImpl implements TlsSession {
    final byte[] a;
    SessionParameters b;

    TlsSessionImpl(byte[] bArr, SessionParameters sessionParameters) {
        if (bArr == null) {
            throw new IllegalArgumentException("'sessionID' cannot be null");
        } else if (bArr.length < 1 || bArr.length > 32) {
            throw new IllegalArgumentException("'sessionID' must have length between 1 and 32 bytes, inclusive");
        } else {
            this.a = Arrays.clone(bArr);
            this.b = sessionParameters;
        }
    }

    public synchronized SessionParameters exportSessionParameters() {
        return this.b == null ? null : this.b.copy();
    }

    public synchronized byte[] getSessionID() {
        return this.a;
    }

    public synchronized void invalidate() {
        if (this.b != null) {
            this.b.clear();
            this.b = null;
        }
    }

    public synchronized boolean isResumable() {
        return this.b != null;
    }
}
