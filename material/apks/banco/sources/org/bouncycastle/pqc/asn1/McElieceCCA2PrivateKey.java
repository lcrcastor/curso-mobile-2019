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

public class McElieceCCA2PrivateKey extends ASN1Object {
    private ASN1ObjectIdentifier a;
    private int b;
    private int c;
    private byte[] d;
    private byte[] e;
    private byte[] f;
    private byte[] g;
    private byte[][] h;

    public McElieceCCA2PrivateKey(ASN1ObjectIdentifier aSN1ObjectIdentifier, int i, int i2, GF2mField gF2mField, PolynomialGF2mSmallM polynomialGF2mSmallM, Permutation permutation, GF2Matrix gF2Matrix, PolynomialGF2mSmallM[] polynomialGF2mSmallMArr) {
        this.a = aSN1ObjectIdentifier;
        this.b = i;
        this.c = i2;
        this.d = gF2mField.getEncoded();
        this.e = polynomialGF2mSmallM.getEncoded();
        this.f = permutation.getEncoded();
        this.g = gF2Matrix.getEncoded();
        this.h = new byte[polynomialGF2mSmallMArr.length][];
        for (int i3 = 0; i3 != polynomialGF2mSmallMArr.length; i3++) {
            this.h[i3] = polynomialGF2mSmallMArr[i3].getEncoded();
        }
    }

    private McElieceCCA2PrivateKey(ASN1Sequence aSN1Sequence) {
        this.a = (ASN1ObjectIdentifier) aSN1Sequence.getObjectAt(0);
        this.b = ((ASN1Integer) aSN1Sequence.getObjectAt(1)).getValue().intValue();
        this.c = ((ASN1Integer) aSN1Sequence.getObjectAt(2)).getValue().intValue();
        this.d = ((ASN1OctetString) aSN1Sequence.getObjectAt(3)).getOctets();
        this.e = ((ASN1OctetString) aSN1Sequence.getObjectAt(4)).getOctets();
        this.f = ((ASN1OctetString) aSN1Sequence.getObjectAt(5)).getOctets();
        this.g = ((ASN1OctetString) aSN1Sequence.getObjectAt(6)).getOctets();
        ASN1Sequence aSN1Sequence2 = (ASN1Sequence) aSN1Sequence.getObjectAt(7);
        this.h = new byte[aSN1Sequence2.size()][];
        for (int i = 0; i < aSN1Sequence2.size(); i++) {
            this.h[i] = ((ASN1OctetString) aSN1Sequence2.getObjectAt(i)).getOctets();
        }
    }

    public static McElieceCCA2PrivateKey getInstance(Object obj) {
        if (obj instanceof McElieceCCA2PrivateKey) {
            return (McElieceCCA2PrivateKey) obj;
        }
        if (obj != null) {
            return new McElieceCCA2PrivateKey(ASN1Sequence.getInstance(obj));
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
        return new GF2Matrix(this.g);
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

    public Permutation getP() {
        return new Permutation(this.f);
    }

    public PolynomialGF2mSmallM[] getQInv() {
        PolynomialGF2mSmallM[] polynomialGF2mSmallMArr = new PolynomialGF2mSmallM[this.h.length];
        GF2mField field = getField();
        for (int i = 0; i < this.h.length; i++) {
            polynomialGF2mSmallMArr[i] = new PolynomialGF2mSmallM(field, this.h[i]);
        }
        return polynomialGF2mSmallMArr;
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
        ASN1EncodableVector aSN1EncodableVector2 = new ASN1EncodableVector();
        for (byte[] dEROctetString : this.h) {
            aSN1EncodableVector2.add(new DEROctetString(dEROctetString));
        }
        aSN1EncodableVector.add(new DERSequence(aSN1EncodableVector2));
        return new DERSequence(aSN1EncodableVector);
    }
}
