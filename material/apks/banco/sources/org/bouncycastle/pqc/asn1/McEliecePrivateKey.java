package org.bouncycastle.pqc.asn1;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.pqc.math.linearalgebra.GF2Matrix;
import org.bouncycastle.pqc.math.linearalgebra.GF2mField;
import org.bouncycastle.pqc.math.linearalgebra.Permutation;
import org.bouncycastle.pqc.math.linearalgebra.PolynomialGF2mSmallM;

public class McEliecePrivateKey extends ASN1Object {
    private ASN1ObjectIdentifier a;
    private int b;
    private int c;
    private byte[] d;
    private byte[] e;
    private byte[] f;
    private byte[] g;
    private byte[] h;
    private byte[] i;
    private byte[][] j;

    public McEliecePrivateKey(ASN1ObjectIdentifier aSN1ObjectIdentifier, int i2, int i3, GF2mField gF2mField, PolynomialGF2mSmallM polynomialGF2mSmallM, GF2Matrix gF2Matrix, Permutation permutation, Permutation permutation2, GF2Matrix gF2Matrix2, PolynomialGF2mSmallM[] polynomialGF2mSmallMArr) {
        this.a = aSN1ObjectIdentifier;
        this.b = i2;
        this.c = i3;
        this.d = gF2mField.getEncoded();
        this.e = polynomialGF2mSmallM.getEncoded();
        this.f = gF2Matrix.getEncoded();
        this.g = permutation.getEncoded();
        this.h = permutation2.getEncoded();
        this.i = gF2Matrix2.getEncoded();
        this.j = new byte[polynomialGF2mSmallMArr.length][];
        for (int i4 = 0; i4 != polynomialGF2mSmallMArr.length; i4++) {
            this.j[i4] = polynomialGF2mSmallMArr[i4].getEncoded();
        }
    }

    private McEliecePrivateKey(ASN1Sequence aSN1Sequence) {
        this.a = (ASN1ObjectIdentifier) aSN1Sequence.getObjectAt(0);
        this.b = ((ASN1Integer) aSN1Sequence.getObjectAt(1)).getValue().intValue();
        this.c = ((ASN1Integer) aSN1Sequence.getObjectAt(2)).getValue().intValue();
        this.d = ((ASN1OctetString) aSN1Sequence.getObjectAt(3)).getOctets();
        this.e = ((ASN1OctetString) aSN1Sequence.getObjectAt(4)).getOctets();
        this.f = ((ASN1OctetString) aSN1Sequence.getObjectAt(5)).getOctets();
        this.g = ((ASN1OctetString) aSN1Sequence.getObjectAt(6)).getOctets();
        this.h = ((ASN1OctetString) aSN1Sequence.getObjectAt(7)).getOctets();
        this.i = ((ASN1OctetString) aSN1Sequence.getObjectAt(8)).getOctets();
        ASN1Sequence aSN1Sequence2 = (ASN1Sequence) aSN1Sequence.getObjectAt(9);
        this.j = new byte[aSN1Sequence2.size()][];
        for (int i2 = 0; i2 < aSN1Sequence2.size(); i2++) {
            this.j[i2] = ((ASN1OctetString) aSN1Sequence2.getObjectAt(i2)).getOctets();
        }
    }

    public static McEliecePrivateKey getInstance(Object obj) {
        if (obj instanceof McEliecePrivateKey) {
            return (McEliecePrivateKey) obj;
        }
        if (obj != null) {
            return new McEliecePrivateKey(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public GF2mField getField() {
        return new GF2mField(this.d);
    }

    public PolynomialGF2mSmallM getGoppaPoly() {
        return new PolynomialGF2mSmallM(getField(), this.e);
    }

    public GF2Matrix getH() {
        return new GF2Matrix(this.i);
    }

    public int getK() {
        return this.c;
    }

    public int getN() {
        return this.b;
    }

    public ASN1ObjectIdentifier getOID() {
        return this.a;
    }

    public Permutation getP1() {
        return new Permutation(this.g);
    }

    public Permutation getP2() {
        return new Permutation(this.h);
    }

    public PolynomialGF2mSmallM[] getQInv() {
        PolynomialGF2mSmallM[] polynomialGF2mSmallMArr = new PolynomialGF2mSmallM[this.j.length];
        GF2mField field = getField();
        for (int i2 = 0; i2 < this.j.length; i2++) {
            polynomialGF2mSmallMArr[i2] = new PolynomialGF2mSmallM(field, this.j[i2]);
        }
        return polynomialGF2mSmallMArr;
    }

    public GF2Matrix getSInv() {
        return new GF2Matrix(this.f);
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.a);
        aSN1EncodableVector.add(new ASN1Integer((long) this.b));
        aSN1EncodableVector.add(new ASN1Integer((long) this.c));
        aSN1EncodableVector.add(new DEROctetString(this.d));
        aSN1EncodableVector.add(new DEROctetString(this.e));
        aSN1EncodableVector.add(new DEROctetString(this.f));
        aSN1EncodableVector.add(new DEROctetString(this.g));
        aSN1EncodableVector.add(new DEROctetString(this.h));
        aSN1EncodableVector.add(new DEROctetString(this.i));
        ASN1EncodableVector aSN1EncodableVector2 = new ASN1EncodableVector();
        for (byte[] dEROctetString : this.j) {
            aSN1EncodableVector2.add(new DEROctetString(dEROctetString));
        }
        aSN1EncodableVector.add(new DERSequence(aSN1EncodableVector2));
        return new DERSequence(aSN1EncodableVector);
    }
}
