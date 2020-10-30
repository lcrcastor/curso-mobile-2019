package org.bouncycastle.crypto.tls;

import java.util.Enumeration;
import java.util.Hashtable;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.util.Shorts;

class DeferredHash implements TlsHandshakeHash {
    protected TlsContext a;
    private DigestInputBuffer b;
    private Hashtable c;
    private Short d;

    DeferredHash() {
        this.b = new DigestInputBuffer();
        this.c = new Hashtable();
        this.d = null;
    }

    private DeferredHash(Short sh, Digest digest) {
        this.b = null;
        this.c = new Hashtable();
        this.d = sh;
        this.c.put(sh, digest);
    }

    /* access modifiers changed from: protected */
    public void a() {
        if (this.b != null && this.c.size() <= 4) {
            Enumeration elements = this.c.elements();
            while (elements.hasMoreElements()) {
                this.b.a((Digest) elements.nextElement());
            }
            this.b = null;
        }
    }

    /* access modifiers changed from: protected */
    public void a(Short sh) {
        if (!this.c.containsKey(sh)) {
            this.c.put(sh, TlsUtils.createHash(sh.shortValue()));
        }
    }

    public int doFinal(byte[] bArr, int i) {
        throw new IllegalStateException("Use fork() to get a definite Digest");
    }

    public Digest forkPRFHash() {
        a();
        if (this.b == null) {
            return TlsUtils.cloneHash(this.d.shortValue(), (Digest) this.c.get(this.d));
        }
        Digest createHash = TlsUtils.createHash(this.d.shortValue());
        this.b.a(createHash);
        return createHash;
    }

    public String getAlgorithmName() {
        throw new IllegalStateException("Use fork() to get a definite Digest");
    }

    public int getDigestSize() {
        throw new IllegalStateException("Use fork() to get a definite Digest");
    }

    public byte[] getFinalHash(short s) {
        Digest digest = (Digest) this.c.get(Shorts.valueOf(s));
        if (digest == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("HashAlgorithm ");
            sb.append(s);
            sb.append(" is not being tracked");
            throw new IllegalStateException(sb.toString());
        }
        Digest cloneHash = TlsUtils.cloneHash(s, digest);
        if (this.b != null) {
            this.b.a(cloneHash);
        }
        byte[] bArr = new byte[cloneHash.getDigestSize()];
        cloneHash.doFinal(bArr, 0);
        return bArr;
    }

    public void init(TlsContext tlsContext) {
        this.a = tlsContext;
    }

    public TlsHandshakeHash notifyPRFDetermined() {
        int prfAlgorithm = this.a.getSecurityParameters().getPrfAlgorithm();
        if (prfAlgorithm == 0) {
            CombinedHash combinedHash = new CombinedHash();
            combinedHash.init(this.a);
            this.b.a(combinedHash);
            return combinedHash.notifyPRFDetermined();
        }
        this.d = Shorts.valueOf(TlsUtils.getHashAlgorithmForPRFAlgorithm(prfAlgorithm));
        a(this.d);
        return this;
    }

    public void reset() {
        if (this.b != null) {
            this.b.reset();
            return;
        }
        Enumeration elements = this.c.elements();
        while (elements.hasMoreElements()) {
            ((Digest) elements.nextElement()).reset();
        }
    }

    public void sealHashAlgorithms() {
        a();
    }

    public TlsHandshakeHash stopTracking() {
        Digest cloneHash = TlsUtils.cloneHash(this.d.shortValue(), (Digest) this.c.get(this.d));
        if (this.b != null) {
            this.b.a(cloneHash);
        }
        DeferredHash deferredHash = new DeferredHash(this.d, cloneHash);
        deferredHash.init(this.a);
        return deferredHash;
    }

    public void trackHashAlgorithm(short s) {
        if (this.b == null) {
            throw new IllegalStateException("Too late to track more hash algorithms");
        }
        a(Shorts.valueOf(s));
    }

    public void update(byte b2) {
        if (this.b != null) {
            this.b.write(b2);
            return;
        }
        Enumeration elements = this.c.elements();
        while (elements.hasMoreElements()) {
            ((Digest) elements.nextElement()).update(b2);
        }
    }

    public void update(byte[] bArr, int i, int i2) {
        if (this.b != null) {
            this.b.write(bArr, i, i2);
            return;
        }
        Enumeration elements = this.c.elements();
        while (elements.hasMoreElements()) {
            ((Digest) elements.nextElement()).update(bArr, i, i2);
        }
    }
}
