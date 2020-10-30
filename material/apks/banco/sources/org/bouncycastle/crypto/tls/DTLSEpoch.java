package org.bouncycastle.crypto.tls;

class DTLSEpoch {
    private final DTLSReplayWindow a = new DTLSReplayWindow();
    private final int b;
    private final TlsCipher c;
    private long d = 0;

    DTLSEpoch(int i, TlsCipher tlsCipher) {
        if (i < 0) {
            throw new IllegalArgumentException("'epoch' must be >= 0");
        } else if (tlsCipher == null) {
            throw new IllegalArgumentException("'cipher' cannot be null");
        } else {
            this.b = i;
            this.c = tlsCipher;
        }
    }

    /* access modifiers changed from: 0000 */
    public long a() {
        long j = this.d;
        this.d = j + 1;
        return j;
    }

    /* access modifiers changed from: 0000 */
    public TlsCipher b() {
        return this.c;
    }

    /* access modifiers changed from: 0000 */
    public int c() {
        return this.b;
    }

    /* access modifiers changed from: 0000 */
    public DTLSReplayWindow d() {
        return this.a;
    }
}
