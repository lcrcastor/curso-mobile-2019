package org.bouncycastle.crypto.agreement.kdf;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.crypto.DerivationParameters;

public class DHKDFParameters implements DerivationParameters {
    private ASN1ObjectIdentifier a;
    private int b;
    private byte[] c;
    private byte[] d;

    public DHKDFParameters(ASN1ObjectIdentifier aSN1ObjectIdentifier, int i, byte[] bArr) {
        this(aSN1ObjectIdentifier, i, bArr, null);
    }

    public DHKDFParameters(ASN1ObjectIdentifier aSN1ObjectIdentifier, int i, byte[] bArr, byte[] bArr2) {
        this.a = aSN1ObjectIdentifier;
        this.b = i;
        this.c = bArr;
        this.d = bArr2;
    }

    public ASN1ObjectIdentifier getAlgorithm() {
        return this.a;
    }

    public byte[] getExtraInfo() {
        return this.d;
    }

    public int getKeySize() {
        return this.b;
    }

    public byte[] getZ() {
        return this.c;
    }
}
