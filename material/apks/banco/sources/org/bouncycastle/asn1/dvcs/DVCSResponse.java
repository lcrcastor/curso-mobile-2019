package org.bouncycastle.asn1.dvcs;

import java.io.IOException;
import org.bouncycastle.asn1.ASN1Choice;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERTaggedObject;

public class DVCSResponse extends ASN1Object implements ASN1Choice {
    private DVCSCertInfo a;
    private DVCSErrorNotice b;

    public DVCSResponse(DVCSCertInfo dVCSCertInfo) {
        this.a = dVCSCertInfo;
    }

    public DVCSResponse(DVCSErrorNotice dVCSErrorNotice) {
        this.b = dVCSErrorNotice;
    }

    public static DVCSResponse getInstance(Object obj) {
        if (obj == null || (obj instanceof DVCSResponse)) {
            return (DVCSResponse) obj;
        }
        if (obj instanceof byte[]) {
            try {
                return getInstance(ASN1Primitive.fromByteArray((byte[]) obj));
            } catch (IOException e) {
                StringBuilder sb = new StringBuilder();
                sb.append("failed to construct sequence from byte[]: ");
                sb.append(e.getMessage());
                throw new IllegalArgumentException(sb.toString());
            }
        } else if (obj instanceof ASN1Sequence) {
            return new DVCSResponse(DVCSCertInfo.getInstance(obj));
        } else {
            if (obj instanceof ASN1TaggedObject) {
                return new DVCSResponse(DVCSErrorNotice.getInstance(ASN1TaggedObject.getInstance(obj), false));
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Couldn't convert from object to DVCSResponse: ");
            sb2.append(obj.getClass().getName());
            throw new IllegalArgumentException(sb2.toString());
        }
    }

    public static DVCSResponse getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public DVCSCertInfo getCertInfo() {
        return this.a;
    }

    public DVCSErrorNotice getErrorNotice() {
        return this.b;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.a != null ? this.a.toASN1Primitive() : new DERTaggedObject(0, this.b);
    }

    public String toString() {
        StringBuilder sb;
        String dVCSErrorNotice;
        if (this.a != null) {
            sb = new StringBuilder();
            sb.append("DVCSResponse {\ndvCertInfo: ");
            dVCSErrorNotice = this.a.toString();
        } else if (this.b == null) {
            return null;
        } else {
            sb = new StringBuilder();
            sb.append("DVCSResponse {\ndvErrorNote: ");
            dVCSErrorNotice = this.b.toString();
        }
        sb.append(dVCSErrorNotice);
        sb.append("}\n");
        return sb.toString();
    }
}
