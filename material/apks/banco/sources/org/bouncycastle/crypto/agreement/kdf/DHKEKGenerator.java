package org.bouncycastle.crypto.agreement.kdf;

import java.io.IOException;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.DerivationFunction;
import org.bouncycastle.crypto.DerivationParameters;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.util.Pack;

public class DHKEKGenerator implements DerivationFunction {
    private final Digest a;
    private ASN1ObjectIdentifier b;
    private int c;
    private byte[] d;
    private byte[] e;

    public DHKEKGenerator(Digest digest) {
        this.a = digest;
    }

    public int generateBytes(byte[] bArr, int i, int i2) {
        int i3;
        byte[] bArr2 = bArr;
        int i4 = i2;
        int i5 = i;
        if (bArr2.length - i4 < i5) {
            throw new DataLengthException("output buffer too small");
        }
        long j = (long) i4;
        int digestSize = this.a.getDigestSize();
        if (j > 8589934591L) {
            throw new IllegalArgumentException("Output length too large");
        }
        long j2 = (long) digestSize;
        int i6 = (int) (((j + j2) - 1) / j2);
        byte[] bArr3 = new byte[this.a.getDigestSize()];
        int i7 = 0;
        int i8 = i4;
        int i9 = i5;
        int i10 = 0;
        int i11 = 1;
        while (i10 < i6) {
            this.a.update(this.d, i7, this.d.length);
            ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
            ASN1EncodableVector aSN1EncodableVector2 = new ASN1EncodableVector();
            aSN1EncodableVector2.add(this.b);
            aSN1EncodableVector2.add(new DEROctetString(Pack.intToBigEndian(i11)));
            aSN1EncodableVector.add(new DERSequence(aSN1EncodableVector2));
            if (this.e != null) {
                i3 = i6;
                aSN1EncodableVector.add(new DERTaggedObject(true, 0, new DEROctetString(this.e)));
            } else {
                i3 = i6;
            }
            aSN1EncodableVector.add(new DERTaggedObject(true, 2, new DEROctetString(Pack.intToBigEndian(this.c))));
            try {
                byte[] encoded = new DERSequence(aSN1EncodableVector).getEncoded(ASN1Encoding.DER);
                this.a.update(encoded, 0, encoded.length);
                this.a.doFinal(bArr3, 0);
                if (i8 > digestSize) {
                    System.arraycopy(bArr3, 0, bArr2, i9, digestSize);
                    i9 += digestSize;
                    i8 -= digestSize;
                } else {
                    System.arraycopy(bArr3, 0, bArr2, i9, i8);
                }
                i11++;
                i10++;
                i6 = i3;
                i7 = 0;
            } catch (IOException e2) {
                IOException iOException = e2;
                StringBuilder sb = new StringBuilder();
                sb.append("unable to encode parameter info: ");
                sb.append(iOException.getMessage());
                throw new IllegalArgumentException(sb.toString());
            }
        }
        this.a.reset();
        return (int) j;
    }

    public Digest getDigest() {
        return this.a;
    }

    public void init(DerivationParameters derivationParameters) {
        DHKDFParameters dHKDFParameters = (DHKDFParameters) derivationParameters;
        this.b = dHKDFParameters.getAlgorithm();
        this.c = dHKDFParameters.getKeySize();
        this.d = dHKDFParameters.getZ();
        this.e = dHKDFParameters.getExtraInfo();
    }
}
