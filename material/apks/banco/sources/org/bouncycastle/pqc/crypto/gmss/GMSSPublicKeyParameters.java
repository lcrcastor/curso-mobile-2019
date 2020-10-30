package org.bouncycastle.pqc.crypto.gmss;

public class GMSSPublicKeyParameters extends GMSSKeyParameters {
    private byte[] b;

    public GMSSPublicKeyParameters(byte[] bArr, GMSSParameters gMSSParameters) {
        super(false, gMSSParameters);
        this.b = bArr;
    }

    public byte[] getPublicKey() {
        return this.b;
    }
}
