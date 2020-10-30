package org.bouncycastle.asn1.eac;

import java.math.BigInteger;
import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;

public class RSAPublicKey extends PublicKeyDataObject {
    private static int e = 1;
    private static int f = 2;
    private ASN1ObjectIdentifier a;
    private BigInteger b;
    private BigInteger c;
    private int d = 0;

    public RSAPublicKey(ASN1ObjectIdentifier aSN1ObjectIdentifier, BigInteger bigInteger, BigInteger bigInteger2) {
        this.a = aSN1ObjectIdentifier;
        this.b = bigInteger;
        this.c = bigInteger2;
    }

    RSAPublicKey(ASN1Sequence aSN1Sequence) {
        Enumeration objects = aSN1Sequence.getObjects();
        this.a = ASN1ObjectIdentifier.getInstance(objects.nextElement());
        while (objects.hasMoreElements()) {
            UnsignedInteger instance = UnsignedInteger.getInstance(objects.nextElement());
            switch (instance.getTagNo()) {
                case 1:
                    a(instance);
                    break;
                case 2:
                    b(instance);
                    break;
                default:
                    StringBuilder sb = new StringBuilder();
                    sb.append("Unknown DERTaggedObject :");
                    sb.append(instance.getTagNo());
                    sb.append("-> not an Iso7816RSAPublicKeyStructure");
                    throw new IllegalArgumentException(sb.toString());
            }
        }
        if (this.d != 3) {
            throw new IllegalArgumentException("missing argument -> not an Iso7816RSAPublicKeyStructure");
        }
    }

    private void a(UnsignedInteger unsignedInteger) {
        if ((this.d & e) == 0) {
            this.d |= e;
            this.b = unsignedInteger.getValue();
            return;
        }
        throw new IllegalArgumentException("Modulus already set");
    }

    private void b(UnsignedInteger unsignedInteger) {
        if ((this.d & f) == 0) {
            this.d |= f;
            this.c = unsignedInteger.getValue();
            return;
        }
        throw new IllegalArgumentException("Exponent already set");
    }

    public BigInteger getModulus() {
        return this.b;
    }

    public BigInteger getPublicExponent() {
        return this.c;
    }

    public ASN1ObjectIdentifier getUsage() {
        return this.a;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.a);
        aSN1EncodableVector.add(new UnsignedInteger(1, getModulus()));
        aSN1EncodableVector.add(new UnsignedInteger(2, getPublicExponent()));
        return new DERSequence(aSN1EncodableVector);
    }
}
