package org.bouncycastle.asn1.pkcs;

import java.math.BigInteger;
import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;

public class RSAPrivateKey extends ASN1Object {
    private BigInteger a;
    private BigInteger b;
    private BigInteger c;
    private BigInteger d;
    private BigInteger e;
    private BigInteger f;
    private BigInteger g;
    private BigInteger h;
    private BigInteger i;
    private ASN1Sequence j;

    public RSAPrivateKey(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger bigInteger4, BigInteger bigInteger5, BigInteger bigInteger6, BigInteger bigInteger7, BigInteger bigInteger8) {
        this.j = null;
        this.a = BigInteger.valueOf(0);
        this.b = bigInteger;
        this.c = bigInteger2;
        this.d = bigInteger3;
        this.e = bigInteger4;
        this.f = bigInteger5;
        this.g = bigInteger6;
        this.h = bigInteger7;
        this.i = bigInteger8;
    }

    private RSAPrivateKey(ASN1Sequence aSN1Sequence) {
        this.j = null;
        Enumeration objects = aSN1Sequence.getObjects();
        BigInteger value = ((ASN1Integer) objects.nextElement()).getValue();
        if (value.intValue() == 0 || value.intValue() == 1) {
            this.a = value;
            this.b = ((ASN1Integer) objects.nextElement()).getValue();
            this.c = ((ASN1Integer) objects.nextElement()).getValue();
            this.d = ((ASN1Integer) objects.nextElement()).getValue();
            this.e = ((ASN1Integer) objects.nextElement()).getValue();
            this.f = ((ASN1Integer) objects.nextElement()).getValue();
            this.g = ((ASN1Integer) objects.nextElement()).getValue();
            this.h = ((ASN1Integer) objects.nextElement()).getValue();
            this.i = ((ASN1Integer) objects.nextElement()).getValue();
            if (objects.hasMoreElements()) {
                this.j = (ASN1Sequence) objects.nextElement();
                return;
            }
            return;
        }
        throw new IllegalArgumentException("wrong version for RSA private key");
    }

    public static RSAPrivateKey getInstance(Object obj) {
        if (obj instanceof RSAPrivateKey) {
            return (RSAPrivateKey) obj;
        }
        if (obj != null) {
            return new RSAPrivateKey(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public static RSAPrivateKey getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public BigInteger getCoefficient() {
        return this.i;
    }

    public BigInteger getExponent1() {
        return this.g;
    }

    public BigInteger getExponent2() {
        return this.h;
    }

    public BigInteger getModulus() {
        return this.b;
    }

    public BigInteger getPrime1() {
        return this.e;
    }

    public BigInteger getPrime2() {
        return this.f;
    }

    public BigInteger getPrivateExponent() {
        return this.d;
    }

    public BigInteger getPublicExponent() {
        return this.c;
    }

    public BigInteger getVersion() {
        return this.a;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(new ASN1Integer(this.a));
        aSN1EncodableVector.add(new ASN1Integer(getModulus()));
        aSN1EncodableVector.add(new ASN1Integer(getPublicExponent()));
        aSN1EncodableVector.add(new ASN1Integer(getPrivateExponent()));
        aSN1EncodableVector.add(new ASN1Integer(getPrime1()));
        aSN1EncodableVector.add(new ASN1Integer(getPrime2()));
        aSN1EncodableVector.add(new ASN1Integer(getExponent1()));
        aSN1EncodableVector.add(new ASN1Integer(getExponent2()));
        aSN1EncodableVector.add(new ASN1Integer(getCoefficient()));
        if (this.j != null) {
            aSN1EncodableVector.add(this.j);
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
