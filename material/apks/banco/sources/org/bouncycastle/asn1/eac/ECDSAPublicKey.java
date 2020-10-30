package org.bouncycastle.asn1.eac;

import java.math.BigInteger;
import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;

public class ECDSAPublicKey extends PublicKeyDataObject {
    private ASN1ObjectIdentifier a;
    private BigInteger b;
    private BigInteger c;
    private BigInteger d;
    private byte[] e;
    private BigInteger f;
    private byte[] g;
    private BigInteger h;
    private int i;

    public ECDSAPublicKey(ASN1ObjectIdentifier aSN1ObjectIdentifier, BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, byte[] bArr, BigInteger bigInteger4, byte[] bArr2, int i2) {
        this.a = aSN1ObjectIdentifier;
        d(bigInteger);
        b(bigInteger2);
        e(bigInteger3);
        a((ASN1OctetString) new DEROctetString(bArr));
        c(bigInteger4);
        b((ASN1OctetString) new DEROctetString(bArr2));
        a(BigInteger.valueOf((long) i2));
    }

    public ECDSAPublicKey(ASN1ObjectIdentifier aSN1ObjectIdentifier, byte[] bArr) {
        this.a = aSN1ObjectIdentifier;
        b((ASN1OctetString) new DEROctetString(bArr));
    }

    ECDSAPublicKey(ASN1Sequence aSN1Sequence) {
        Enumeration objects = aSN1Sequence.getObjects();
        this.a = ASN1ObjectIdentifier.getInstance(objects.nextElement());
        this.i = 0;
        while (objects.hasMoreElements()) {
            Object nextElement = objects.nextElement();
            if (nextElement instanceof ASN1TaggedObject) {
                ASN1TaggedObject aSN1TaggedObject = (ASN1TaggedObject) nextElement;
                switch (aSN1TaggedObject.getTagNo()) {
                    case 1:
                        d(UnsignedInteger.getInstance(aSN1TaggedObject).getValue());
                        break;
                    case 2:
                        b(UnsignedInteger.getInstance(aSN1TaggedObject).getValue());
                        break;
                    case 3:
                        e(UnsignedInteger.getInstance(aSN1TaggedObject).getValue());
                        break;
                    case 4:
                        a(ASN1OctetString.getInstance(aSN1TaggedObject, false));
                        break;
                    case 5:
                        c(UnsignedInteger.getInstance(aSN1TaggedObject).getValue());
                        break;
                    case 6:
                        b(ASN1OctetString.getInstance(aSN1TaggedObject, false));
                        break;
                    case 7:
                        a(UnsignedInteger.getInstance(aSN1TaggedObject).getValue());
                        break;
                    default:
                        this.i = 0;
                        throw new IllegalArgumentException("Unknown Object Identifier!");
                }
            } else {
                throw new IllegalArgumentException("Unknown Object Identifier!");
            }
        }
        if (this.i != 32 && this.i != 127) {
            throw new IllegalArgumentException("All options must be either present or absent!");
        }
    }

    private void a(BigInteger bigInteger) {
        if ((this.i & 64) == 0) {
            this.i |= 64;
            this.h = bigInteger;
            return;
        }
        throw new IllegalArgumentException("Cofactor F already set");
    }

    private void a(ASN1OctetString aSN1OctetString) {
        if ((this.i & 8) == 0) {
            this.i |= 8;
            this.e = aSN1OctetString.getOctets();
            return;
        }
        throw new IllegalArgumentException("Base Point G already set");
    }

    private void b(BigInteger bigInteger) {
        if ((this.i & 2) == 0) {
            this.i |= 2;
            this.c = bigInteger;
            return;
        }
        throw new IllegalArgumentException("First Coef A already set");
    }

    private void b(ASN1OctetString aSN1OctetString) {
        if ((this.i & 32) == 0) {
            this.i |= 32;
            this.g = aSN1OctetString.getOctets();
            return;
        }
        throw new IllegalArgumentException("Public Point Y already set");
    }

    private void c(BigInteger bigInteger) {
        if ((this.i & 16) == 0) {
            this.i |= 16;
            this.f = bigInteger;
            return;
        }
        throw new IllegalArgumentException("Order of base point R already set");
    }

    private void d(BigInteger bigInteger) {
        if ((this.i & 1) == 0) {
            this.i |= 1;
            this.b = bigInteger;
            return;
        }
        throw new IllegalArgumentException("Prime Modulus P already set");
    }

    private void e(BigInteger bigInteger) {
        if ((this.i & 4) == 0) {
            this.i |= 4;
            this.d = bigInteger;
            return;
        }
        throw new IllegalArgumentException("Second Coef B already set");
    }

    public ASN1EncodableVector getASN1EncodableVector(ASN1ObjectIdentifier aSN1ObjectIdentifier, boolean z) {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(aSN1ObjectIdentifier);
        if (!z) {
            aSN1EncodableVector.add(new UnsignedInteger(1, getPrimeModulusP()));
            aSN1EncodableVector.add(new UnsignedInteger(2, getFirstCoefA()));
            aSN1EncodableVector.add(new UnsignedInteger(3, getSecondCoefB()));
            aSN1EncodableVector.add(new DERTaggedObject(false, 4, new DEROctetString(getBasePointG())));
            aSN1EncodableVector.add(new UnsignedInteger(5, getOrderOfBasePointR()));
        }
        aSN1EncodableVector.add(new DERTaggedObject(false, 6, new DEROctetString(getPublicPointY())));
        if (!z) {
            aSN1EncodableVector.add(new UnsignedInteger(7, getCofactorF()));
        }
        return aSN1EncodableVector;
    }

    public byte[] getBasePointG() {
        if ((this.i & 8) != 0) {
            return this.e;
        }
        return null;
    }

    public BigInteger getCofactorF() {
        if ((this.i & 64) != 0) {
            return this.h;
        }
        return null;
    }

    public BigInteger getFirstCoefA() {
        if ((this.i & 2) != 0) {
            return this.c;
        }
        return null;
    }

    public BigInteger getOrderOfBasePointR() {
        if ((this.i & 16) != 0) {
            return this.f;
        }
        return null;
    }

    public BigInteger getPrimeModulusP() {
        if ((this.i & 1) != 0) {
            return this.b;
        }
        return null;
    }

    public byte[] getPublicPointY() {
        if ((this.i & 32) != 0) {
            return this.g;
        }
        return null;
    }

    public BigInteger getSecondCoefB() {
        if ((this.i & 4) != 0) {
            return this.d;
        }
        return null;
    }

    public ASN1ObjectIdentifier getUsage() {
        return this.a;
    }

    public boolean hasParameters() {
        return this.b != null;
    }

    public ASN1Primitive toASN1Primitive() {
        return new DERSequence(getASN1EncodableVector(this.a, false));
    }
}
