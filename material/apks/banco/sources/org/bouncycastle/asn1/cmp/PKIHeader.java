package org.bouncycastle.asn1.cmp;

import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1GeneralizedTime;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.GeneralName;

public class PKIHeader extends ASN1Object {
    public static final int CMP_1999 = 1;
    public static final int CMP_2000 = 2;
    public static final GeneralName NULL_NAME = new GeneralName(X500Name.getInstance(new DERSequence()));
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

    public PKIHeader(int i2, GeneralName generalName, GeneralName generalName2) {
        this(new ASN1Integer((long) i2), generalName, generalName2);
    }

    private PKIHeader(ASN1Integer aSN1Integer, GeneralName generalName, GeneralName generalName2) {
        this.a = aSN1Integer;
        this.b = generalName;
        this.c = generalName2;
    }

    private PKIHeader(ASN1Sequence aSN1Sequence) {
        Enumeration objects = aSN1Sequence.getObjects();
        this.a = ASN1Integer.getInstance(objects.nextElement());
        this.b = GeneralName.getInstance(objects.nextElement());
        this.c = GeneralName.getInstance(objects.nextElement());
        while (objects.hasMoreElements()) {
            ASN1TaggedObject aSN1TaggedObject = (ASN1TaggedObject) objects.nextElement();
            switch (aSN1TaggedObject.getTagNo()) {
                case 0:
                    this.d = ASN1GeneralizedTime.getInstance(aSN1TaggedObject, true);
                    break;
                case 1:
                    this.e = AlgorithmIdentifier.getInstance(aSN1TaggedObject, true);
                    break;
                case 2:
                    this.f = ASN1OctetString.getInstance(aSN1TaggedObject, true);
                    break;
                case 3:
                    this.g = ASN1OctetString.getInstance(aSN1TaggedObject, true);
                    break;
                case 4:
                    this.h = ASN1OctetString.getInstance(aSN1TaggedObject, true);
                    break;
                case 5:
                    this.i = ASN1OctetString.getInstance(aSN1TaggedObject, true);
                    break;
                case 6:
                    this.j = ASN1OctetString.getInstance(aSN1TaggedObject, true);
                    break;
                case 7:
                    this.k = PKIFreeText.getInstance(aSN1TaggedObject, true);
                    break;
                case 8:
                    this.l = ASN1Sequence.getInstance(aSN1TaggedObject, true);
                    break;
                default:
                    StringBuilder sb = new StringBuilder();
                    sb.append("unknown tag number: ");
                    sb.append(aSN1TaggedObject.getTagNo());
                    throw new IllegalArgumentException(sb.toString());
            }
        }
    }

    private void a(ASN1EncodableVector aSN1EncodableVector, int i2, ASN1Encodable aSN1Encodable) {
        if (aSN1Encodable != null) {
            aSN1EncodableVector.add(new DERTaggedObject(true, i2, aSN1Encodable));
        }
    }

    public static PKIHeader getInstance(Object obj) {
        if (obj instanceof PKIHeader) {
            return (PKIHeader) obj;
        }
        if (obj != null) {
            return new PKIHeader(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public PKIFreeText getFreeText() {
        return this.k;
    }

    public InfoTypeAndValue[] getGeneralInfo() {
        if (this.l == null) {
            return null;
        }
        InfoTypeAndValue[] infoTypeAndValueArr = new InfoTypeAndValue[this.l.size()];
        for (int i2 = 0; i2 < infoTypeAndValueArr.length; i2++) {
            infoTypeAndValueArr[i2] = InfoTypeAndValue.getInstance(this.l.getObjectAt(i2));
        }
        return infoTypeAndValueArr;
    }

    public ASN1GeneralizedTime getMessageTime() {
        return this.d;
    }

    public AlgorithmIdentifier getProtectionAlg() {
        return this.e;
    }

    public ASN1Integer getPvno() {
        return this.a;
    }

    public ASN1OctetString getRecipKID() {
        return this.g;
    }

    public ASN1OctetString getRecipNonce() {
        return this.j;
    }

    public GeneralName getRecipient() {
        return this.c;
    }

    public GeneralName getSender() {
        return this.b;
    }

    public ASN1OctetString getSenderKID() {
        return this.f;
    }

    public ASN1OctetString getSenderNonce() {
        return this.i;
    }

    public ASN1OctetString getTransactionID() {
        return this.h;
    }

    public ASN1Primitive toASN1Primitive() {
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
        return new DERSequence(aSN1EncodableVector);
    }
}
