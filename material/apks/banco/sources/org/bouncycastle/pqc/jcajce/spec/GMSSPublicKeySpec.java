package org.bouncycastle.pqc.jcajce.spec;

import org.bouncycastle.pqc.crypto.gmss.GMSSParameters;

public class GMSSPublicKeySpec extends GMSSKeySpec {
    private byte[] a;

    public GMSSPublicKeySpec(byte[] bArr, GMSSParameters gMSSParameters) {
        super(gMSSParameters);
        this.a = bArr;
    }

    public byte[] getPublicKey() {
        return this.a;
    }
}
