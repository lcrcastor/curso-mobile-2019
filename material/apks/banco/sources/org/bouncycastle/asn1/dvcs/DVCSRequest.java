package org.bouncycastle.asn1.dvcs;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.x509.GeneralName;

public class DVCSRequest extends ASN1Object {
    private DVCSRequestInformation a;
    private Data b;
    private GeneralName c;

    private DVCSRequest(ASN1Sequence aSN1Sequence) {
        this.a = DVCSRequestInformation.getInstance(aSN1Sequence.getObjectAt(0));
        this.b = Data.getInstance(aSN1Sequence.getObjectAt(1));
        if (aSN1Sequence.size() > 2) {
            this.c = GeneralName.getInstance(aSN1Sequence.getObjectAt(2));
        }
    }

    public DVCSRequest(DVCSRequestInformation dVCSRequestInformation, Data data) {
        this(dVCSRequestInformation, data, null);
    }

    public DVCSRequest(DVCSRequestInformation dVCSRequestInformation, Data data, GeneralName generalName) {
        this.a = dVCSRequestInformation;
        this.b = data;
        this.c = generalName;
    }

    public static DVCSRequest getInstance(Object obj) {
        if (obj instanceof DVCSRequest) {
            return (DVCSRequest) obj;
        }
        if (obj != null) {
            return new DVCSRequest(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public static DVCSRequest getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public Data getData() {
        return this.b;
    }

    public DVCSRequestInformation getRequestInformation() {
        return this.a;
    }

    public GeneralName getTransactionIdentifier() {
        return this.c;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.a);
        aSN1EncodableVector.add(this.b);
        if (this.c != null) {
            aSN1EncodableVector.add(this.c);
        }
        return new DERSequence(aSN1EncodableVector);
    }

    public String toString() {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append("DVCSRequest {\nrequestInformation: ");
        sb.append(this.a);
        sb.append("\n");
        sb.append("data: ");
        sb.append(this.b);
        sb.append("\n");
        if (this.c != null) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("transactionIdentifier: ");
            sb2.append(this.c);
            sb2.append("\n");
            str = sb2.toString();
        } else {
            str = "";
        }
        sb.append(str);
        sb.append("}\n");
        return sb.toString();
    }
}
