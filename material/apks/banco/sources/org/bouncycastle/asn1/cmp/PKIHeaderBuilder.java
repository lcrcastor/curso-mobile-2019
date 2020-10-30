package org.bouncycastle.asn1.cmp;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1GeneralizedTime;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.GeneralName;

public class PKIHeaderBuilder {
    private ASN1Integer a;
    private GeneralName b;
    private GeneralName c;
    private ASN1GeneralizedTime d;
    private AlgorithmIdentifier e;
    private ASN1OctetString f;
    private ASN1OctetString g;
    private ASN1OctetString h;
    private ASN1OctetString i;
    private ASN1OctetString j;
    private PKIFreeText k;
    private ASN1Sequence l;

    public PKIHeaderBuilder(int i2, GeneralName generalName, GeneralName generalName2) {
        this(new ASN1Integer((long) i2), generalName, generalName2);
    }

    private PKIHeaderBuilder(ASN1Integer aSN1Integer, GeneralName generalName, GeneralName generalName2) {
        this.a = aSN1Integer;
        this.b = generalName;
        this.c = generalName2;
    }

    private static ASN1Sequence a(InfoTypeAndValue infoTypeAndValue) {
        return new DERSequence((ASN1Encodable) infoTypeAndValue);
    }

    private static ASN1Sequence a(InfoTypeAndValue[] infoTypeAndValueArr) {
        if (infoTypeAndValueArr == null) {
            return null;
        }
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        for (InfoTypeAndValue add : infoTypeAndValueArr) {
            aSN1EncodableVector.add(add);
        }
        return new DERSequence(aSN1EncodableVector);
    }

    private void a(ASN1EncodableVector aSN1EncodableVector, int i2, ASN1Encodable aSN1Encodable) {
        if (aSN1Encodable != null) {
            aSN1EncodableVector.add(new DERTaggedObject(true, i2, aSN1Encodable));
        }
    }

    public PKIHeader build() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.a);
        aSN1EncodableVector.add(this.b);
        aSN1EncodableVector.add(this.c);
        a(aSN1EncodableVector, 0, this.d);
        a(aSN1EncodableVector, 1, this.e);
        a(aSN1EncodableVector, 2, this.f);
        a(aSN1EncodableVector, 3, this.g);
        a(aSN1EncodableVector, 4, this.h);
        a(aSN1EncodableVector, 5, this.i);
        a(aSN1EncodableVector, 6, this.j);
        a(aSN1EncodableVector, 7, this.k);
        a(aSN1EncodableVector, 8, this.l);
        this.d = null;
        this.e = null;
        this.f = null;
        this.g = null;
        this.h = null;
        this.i = null;
        this.j = null;
        this.k = null;
        this.l = null;
        return PKIHeader.getInstance(new DERSequence(aSN1EncodableVector));
    }

    public PKIHeaderBuilder setFreeText(PKIFreeText pKIFreeText) {
        this.k = pKIFreeText;
        return this;
    }

    public PKIHeaderBuilder setGeneralInfo(ASN1Sequence aSN1Sequence) {
        this.l = aSN1Sequence;
        return this;
    }

    public PKIHeaderBuilder setGeneralInfo(InfoTypeAndValue infoTypeAndValue) {
        return setGeneralInfo(a(infoTypeAndValue));
    }

    public PKIHeaderBuilder setGeneralInfo(InfoTypeAndValue[] infoTypeAndValueArr) {
        return setGeneralInfo(a(infoTypeAndValueArr));
    }

    public PKIHeaderBuilder setMessageTime(ASN1GeneralizedTime aSN1GeneralizedTime) {
        this.d = aSN1GeneralizedTime;
        return this;
    }

    public PKIHeaderBuilder setProtectionAlg(AlgorithmIdentifier algorithmIdentifier) {
        this.e = algorithmIdentifier;
        return this;
    }

    public PKIHeaderBuilder setRecipKID(DEROctetString dEROctetString) {
        this.g = dEROctetString;
        return this;
    }

    public PKIHeaderBuilder setRecipKID(byte[] bArr) {
        return setRecipKID(bArr == null ? null : new DEROctetString(bArr));
    }

    public PKIHeaderBuilder setRecipNonce(ASN1OctetString aSN1OctetString) {
        this.j = aSN1OctetString;
        return this;
    }

    public PKIHeaderBuilder setRecipNonce(byte[] bArr) {
        return setRecipNonce(bArr == null ? null : new DEROctetString(bArr));
    }

    public PKIHeaderBuilder setSenderKID(ASN1OctetString aSN1OctetString) {
        this.f = aSN1OctetString;
        return this;
    }

    public PKIHeaderBuilder setSenderKID(byte[] bArr) {
        return setSenderKID(bArr == null ? null : new DEROctetString(bArr));
    }

    public PKIHeaderBuilder setSenderNonce(ASN1OctetString aSN1OctetString) {
        this.i = aSN1OctetString;
        return this;
    }

    public PKIHeaderBuilder setSenderNonce(byte[] bArr) {
        return setSenderNonce(bArr == null ? null : new DEROctetString(bArr));
    }

    public PKIHeaderBuilder setTransactionID(ASN1OctetString aSN1OctetString) {
        this.h = aSN1OctetString;
        return this;
    }

    public PKIHeaderBuilder setTransactionID(byte[] bArr) {
        return setTransactionID(bArr == null ? null : new DEROctetString(bArr));
    }
}
