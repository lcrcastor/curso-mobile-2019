package org.bouncycastle.crypto.tls;

import org.bouncycastle.util.Arrays;

public class SecurityParameters {
    int a = -1;
    int b = -1;
    short c = -1;
    int d = -1;
    int e = -1;
    byte[] f = null;
    byte[] g = null;
    byte[] h = null;
    short i = -1;
    boolean j = false;
    boolean k = false;

    /* access modifiers changed from: 0000 */
    public void a() {
        if (this.f != null) {
            Arrays.fill(this.f, 0);
            this.f = null;
        }
    }

    public int getCipherSuite() {
        return this.b;
    }

    public byte[] getClientRandom() {
        return this.g;
    }

    public short getCompressionAlgorithm() {
        return this.c;
    }

    public int getEntity() {
        return this.a;
    }

    public byte[] getMasterSecret() {
        return this.f;
    }

    public int getPrfAlgorithm() {
        return this.d;
    }

    public byte[] getServerRandom() {
        return this.h;
    }

    public int getVerifyDataLength() {
        return this.e;
    }
}
