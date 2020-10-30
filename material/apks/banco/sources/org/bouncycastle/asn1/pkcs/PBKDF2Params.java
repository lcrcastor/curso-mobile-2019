package org.bouncycastle.asn1.pkcs;

import java.math.BigInteger;
import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class PBKDF2Params extends ASN1Object {
    private static final AlgorithmIdentifier a = new AlgorithmIdentifier(PKCSObjectIdentifiers.id_hmacWithSHA1, DERNull.INSTANCE);
    private ASN1OctetString b;
    private ASN1Integer c;
    private ASN1Integer d;
    private AlgorithmIdentifier e;

    private PBKDF2Params(ASN1Sequence aSN1Sequence) {
        Enumeration objects = aSN1Sequence.getObjects();
        this.b = (ASN1OctetString) objects.nextElement();
        this.c = (ASN1Integer) objects.nextElement();
        if (objects.hasMoreElements()) {
            Object nextElement = objects.nextElement();
            if (nextElement instanceof ASN1Integer) {
                this.d = ASN1Integer.getInstance(nextElement);
                nextElement = objects.hasMoreElements() ? objects.nextElement() : null;
            } else {
                this.d = null;
            }
            if (nextElement != null) {
                this.e = AlgorithmIdentifier.getInstance(nextElement);
            }
        }
    }

    public PBKDF2Params(byte[] bArr, int i) {
        this.b = new DEROctetString(bArr);
        this.c = new ASN1Integer((long) i);
    }

    public PBKDF2Params(byte[] bArr, int i, int i2) {
        this(bArr, i);
        this.d = new ASN1Integer((long) i2);
    }

    public PBKDF2Params(byte[] bArr, int i, int i2, AlgorithmIdentifier algorithmIdentifier) {
        this(bArr, i);
        this.d = new ASN1Integer((long) i2);
        this.e = algorithmIdentifier;
    }

    public PBKDF2Params(byte[] bArr, int i, AlgorithmIdentifier algorithmIdentifier) {
        this(bArr, i);
        this.e = algorithmIdentifier;
    }

    public static PBKDF2Params getInstance(Object obj) {
        if (obj instanceof PBKDF2Params) {
            return (PBKDF2Params) obj;
        }
        if (obj != null) {
            return new PBKDF2Params(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public BigInteger getIterationCount() {
        return this.c.getValue();
    }

    public BigInteger getKeyLength() {
        if (this.d != null) {
            return this.d.getValue();
        }
        return null;
    }

    public AlgorithmIdentifier getPrf() {
        return this.e != null ? this.e : a;
    }

    public byte[] getSalt() {
        return this.b.getOctets();
    }

    public boolean isDefaultPrf() {
        return this.e == null || this.e.equals(a);
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.b);
        aSN1EncodableVector.add(this.c);
        if (this.d != null) {
            aSN1EncodableVector.add(this.d);
        }
        if (this.e != null && !this.e.equals(a)) {
            aSN1EncodableVector.add(this.e);
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
