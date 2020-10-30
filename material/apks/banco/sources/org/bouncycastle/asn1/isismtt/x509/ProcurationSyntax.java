package org.bouncycastle.asn1.isismtt.x509;

import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERPrintableString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.x500.DirectoryString;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.IssuerSerial;

public class ProcurationSyntax extends ASN1Object {
    private String a;
    private DirectoryString b;
    private GeneralName c;
    private IssuerSerial d;

    public ProcurationSyntax(String str, DirectoryString directoryString, GeneralName generalName) {
        this.a = str;
        this.b = directoryString;
        this.c = generalName;
        this.d = null;
    }

    public ProcurationSyntax(String str, DirectoryString directoryString, IssuerSerial issuerSerial) {
        this.a = str;
        this.b = directoryString;
        this.c = null;
        this.d = issuerSerial;
    }

    private ProcurationSyntax(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() < 1 || aSN1Sequence.size() > 3) {
            StringBuilder sb = new StringBuilder();
            sb.append("Bad sequence size: ");
            sb.append(aSN1Sequence.size());
            throw new IllegalArgumentException(sb.toString());
        }
        Enumeration objects = aSN1Sequence.getObjects();
        while (objects.hasMoreElements()) {
            ASN1TaggedObject instance = ASN1TaggedObject.getInstance(objects.nextElement());
            switch (instance.getTagNo()) {
                case 1:
                    this.a = DERPrintableString.getInstance(instance, true).getString();
                    break;
                case 2:
                    this.b = DirectoryString.getInstance(instance, true);
                    break;
                case 3:
                    ASN1Primitive object = instance.getObject();
                    if (!(object instanceof ASN1TaggedObject)) {
                        this.d = IssuerSerial.getInstance(object);
                        break;
                    } else {
                        this.c = GeneralName.getInstance(object);
                        break;
                    }
                default:
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Bad tag number: ");
                    sb2.append(instance.getTagNo());
                    throw new IllegalArgumentException(sb2.toString());
            }
        }
    }

    public static ProcurationSyntax getInstance(Object obj) {
        if (obj == null || (obj instanceof ProcurationSyntax)) {
            return (ProcurationSyntax) obj;
        }
        if (obj instanceof ASN1Sequence) {
            return new ProcurationSyntax((ASN1Sequence) obj);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("illegal object in getInstance: ");
        sb.append(obj.getClass().getName());
        throw new IllegalArgumentException(sb.toString());
    }

    public IssuerSerial getCertRef() {
        return this.d;
    }

    public String getCountry() {
        return this.a;
    }

    public GeneralName getThirdPerson() {
        return this.c;
    }

    public DirectoryString getTypeOfSubstitution() {
        return this.b;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        if (this.a != null) {
            aSN1EncodableVector.add(new DERTaggedObject(true, 1, new DERPrintableString(this.a, true)));
        }
        if (this.b != null) {
            aSN1EncodableVector.add(new DERTaggedObject(true, 2, this.b));
        }
        aSN1EncodableVector.add(this.c != null ? new DERTaggedObject(true, 3, this.c) : new DERTaggedObject(true, 3, this.d));
        return new DERSequence(aSN1EncodableVector);
    }
}
