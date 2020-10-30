package org.bouncycastle.asn1.dvcs;

import org.bouncycastle.asn1.ASN1Choice;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.x509.DigestInfo;

public class Data extends ASN1Object implements ASN1Choice {
    private ASN1OctetString a;
    private DigestInfo b;
    private ASN1Sequence c;

    public Data(ASN1OctetString aSN1OctetString) {
        this.a = aSN1OctetString;
    }

    private Data(ASN1Sequence aSN1Sequence) {
        this.c = aSN1Sequence;
    }

    public Data(TargetEtcChain targetEtcChain) {
        this.c = new DERSequence((ASN1Encodable) targetEtcChain);
    }

    public Data(DigestInfo digestInfo) {
        this.b = digestInfo;
    }

    public Data(byte[] bArr) {
        this.a = new DEROctetString(bArr);
    }

    public Data(TargetEtcChain[] targetEtcChainArr) {
        this.c = new DERSequence((ASN1Encodable[]) targetEtcChainArr);
    }

    public static Data getInstance(Object obj) {
        if (obj instanceof Data) {
            return (Data) obj;
        }
        if (obj instanceof ASN1OctetString) {
            return new Data((ASN1OctetString) obj);
        }
        if (obj instanceof ASN1Sequence) {
            return new Data(DigestInfo.getInstance(obj));
        }
        if (obj instanceof ASN1TaggedObject) {
            return new Data(ASN1Sequence.getInstance((ASN1TaggedObject) obj, false));
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Unknown object submitted to getInstance: ");
        sb.append(obj.getClass().getName());
        throw new IllegalArgumentException(sb.toString());
    }

    public static Data getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(aSN1TaggedObject.getObject());
    }

    public TargetEtcChain[] getCerts() {
        if (this.c == null) {
            return null;
        }
        TargetEtcChain[] targetEtcChainArr = new TargetEtcChain[this.c.size()];
        for (int i = 0; i != targetEtcChainArr.length; i++) {
            targetEtcChainArr[i] = TargetEtcChain.getInstance(this.c.getObjectAt(i));
        }
        return targetEtcChainArr;
    }

    public ASN1OctetString getMessage() {
        return this.a;
    }

    public DigestInfo getMessageImprint() {
        return this.b;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.a != null ? this.a.toASN1Primitive() : this.b != null ? this.b.toASN1Primitive() : new DERTaggedObject(false, 0, this.c);
    }

    public String toString() {
        StringBuilder sb;
        Object obj;
        if (this.a != null) {
            sb = new StringBuilder();
            sb.append("Data {\n");
            obj = this.a;
        } else if (this.b != null) {
            sb = new StringBuilder();
            sb.append("Data {\n");
            obj = this.b;
        } else {
            sb = new StringBuilder();
            sb.append("Data {\n");
            obj = this.c;
        }
        sb.append(obj);
        sb.append("}\n");
        return sb.toString();
    }
}
