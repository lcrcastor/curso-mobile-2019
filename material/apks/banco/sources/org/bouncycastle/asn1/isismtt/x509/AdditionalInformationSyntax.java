package org.bouncycastle.asn1.isismtt.x509;

import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.x500.DirectoryString;

public class AdditionalInformationSyntax extends ASN1Object {
    private DirectoryString a;

    public AdditionalInformationSyntax(String str) {
        this(new DirectoryString(str));
    }

    private AdditionalInformationSyntax(DirectoryString directoryString) {
        this.a = directoryString;
    }

    public static AdditionalInformationSyntax getInstance(Object obj) {
        if (obj instanceof AdditionalInformationSyntax) {
            return (AdditionalInformationSyntax) obj;
        }
        if (obj != null) {
            return new AdditionalInformationSyntax(DirectoryString.getInstance(obj));
        }
        return null;
    }

    public DirectoryString getInformation() {
        return this.a;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.a.toASN1Primitive();
    }
}
