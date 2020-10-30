package org.bouncycastle.crypto.tls;

import org.bouncycastle.crypto.Digest;

class CombinedHash implements TlsHandshakeHash {
    protected TlsContext a;
    protected Digest b;
    protected Digest c;

    CombinedHash() {
        this.b = TlsUtils.createHash(1);
        this.c = TlsUtils.createHash(2);
    }

    CombinedHash(CombinedHash combinedHash) {
        this.a = combinedHash.a;
        this.b = TlsUtils.cloneHash(1, combinedHash.b);
        this.c = TlsUtils.cloneHash(2, combinedHash.c);
    }

    /* access modifiers changed from: protected */
    public void a(Digest digest, byte[] bArr, byte[] bArr2, int i) {
        byte[] bArr3 = this.a.getSecurityParameters().f;
        digest.update(bArr3, 0, bArr3.length);
        digest.update(bArr, 0, i);
        byte[] bArr4 = new byte[digest.getDigestSize()];
        digest.doFinal(bArr4, 0);
        digest.update(bArr3, 0, bArr3.length);
        digest.update(bArr2, 0, i);
        digest.update(bArr4, 0, bArr4.length);
    }

    public int doFinal(byte[] bArr, int i) {
        if (this.a != null && TlsUtils.isSSL(this.a)) {
            a(this.b, SSL3Mac.a, SSL3Mac.b, 48);
            a(this.c, SSL3Mac.a, SSL3Mac.b, 40);
        }
        int doFinal = this.b.doFinal(bArr, i);
        return doFinal + this.c.doFinal(bArr, i + doFinal);
    }

    public Digest forkPRFHash() {
        return new CombinedHash(this);
    }

    public String getAlgorithmName() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.b.getAlgorithmName());
        sb.append(" and ");
        sb.append(this.c.getAlgorithmName());
        return sb.toString();
    }

    public int getDigestSize() {
        return this.b.getDigestSize() + this.c.getDigestSize();
    }

    public byte[] getFinalHash(short s) {
        throw new IllegalStateException("CombinedHash doesn't support multiple hashes");
    }

    public void init(TlsContext tlsContext) {
        this.a = tlsContext;
    }

    public TlsHandshakeHash notifyPRFDetermined() {
        return this;
    }

    public void reset() {
        this.b.reset();
        this.c.reset();
    }

    public void sealHashAlgorithms() {
    }

    public TlsHandshakeHash stopTracking() {
        return new CombinedHash(this);
    }

    public void trackHashAlgorithm(short s) {
        throw new IllegalStateException("CombinedHash only supports calculating the legacy PRF for handshake hash");
    }

    public void update(byte b2) {
        this.b.update(b2);
        this.c.update(b2);
    }

    public void update(byte[] bArr, int i, int i2) {
        this.b.update(bArr, i, i2);
        this.c.update(bArr, i, i2);
    }
}
